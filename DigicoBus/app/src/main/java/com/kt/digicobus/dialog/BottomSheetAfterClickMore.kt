package com.kt.digicobus.dialog

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kt.digicobus.R
import com.kt.digicobus.activity.CommuteBusEntireRouteActivity
import com.kt.digicobus.data.model.CommuteBusInfo
import com.naver.maps.geometry.LatLng

class BottomSheetAfterClickMore(var selectBusInfo: CommuteBusInfo) :
    BottomSheetDialogFragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.custom_dialog_after_click_more, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.btn_close)?.setOnClickListener {
            dismiss()
        }

        // 정류장 위치 보기
        val specificationMap = view.findViewById<LinearLayout>(R.id.layout_linear_specification_map)
        specificationMap.setOnClickListener{
            val bottomDialog = BottomSheetStopLocation(selectBusInfo)

            bottomDialog.show(
                (context as FragmentActivity).supportFragmentManager,
                bottomDialog.tag
            )
        }

        // 전체 노선 보기
        val totalMap = view.findViewById<LinearLayout>(R.id.layout_linear_total_map)
        totalMap.setOnClickListener {
            dismiss()

            // start activity
            val intent = Intent(context, CommuteBusEntireRouteActivity::class.java)
            context?.startActivity(intent)
        }


        // dialog가 drag로 닫히지 않도록 설정
        if (dialog is BottomSheetDialog) {
            val behaviour = (dialog as BottomSheetDialog).behavior
            behaviour.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                        behaviour.state = BottomSheetBehavior.STATE_EXPANDED
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                }
            })
        }
    }
}