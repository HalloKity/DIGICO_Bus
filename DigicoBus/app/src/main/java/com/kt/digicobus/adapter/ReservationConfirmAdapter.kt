package com.kt.digicobus.adapter

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatButton
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kt.digicobus.GOGenieApplication
import com.kt.digicobus.R
import com.kt.digicobus.activity.CommuteBusEntireRouteActivity
import com.kt.digicobus.data.data
import com.kt.digicobus.data.model.BusEntireRoute
import com.kt.digicobus.data.model.CommuteBusInfo
import com.kt.digicobus.data.model.ReserveSearch
import com.kt.digicobus.dialog.BottomSheetQrcodeHelp
import com.kt.digicobus.fragment.commute.TAG
import com.kt.digicobus.service.CommuteService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ReservationConfirmAdapter(var context: Context, private val resource: Int,  var ticketContentsList: MutableList<ReserveSearch>,
                                val onClickReservationCancelBtn: (Int) -> Unit)
    : RecyclerView.Adapter<ReservationHolder>() {
        val PERMISSIONS_CALL_PHONE = 1

    private lateinit var recyclerView: RecyclerView
    private lateinit var busRouteAdapter: BusRouteAdapter


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(resource, parent, false)

        return ReservationHolder(itemView)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ReservationHolder, position: Int) {
        // card setting
        setHolderData(holder, ticketContentsList[position])


        holder.iconInfo.setOnClickListener{
            val bottomDialog = BottomSheetQrcodeHelp()

            bottomDialog.show(
                (holder.itemView.context as FragmentActivity).supportFragmentManager,
                bottomDialog.tag
            )
        }

        val callNumber = holder.carNumberContent.text.toString()
        holder.carNumberContent.setOnClickListener{
            dialog(callNumber)
        }

        holder.iconCall.setOnClickListener{
            dialog(callNumber)
        }

        CoroutineScope(Dispatchers.Main).launch {
            val resultList = getBusEntireRoute(ticketContentsList[position].busId)
            setAdapter(holder.recyclerview, resultList)
        }

        // 지도로 보기
        holder.viewMap.setOnClickListener {
            // setting data.choiceRoute for map
            data.choiceRoute = CommuteBusInfo(busId = ticketContentsList[position].busId.toString())

            // start activity
            val intent = Intent(context, CommuteBusEntireRouteActivity::class.java).apply {
                if(ticketContentsList[position].reserveDate == LocalDate.now().toString())
                    putExtra("needBusMarker", true)
                putExtra("selectedStationId", ticketContentsList[position].stationId.toString())
            }
            context.startActivity(intent)
        }

        // 예약 취소
        holder.btnReserveCancel.setOnClickListener {
            onClickReservationCancelBtn(position)
        }
    }

    override fun getItemCount(): Int {
        return ticketContentsList.size
    }

    private suspend fun getBusEntireRoute(busId: Int): MutableList<BusEntireRoute> {
        var busEntireRouteList : MutableList<BusEntireRoute> = mutableListOf()

        withContext(Dispatchers.IO) {
            val service = GOGenieApplication.retrofit.create(CommuteService::class.java)
            val response = service.selectBusEntireRoute(busId).execute()

            if (response.code() == 200) {
                val resp = response.body()
                busEntireRouteList = (resp as List<BusEntireRoute>).toMutableList()
            } else {
                Log.d(TAG, "getBusEntireRoute: error code")
            }

        }
        return busEntireRouteList
    }

    private fun setAdapter(recyclerview:RecyclerView, busEntireRouteList: MutableList<BusEntireRoute>){

        // RecyclerView 객체 생성
        recyclerView = recyclerview
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        OverScrollDecoratorHelper.setUpOverScroll(recyclerView, OverScrollDecoratorHelper.ORIENTATION_VERTICAL)

        // 2. Adapter 객체 생성(한 행을 위해 반복 생성할 Layout과 데이터 전달)
        busRouteAdapter = BusRouteAdapter(context, R.layout.listview_specification_bus_route, busEntireRouteList)

        // 3. RecyclerView와 Adapter 연결
        recyclerView.adapter = busRouteAdapter
    }

    fun dialog(callNumber: String){
        //권한확인 후 다이얼로그 뷰 띄우기
        AlertDialog.Builder(context)
            .setMessage("${callNumber}로 전화하시겠습니까?")
            .setPositiveButton("확인", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, which: Int) {
                    if (ContextCompat.checkSelfPermission(
                            context.applicationContext,
                            Manifest.permission.CALL_PHONE
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        ActivityCompat.requestPermissions(
                            context as Activity,
                            arrayOf(Manifest.permission.CALL_PHONE),
                            PERMISSIONS_CALL_PHONE
                        )
                    } else {
                        val callIntent = Intent(Intent.ACTION_CALL)
                        callIntent.data = Uri.parse("tel:${callNumber}")
                        context.startActivity(callIntent)
                    }
                }
            })
            .setNegativeButton("취소") { dialog, which -> }
            .create()
            .show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setHolderData (holder: ReservationHolder, item: ReserveSearch) {
        // 날짜에 대한 요일 구하기
        val localDate = LocalDate.parse(item.reserveDate, DateTimeFormatter.ISO_DATE)

        holder.tv_date.text = "${item.reserveDate}  ${data.dayNumToStringList[localDate.dayOfWeek.value]}"
        holder.tv_to_place.text = item.mainPlace
        holder.tv_from_place.text = item.officePlace
        holder.tv_to_time.text = item.departureTime
        holder.tv_from_time.text = item.officeTime
        holder.tv_bus_number_content.text = item.busNumber
        holder.tv_car_number_content.text = item.busDriverNumber
    }
}

class ReservationHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tv_date: TextView = itemView!!.findViewById(R.id.tv_date)
    val img_qr_code: ImageView = itemView!!.findViewById(R.id.img_qr_code)
    val tv_to_place: TextView = itemView!!.findViewById(R.id.tv_to_place)
    val tv_from_place: TextView = itemView!!.findViewById(R.id.tv_from_place)
    val tv_to_time: TextView = itemView!!.findViewById(R.id.tv_to_time)
    val tv_from_time: TextView = itemView!!.findViewById(R.id.tv_from_time)
    val tv_bus_number_content : TextView = itemView!!.findViewById(R.id.tv_bus_number_content)
    val tv_car_number_content: TextView = itemView!!.findViewById(R.id.tv_car_number_content)
    val btnReserveCancel: AppCompatButton = itemView!!.findViewById(R.id.btn_reserve_cancle)

    val carNumberContent: TextView = itemView!!.findViewById((R.id.tv_car_number_content))
    val iconCall: ImageView = itemView!!.findViewById(R.id.icon_call)
    val iconInfo: ImageView = itemView!!.findViewById(R.id.icon_info)
    val recyclerview: RecyclerView = itemView!!.findViewById(R.id.recyclerview)
    val viewMap : TextView = itemView!!.findViewById(R.id.tv_large)
}