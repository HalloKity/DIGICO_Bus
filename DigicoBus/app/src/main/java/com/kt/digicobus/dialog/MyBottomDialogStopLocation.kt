package com.kt.digicobus.dialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kt.digicobus.R
import com.kt.digicobus.naverMap.NaverMapAPIService
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback

class MyBottomDialogStopLocation : BottomSheetDialogFragment(), OnMapReadyCallback {
    private lateinit var mapView: MapView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.custom_dialog_commute_stop_location, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.btn_close)?.setOnClickListener {
            dismiss()
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

        // 네이버 지도
        mapView = view.findViewById(R.id.station_map)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

    }

    override fun onMapReady(naverMap: NaverMap) {
        val naverMapAPIService = NaverMapAPIService(naverMap)

        // 판교사옥, 판교역 위치
        val latlngOffice = LatLng(37.407385, 127.090450)

        // camera position
        naverMapAPIService.setCameraPosition(latlngOffice)

        // 판교사옥 마커 표시
        naverMapAPIService.setMarker(latlngOffice, "KT 판교사옥")
    }
}