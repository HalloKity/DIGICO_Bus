package com.kt.digicobus.fragment

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
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.PathOverlay
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper


class ShuttleFragment : Fragment(), OnMapReadyCallback {
    private lateinit var binding : FragmentShuttleBinding
    private lateinit var ctx: Context
    private lateinit var naverMap : MapView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx = context
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShuttleBinding.inflate(layoutInflater)

        //spinner
        val items = resources.getStringArray(R.array.shuttle_spinner_array)
        val myAdapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, items)
        binding.spinner.adapter = myAdapter

        //출퇴근 fragment 연결
        val navHostFragment = childFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navController = navHostFragment.navController
//        OverScrollDecoratorHelper.setUpOverScroll(R.id.nav_host, OverScrollDecoratorHelper.ORIENTATION_VERTICAL)
        NavigationUI.setupWithNavController(binding.tabView, navController)

        // 네이버 지도
        naverMap = binding.naverMap
        naverMap.onCreate(savedInstanceState)
        naverMap.getMapAsync(this)

        return binding.root
    }

    override fun onMapReady(naverMap: NaverMap) {
        // 판교 사옥 위치
        // 판교역 37.394769, 127.111194
        val latlng = LatLng(37.406325, 127.090831)

        // camera position
        val cameraUpdate = CameraUpdate.scrollTo(latlng)
        naverMap.moveCamera(cameraUpdate)

        // 마커 표시
        val marker = Marker()
        marker.position = latlng
        marker.width = Marker.SIZE_AUTO
        marker.height = Marker.SIZE_AUTO
        marker.captionText = "KT 판교사옥"
        marker.map = naverMap
    }

}