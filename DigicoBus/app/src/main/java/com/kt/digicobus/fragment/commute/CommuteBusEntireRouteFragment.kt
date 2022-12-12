package com.kt.digicobus.fragment.commute

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kt.digicobus.R
import com.kt.digicobus.adapter.BusStopListAdapter
import com.kt.digicobus.data.BusStopContent
import com.kt.digicobus.data.data.Companion.busStopList
import com.kt.digicobus.databinding.FragmentCommuteBusEntireRouteBinding
import com.kt.digicobus.naverMap.NaverMapAPIService
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper

//통근버스 - 전체 노선 보기
class CommuteBusEntireRouteFragment : Fragment(), OnMapReadyCallback {
    private lateinit var binding: FragmentCommuteBusEntireRouteBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var busStopListAdapter: BusStopListAdapter
    private lateinit var naverMapAPIService : NaverMapAPIService

    private lateinit var ctx: Context
    private var prevMarker : Marker? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommuteBusEntireRouteBinding.inflate(layoutInflater)

        // 뒤로 가기
        binding.btnBack.setOnClickListener {
            activity?.onBackPressed()
        }

        setAdapter()

        // 네이버 지도
        val mapView = binding.routeMap
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

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
            BusStopListAdapter(ctx, binding, R.layout.listview_detail_bus_info, busStopList, ::onClickItem)

        // 3. RecyclerView와 Adapter 연결
        recyclerView.adapter = busStopListAdapter
    }

    private fun onClickItem(position: Int) {
        val clickedItem = busStopList[position]
        val clickedItemLat = LatLng(clickedItem.locationLatitude, clickedItem.locationLongitude)

        if (prevMarker != null) {
            naverMapAPIService.removeMarker(prevMarker!!)
        }
        prevMarker = naverMapAPIService.setMarker(clickedItemLat, clickedItem.busStopLocation)
        naverMapAPIService.setCameraPositionAndZoom(clickedItemLat, 16.0)
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

        for (i in 1 until busStopList.size) {
            val latlngStart = LatLng(busStopList[i - 1].locationLatitude, busStopList[i - 1].locationLongitude)
            val latlngEnd = LatLng(busStopList[i].locationLatitude, busStopList[i].locationLongitude)

            // 경로 표시
            naverMapAPIService.setPath(latlngStart, latlngEnd)
        }

        val startLatLng = LatLng(busStopList[0].locationLatitude, busStopList[0].locationLongitude)
        val goalLatLng = LatLng(busStopList[busStopList.size-1].locationLatitude, busStopList[busStopList.size-1].locationLongitude)
        naverMapAPIService.showPath(startLatLng, goalLatLng)
    }
}