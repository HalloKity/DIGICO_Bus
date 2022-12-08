package com.kt.digicobus.fragment.commute

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kt.digicobus.R
import com.kt.digicobus.adapter.BusStopListAdapter
import com.kt.digicobus.data.BusStopContent
import com.kt.digicobus.data.data
import com.kt.digicobus.data.data.Companion.busStopList
import com.kt.digicobus.databinding.FragmentCommuteBusEntireRouteBinding
import com.kt.digicobus.naverMap.NaverMapAPIService
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper

//통근버스 - 전체 노선 보기
class CommuteBusEntireRoute : Fragment(), OnMapReadyCallback {
    private lateinit var binding: FragmentCommuteBusEntireRouteBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var busStopListAdapter: BusStopListAdapter
    private lateinit var naverMapAPIService : NaverMapAPIService

    private lateinit var ctx: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommuteBusEntireRouteBinding.inflate(layoutInflater)

        setAdapter()

        //뒤로가기
        binding.btnBack.setOnClickListener {
            data.busChoiceInfo = -1
            container?.findNavController()
                ?.navigate(R.id.action_CommuteBusChoiceFragment_to_CommuteMainFragment)
        }

        // 네이버 지도
        val mapView = binding.routeMap
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        binding.recyclerview.setOnClickListener {
            val clickedItem = busStopList.find { it.isClick }
            if (clickedItem != null) {
                naverMapAPIService.setMarker(LatLng(clickedItem.tv_location_latitude, clickedItem.tv_location_longitude))
            }
        }

        return binding.root
    }

    private fun setAdapter() {
        fillData()

        // RecyclerView 객체 생성
        recyclerView = binding.recyclerview
        recyclerView.layoutManager = LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false)
        OverScrollDecoratorHelper.setUpOverScroll(
            recyclerView,
            OverScrollDecoratorHelper.ORIENTATION_VERTICAL
        )

        // 2. Adapter 객체 생성(한 행을 위해 반복 생성할 Layout과 데이터 전달)
        busStopListAdapter =
            BusStopListAdapter(ctx, binding, R.layout.listview_detail_bus_info, busStopList)

        // 3. RecyclerView와 Adapter 연결
        recyclerView.adapter = busStopListAdapter
    }

    fun fillData() {
        busStopList.clear()
        busStopList.add(BusStopContent("간선오거리역", "06:50", 37.46749, 126.70815, false))
        busStopList.add(BusStopContent("부평역", "07:00", 37.48727, 126.72485, false))
        busStopList.add(BusStopContent("송내남부역점앞", "07:15", 37.48531, 126.75358, false))
        busStopList.add(BusStopContent("시흥영업소", "07:25", 37.45037, 126.80362, false))
        busStopList.add(BusStopContent("판교사옥", "08:30", 37.4063, 127.0908, false))

    }

    override fun onMapReady(naverMap: NaverMap) {
        naverMapAPIService = NaverMapAPIService(naverMap)

        // camera position
        naverMapAPIService.setCameraPosition(
            LatLng(
                busStopList[busStopList.size / 2].tv_location_latitude,
                busStopList[busStopList.size / 2].tv_location_longitude
            )
        )

//        // 첫번째 정차역 마커표시
//        naverMapAPIService.setMarker(
//            LatLng(
//                busStopList[0].tv_location_latitude,
//                busStopList[0].tv_location_longitude
//            ),
//            busStopList[0].tv_bus_stop_location
//        )
//
//        // 마지막 정차역 마커표시
//        naverMapAPIService.setMarker(
//            LatLng(
//                busStopList[busStopList.size-1].tv_location_latitude,
//                busStopList[busStopList.size-1].tv_location_longitude
//            ),
//            busStopList[0].tv_bus_stop_location
//        )

        for (i in 1 until busStopList.size) {
            val latlngStart = LatLng(busStopList[i - 1].tv_location_latitude, busStopList[i - 1].tv_location_longitude)
            val latlngEnd = LatLng(busStopList[i].tv_location_latitude, busStopList[i].tv_location_longitude)

            // 마커 표시
//            naverMapAPIService.setMarker(latlngEnd, busStopList[i].tv_bus_stop_location)

            // 경로 표시
            naverMapAPIService.setPath(latlngStart, latlngEnd)
        }
    }
}