package com.kt.digicobus.adapter

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kt.digicobus.R
import com.kt.digicobus.data.TicketContent
import com.kt.digicobus.data.data
import com.kt.digicobus.dialog.BottomSheetQrcodeHelp
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper

class ReservationConfirmAdapter(var context: Context, private val resource: Int,  var ticketContentsList: MutableList<TicketContent>)
    : RecyclerView.Adapter<ReservationHolder>() {
    val PERMISSIONS_CALL_PHONE = 1

    private lateinit var recyclerView: RecyclerView
    private lateinit var busRouteAdapter: BusRouteAdapter


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(resource, parent, false)

        return ReservationHolder(itemView)
    }

    override fun onBindViewHolder(holder: ReservationHolder, position: Int) {
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

        setAdapter(holder.recyclerview)

    }

    override fun getItemCount(): Int {
        return ticketContentsList.size
    }

    private fun setAdapter(recyclerview:RecyclerView){

        // RecyclerView 객체 생성
        recyclerView = recyclerview
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        OverScrollDecoratorHelper.setUpOverScroll(recyclerView, OverScrollDecoratorHelper.ORIENTATION_VERTICAL)

        // 2. Adapter 객체 생성(한 행을 위해 반복 생성할 Layout과 데이터 전달)
        busRouteAdapter = BusRouteAdapter(context, R.layout.listview_specification_bus_route, data.ticketList)

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
}

class ReservationHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tv_date: TextView = itemView!!.findViewById(R.id.tv_date)
    val img_qr_code: ImageView = itemView!!.findViewById(R.id.img_qr_code)
    val tv_to_place: TextView = itemView!!.findViewById(R.id.tv_to_place)
    val tv_from_place: TextView = itemView!!.findViewById(R.id.tv_from_place)
    val tv_to_time: TextView = itemView!!.findViewById(R.id.tv_to_time)
    val tv_from_time: TextView = itemView!!.findViewById(R.id.tv_from_time)

    val carNumberContent: TextView = itemView!!.findViewById((R.id.tv_car_number_content))
    val iconCall: ImageView = itemView!!.findViewById(R.id.icon_call)
    val iconInfo: ImageView = itemView!!.findViewById(R.id.icon_info)
    val recyclerview: RecyclerView = itemView!!.findViewById(R.id.recyclerview)
}