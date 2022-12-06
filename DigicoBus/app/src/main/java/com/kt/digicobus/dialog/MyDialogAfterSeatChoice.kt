package com.kt.digicobus.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import com.kt.digicobus.R


class MyDialogAfterSeatChoice(context: Context?,container: ViewGroup?) : Dialog(context!!) {
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.custom_dialog_after_seat_choice)

        var btn_ok = findViewById<Button>(R.id.btn_ok)
        var btn_cancle = findViewById<Button>(R.id.btn_cancle)

        //확인 버튼 눌렀을 때 뒤로 가는 버튼
        btn_ok.setOnClickListener{
            dismiss()
            container?.findNavController()?.navigate(R.id.action_CommuteCalendarChoiceFragment_to_CommuteMainFragment)
        }

        //취소버튼
        btn_cancle.setOnClickListener {
            dismiss()
        }
    }

}