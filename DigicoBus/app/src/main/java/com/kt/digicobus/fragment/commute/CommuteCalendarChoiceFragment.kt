package com.kt.digicobus.fragment.commute

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.kt.digicobus.GOGenieApplication
import com.kt.digicobus.R
import com.kt.digicobus.adapter.MonthAdapter
import com.kt.digicobus.data.data.Companion.busRegisterList
import com.kt.digicobus.data.data.Companion.choiceRoute
import com.kt.digicobus.data.model.RemainSeat
import com.kt.digicobus.data.model.ReserveSearch
import com.kt.digicobus.databinding.FragmentCommuteCalendarChoiceBinding
import com.kt.digicobus.dialog.DialogAfterSeatChoice
import com.kt.digicobus.service.CommuteService
import com.kt.digicobus.service.ReservationService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//통근버스 3
class CommuteCalendarChoiceFragment : Fragment() {
    private lateinit var binding : FragmentCommuteCalendarChoiceBinding
    private lateinit var remainSeatList:MutableList<RemainSeat>
    private lateinit var callback: OnBackPressedCallback
    private var reservationInfoList = mutableListOf<ReserveSearch>()

    private lateinit var ctx: Context
    private var commuteId : Int? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx = context

        //뒤로가기 버튼 콜백 함수
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                busRegisterList.clear()
                findNavController()?.navigate(R.id.action_CommuteCalendarChoiceFragment_to_CommuteMainFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommuteCalendarChoiceBinding.inflate(layoutInflater)
        commuteId = arguments?.getInt("commuteId", -1)

        CoroutineScope(Dispatchers.Main).launch {
            getRemainSeatInfo()
            getReservationInfo()
            makeCalendar()
        }

        changeDisplayByRoute()

        // 뒤로가기
        binding.btnBack.setOnClickListener{
            busRegisterList.clear()
            container?.findNavController()?.navigate(R.id.action_CommuteCalendarChoiceFragment_to_CommuteMainFragment)
        }

        //신청하기
        binding.btnChoice.setOnClickListener{
            //알림창 띄우기
            var dialogListener = DialogAfterSeatChoice(ctx, container)
            //다이얼로그 띄우기
            dialogListener.show()
        }

        return binding.root
    }

    private fun changeDisplayByRoute() {
        //출근인지 퇴근인지
        if(choiceRoute.commuteId.toInt() == 0){
            //출발지 지정
            binding.tvStartPlace.text = choiceRoute.mainPlace
            //출발 시간 지정
            binding.tvStartTime.text = choiceRoute.departureTime
            //도착지 지정
            binding.tvEndPlace.text = choiceRoute.officePlace
            //도착 시간 지정
            binding.tvEndTime.text = choiceRoute.officeTime
        }else{
            //출발지 지정
            binding.tvStartPlace.text =  choiceRoute.officePlace
            binding.tvStartPlace.setTextColor(Color.parseColor("#656565"))
            //출발 시간 지정
            binding.tvStartTime.text = choiceRoute.officeTime
            //도착지 지정
            binding.tvEndPlace.text = choiceRoute.mainPlace
            binding.tvEndPlace.setTextColor(Color.parseColor("#000000"))
            //도착 시간 지정
            binding.tvEndTime.text = choiceRoute.departureTime
        }
    }

    private fun makeCalendar() {
        val monthListManager = LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, false)
        val monthListAdapter = MonthAdapter(ctx,binding,commuteId,remainSeatList, reservationInfoList)

        binding.calendarCustom.apply {
            layoutManager = monthListManager
            adapter = monthListAdapter
            // scrollToPosition은 리스트를 item의 위치를 지정한 곳에서 시작한다.
            // 해당 위치에서 리스트를 시작하는 이유는 뒤 Adapter 부분에서 설명한다.
            scrollToPosition(Int.MAX_VALUE/2)
        }
        // PagerSnapHelper()를 설정함으로써 한 항목씩 스크롤이 되도록 만들 수 있다.
        val snap = PagerSnapHelper()
        snap.attachToRecyclerView(binding.calendarCustom)
    }



    private suspend fun getRemainSeatInfo() {
        withContext(Dispatchers.IO) {
            val service = GOGenieApplication.retrofit.create(CommuteService::class.java)
            val response = service.selectRemainSeatByBusID(choiceRoute.busId).execute()

            if (response.code() == 200) {
                var resp = response.body()

                remainSeatList = (resp as List<RemainSeat>).toMutableList()

                println("busId : ${choiceRoute.busId} , date : ${remainSeatList?.get(0)?.date}" +
                        " remainseatcount : ${remainSeatList?.get(0)?.remainSeatsCount}")

                Log.d(TAG, "getRemainSeat: ${remainSeatList.size}")
            } else {
                Log.d(TAG, "getRemainSeat: error code")
            }
        }
    }

    // 예약내역 가져오기
    private suspend fun getReservationInfo() {
        withContext(Dispatchers.IO) {
            val service = GOGenieApplication.retrofit.create(ReservationService::class.java)
            val response = service.selectAllReservationBusInfo().execute()

            if (response.code() == 200) {
                var resp = response.body()
                reservationInfoList = (resp as List<ReserveSearch>).toMutableList()
            } else {
                Log.d(TAG, "getAllReservationBusInfo: error code")
            }
        }
    }
}