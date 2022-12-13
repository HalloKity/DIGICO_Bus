package com.kt.digicobus.dialog

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import com.kt.digicobus.GOGenieApplication
import com.kt.digicobus.R
import com.kt.digicobus.data.data.Companion.busRegisterList
import com.kt.digicobus.data.data.Companion.choiceRoute
import com.kt.digicobus.data.model.RemainSeat
import com.kt.digicobus.fragment.commute.TAG
import com.kt.digicobus.service.CommuteService
import com.kt.digicobus.service.ReservationService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DialogAfterSeatChoice(context: Context?, container: ViewGroup?) : Dialog(context!!) {
    val container = container

    //활동 기반 컨텍스트가 아닌 경우 다음 메서드를 사용하여 컨텍스트 또는 throw 및 예외에서 활동을 가져올 수 있습니다
    // 일반 context를 쓸 경우 회원가입 Activity가 죽어버리는 현상이 발생해 이를 해결하기 위해 사용
    private fun getActivity(context: Context): Activity {
        return when (context) {
            is Activity -> context
            is ContextWrapper -> getActivity(context.getBaseContext())
            else -> error("Non Activity based context")
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.custom_dialog_after_seat_choice)

        //예약 등록 로직
        //예약내역이 하나일 경우 => 2022.12.7 (목)
        if(busRegisterList.size == 1){
            //"2022.12.7 (목)\n인천 간선오거리역 > 판교사옥\n예약하시겠습니까?
            findViewById<TextView>(R.id.tv_text).text = "${busRegisterList[0].reserveDate}\n${choiceRoute.detailPlace} > ${choiceRoute.officePlace}\n예약하시겠습니까?"
        }
        //예약 내역이 여러개일 경우 => 2022.12.7 (목) 외 3개
        else{
            //"2022.12.7 (목)\n인천 간선오거리역 > 판교사옥\n예약하시겠습니까?
            findViewById<TextView>(R.id.tv_text).text = "${busRegisterList[0].reserveDate}외 ${busRegisterList.size}개\n${choiceRoute.detailPlace} > ${choiceRoute.officePlace}\n예약하시겠습니까?"
        }


        var btnOk = findViewById<Button>(R.id.btn_ok)
        var btnCancle = findViewById<Button>(R.id.btn_cancle)

        //확인 버튼 눌렀을 때 뒤로 가는 버튼
        btnOk.setOnClickListener{
            //post로 보내기
            CoroutineScope(Dispatchers.Main).launch {
                insertSeat()
                dismiss()
            }


            // 예약이 완료되었습니다. 다이얼로그 뜨ㅣ우고 거기서 이동
            //알림창 띄우기
            var dialog_listener = Dialog(context,container); //다이얼로그 선언
            //다이얼로그 띄우기
            dialog_listener.show()
        }

        //취소버튼
        btnCancle.setOnClickListener {
            dismiss()
        }
    }

    private suspend fun insertSeat() {
        withContext(Dispatchers.IO) {
            val service = GOGenieApplication.retrofit.create(ReservationService::class.java)
            val response = service.insertReservationBusInfo(busRegisterList).execute()

            if (response.code() == 200) {
                var resp = response.body()

                Log.d(TAG, "insertSeat: ${resp}")
            } else {
                Log.d(TAG, "insertSeat: error code")
            }
        }
    }
}