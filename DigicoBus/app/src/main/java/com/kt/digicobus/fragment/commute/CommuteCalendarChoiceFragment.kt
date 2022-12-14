package com.kt.digicobus.fragment.commute

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.kt.digicobus.GOGenieApplication
import com.kt.digicobus.R
import com.kt.digicobus.adapter.MonthAdapter
import com.kt.digicobus.data.data
import com.kt.digicobus.data.data.Companion.choiceRoute
import com.kt.digicobus.data.model.RemainSeat
import com.kt.digicobus.databinding.FragmentCommuteCalendarChoiceBinding
import com.kt.digicobus.dialog.DialogAfterSeatChoice
import com.kt.digicobus.service.CommuteService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//통근버스 3
class CommuteCalendarChoiceFragment : Fragment() {
    private lateinit var binding : FragmentCommuteCalendarChoiceBinding
    private lateinit var remainSeatList:MutableList<RemainSeat>

    private lateinit var ctx: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommuteCalendarChoiceBinding.inflate(layoutInflater)

        //캘린더 뼈대

        CoroutineScope(Dispatchers.Main).launch {
            getRemainSeatInfo()
            makeCalendar()
        }

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

        // 뒤로가기
        binding.btnBack.setOnClickListener{
            container?.findNavController()?.navigate(R.id.action_CommuteCalendarChoiceFragment_to_CommuteMainFragment)
        }

        //신청하기
        binding.btnChoice.setOnClickListener{
            //알림창 띄우기
            var dialog_listener = DialogAfterSeatChoice(ctx, container)
            //다이얼로그 띄우기
            dialog_listener.show()
        }

        return binding.root
    }

    private fun makeCalendar() {
        val monthListManager = LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, false)
        val monthListAdapter = MonthAdapter(ctx,binding,remainSeatList)

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
}