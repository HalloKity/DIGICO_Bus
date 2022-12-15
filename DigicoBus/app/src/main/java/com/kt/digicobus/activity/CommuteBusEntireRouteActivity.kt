package com.kt.digicobus.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kt.digicobus.GOGenieApplication
import com.kt.digicobus.R
import com.kt.digicobus.adapter.BusStopListAdapter
import com.kt.digicobus.data.BusStopContent
import com.kt.digicobus.data.data
import com.kt.digicobus.data.model.BusEntireRoute
import com.kt.digicobus.databinding.ActivityCommuteBusEntireRouteBinding
import com.kt.digicobus.fragment.commute.TAG
import com.kt.digicobus.naverMap.NaverMapAPIService
import com.kt.digicobus.service.CommuteService
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper

class CommuteBusEntireRouteActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityCommuteBusEntireRouteBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var busStopListAdapter: BusStopListAdapter
    private lateinit var naverMapAPIService : NaverMapAPIService

    private var prevMarker : Marker? = null

    private lateinit var busEntireRouteList: MutableList<BusStopContent>

    private var needBusMarker = false
    private var selectedStationId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommuteBusEntireRouteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        needBusMarker = intent.getBooleanExtra("needBusMarker", false)
        selectedStationId = intent.getStringExtra("selectedStationId").toString()

        // 뒤로 가기
        binding.btnBack.setOnClickListener {
            finish()
        }

        CoroutineScope(Dispatchers.Main).launch {
            getBusEntireRoute(data.choiceRoute.busId.toInt())
            setAdapter()

            // 네이버 지도
            val mapView = binding.routeMap
            mapView.onCreate(savedInstanceState)
            mapView.getMapAsync(this@CommuteBusEntireRouteActivity)
        }
    }

    private fun setAdapter() {

        // RecyclerView 객체 생성
        recyclerView = binding.recyclerview
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        OverScrollDecoratorHelper.setUpOverScroll(
            recyclerView,
            OverScrollDecoratorHelper.ORIENTATION_VERTICAL
        )

        // 2. Adapter 객체 생성(한 행을 위해 반복 생성할 Layout과 데이터 전달)
        busStopListAdapter =
            BusStopListAdapter(this, R.layout.listview_detail_bus_info, busEntireRouteList, ::onClickItem)

        // 3. RecyclerView와 Adapter 연결
        recyclerView.adapter = busStopListAdapter
    }

    private suspend fun getBusEntireRoute(busId: Int){
        withContext(Dispatchers.IO) {
            val service = GOGenieApplication.retrofit.create(CommuteService::class.java)
            val response = service.selectBusEntireRoute(busId).execute()

            if (response.code() == 200) {
                val resp = response.body()
                val resultList = (resp as List<BusEntireRoute>).toMutableList()

                busEntireRouteList = mutableListOf()
                resultList.forEach { item ->
                    busEntireRouteList.add(
                        BusStopContent(item.mainPlace, item.departureTime, item.latitude, item.longitude,
                            (item.stationId == selectedStationId))
                    )
                }

            } else {
                Log.d(TAG, "getBusEntireRoute: error code")
            }
        }
    }

    private fun onClickItem(position: Int, isClick: Boolean) {
        val clickedItem = busEntireRouteList[position]
        val clickedItemLat = LatLng(clickedItem.locationLatitude, clickedItem.locationLongitude)

        if (prevMarker != null) {
            naverMapAPIService.removeMarker(prevMarker!!)
        }
        if(isClick) {
            prevMarker = naverMapAPIService.setMarker(clickedItemLat, clickedItem.busStopLocation)
            naverMapAPIService.setCameraPositionAndZoom(clickedItemLat, 16.0)
        } else {
            // 클릭된게 없으면 전체 노선 보여주기
            showEntireRoute()
        }
    }

    private fun showEntireRoute() {
        // 클릭된게 없으면 전체 노선 보여주기
        val startLatLng = LatLng(busEntireRouteList[0].locationLatitude, busEntireRouteList[0].locationLongitude)
        val goalLatLng = LatLng(busEntireRouteList[busEntireRouteList.size-1].locationLatitude, busEntireRouteList[busEntireRouteList.size-1].locationLongitude)

        naverMapAPIService.showPath(startLatLng, goalLatLng)
    }

    override fun onMapReady(naverMap: NaverMap) {
        naverMapAPIService = NaverMapAPIService(naverMap, needBusMarker)

        for (i in 1 until busEntireRouteList.size) {
            val latlngStart = LatLng(busEntireRouteList[i - 1].locationLatitude, busEntireRouteList[i - 1].locationLongitude)
            val latlngEnd = LatLng(busEntireRouteList[i].locationLatitude, busEntireRouteList[i].locationLongitude)

            // 경로 표시
            naverMapAPIService.setPath(latlngStart, latlngEnd)
        }

        val clickedIdx = busEntireRouteList.indexOfFirst { it.isClick }
        onClickItem(clickedIdx, clickedIdx != -1)
    }
}