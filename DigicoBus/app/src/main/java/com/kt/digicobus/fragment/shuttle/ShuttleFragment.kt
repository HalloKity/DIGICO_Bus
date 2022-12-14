package com.kt.digicobus.fragment.shuttle

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.kt.digicobus.R
import com.kt.digicobus.databinding.FragmentShuttleBinding
import com.kt.digicobus.dialog.DialogAfterSeatChoice
import com.kt.digicobus.dialog.DialogNuribus
import com.kt.digicobus.naverMap.NaverMapAPIService
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback

class ShuttleFragment : Fragment(), OnMapReadyCallback {
    private lateinit var binding: FragmentShuttleBinding
    private lateinit var ctx: Context
    private lateinit var mapView: MapView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShuttleBinding.inflate(layoutInflater)

        //출퇴근 fragment 연결
//        val navHostFragment =
//            childFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
//        val navController = navHostFragment.navController
//        OverScrollDecoratorHelper.setUpOverScroll(R.id.nav_host, OverScrollDecoratorHelper.ORIENTATION_VERTICAL)
//        NavigationUI.setupWithNavController(binding.tabView, navController)

        binding.tvTitleEtcContent.setOnClickListener{
            //알림창 띄우기
            var dialog_listener = DialogNuribus(ctx)
            //다이얼로그 띄우기
            dialog_listener.show()
        }

        // 네이버 지도
        mapView = binding.naverMap
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        return binding.root
    }

    override fun onMapReady(naverMap: NaverMap) {
        val naverMapAPIService = NaverMapAPIService(naverMap, true)

        // 판교사옥, 판교역 위치
        val latlngOffice = LatLng(37.407385, 127.090450)
        val latlngStation = LatLng(37.394769, 127.111194)

        // 판교사옥 마커 표시
        naverMapAPIService.setMarker(latlngOffice, "KT 판교사옥")

        // 판교역 마커 표시
        naverMapAPIService.setMarker(latlngStation)

        // 판교역-판교사옥 경로 표시
        naverMapAPIService.setPath(latlngStation, latlngOffice)

        // camera position
        naverMapAPIService.showPath(latlngStation, latlngOffice)
    }
}