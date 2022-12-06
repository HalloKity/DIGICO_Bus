package com.kt.digicobus.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kt.digicobus.R
import com.kt.digicobus.naverMap.NaverMapAPIService
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback

class MyBottomDialogStopLocation(val stopLocation: String, val departureTime: String, val latlng: LatLng) :
    BottomSheetDialogFragment(), OnMapReadyCallback {

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

        // 정류장 위치, 시간
        val tvStopLocation = view.findViewById<TextView>(R.id.tv_stop_location)
        tvStopLocation.text = stopLocation

        val tvStopTime = view.findViewById<TextView>(R.id.tv_stop_time)
        tvStopTime.text = departureTime

        // 네이버 지도
        val mapView = view.findViewById<MapView>(R.id.station_map)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

    }

    override fun onMapReady(naverMap: NaverMap) {
        val naverMapAPIService = NaverMapAPIService(naverMap)

        // camera position
        naverMapAPIService.setCameraPosition(latlng)

        // 판교사옥 마커 표시
        naverMapAPIService.setMarker(latlng)
    }
}