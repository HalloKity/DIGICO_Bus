package com.kt.digicobus.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kt.digicobus.GOGenieApplication
import com.kt.digicobus.R
import com.kt.digicobus.adapter.ReservationConfirmAdapter
import com.kt.digicobus.data.model.CommuteBusInfo
import com.kt.digicobus.data.model.ReserveSearch
import com.kt.digicobus.databinding.FragmentReservationConfirmBinding
import com.kt.digicobus.dialog.DialogAfterSeatChoice
import com.kt.digicobus.fragment.commute.TAG
import com.kt.digicobus.service.ReservationService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper

class ReservationConfirmFragment : Fragment() {
    private lateinit var binding : FragmentReservationConfirmBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var reservationConfirmAdapter: ReservationConfirmAdapter

    private lateinit var ctx: Context
    private lateinit var ticketList : MutableList<ReserveSearch>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReservationConfirmBinding.inflate(layoutInflater)

        setDataAndAdapter()

        return binding.root
    }

    private fun setAdapter(){

        // RecyclerView 객체 생성
        recyclerView = binding.recyclerview
        recyclerView.layoutManager = LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false)
        OverScrollDecoratorHelper.setUpOverScroll(recyclerView, OverScrollDecoratorHelper.ORIENTATION_VERTICAL)

        // 2. Adapter 객체 생성(한 행을 위해 반복 생성할 Layout과 데이터 전달)
        reservationConfirmAdapter = ReservationConfirmAdapter(ctx, R.layout.card_reservation_confirm, ticketList, ::onClickReservationCancelBtn)

        // 3. RecyclerView와 Adapter 연결
        recyclerView.adapter = reservationConfirmAdapter
    }

    private fun setDataAndAdapter() {
        CoroutineScope(Dispatchers.Main).launch {
            getAllReservationBusInfo()
            setAdapter()

            // 예매내역이 없을 경우
            if(ticketList.isNotEmpty()){
                binding.tvNoReservation.visibility = View.GONE
                binding.imgNoReservation.visibility = View.GONE
                binding.recyclerview.visibility = View.VISIBLE
            }else{
                binding.tvNoReservation.visibility = View.VISIBLE
                binding.imgNoReservation.visibility = View.VISIBLE
                binding.recyclerview.visibility = View.GONE
            }
        }

    }

    // 예약 취소
    private fun onClickReservationCancelBtn(position: Int) {
        val dialogCancel = DialogAfterSeatChoice(ctx, null, ticketList[position], setReservationConfirmAdapter = ::setDataAndAdapter)
//        // 예매내역이 없을 경우
//        if(ticketList.isNotEmpty()){
//            binding.tvNoReservation.visibility = View.GONE
//            binding.imgNoReservation.visibility = View.GONE
//            binding.recyclerview.visibility = View.VISIBLE
//        }
        dialogCancel.show()
    }

    private suspend fun getAllReservationBusInfo() {
        withContext(Dispatchers.IO) {
            val service = GOGenieApplication.retrofit.create(ReservationService::class.java)
            val response = service.selectAllReservationBusInfo().execute()

            if (response.code() == 200) {
                var resp = response.body()
                var list = (resp as List<ReserveSearch>).toMutableList()

                ticketList = mutableListOf<ReserveSearch>()
                for(i in 0 until list.size){
                    var startTime = list[i].departureTime.split(":")
                    var endTime = list[i].officeTime.split(":")

                    //시간이 크면
                    if(startTime[0].toInt() > endTime[0].toInt()){
                        //바꿔서 저장
                        var newData = ReserveSearch(list[i].reservationId,list[i].reserveDate,list[i].stationId,list[i].officePlace,
                            list[i].detailPlace, list[i].officeTime, list[i].mainPlace, list[i].departureTime,
                            list[i].busId, list[i].busNumber, list[i].busDriverNumber)
                        ticketList.add(newData)
                    }
                    //시간은 같은데 분이 크면
                    else if(startTime[0].toInt() == endTime[0].toInt()){
                        if(startTime[1].toInt() > endTime[1].toInt()){
                            //바꿔서 저장
                            var newData = ReserveSearch(list[i].reservationId,list[i].reserveDate,list[i].stationId,list[i].officePlace,
                                                list[i].detailPlace, list[i].officeTime, list[i].mainPlace, list[i].departureTime,
                                                list[i].busId, list[i].busNumber, list[i].busDriverNumber)
                            ticketList.add(newData)
                        }else{
                            //그냥 저장
                            ticketList.add(list[i])
                        }
                    }else{
                        //그냥 저장
                        ticketList.add(list[i])
                    }
                }

            } else {
                Log.d(TAG, "getAllReservationBusInfo: error code")
            }
        }
    }
}