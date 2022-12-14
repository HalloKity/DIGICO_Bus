package com.kt.digicobus.fragment.commute

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kt.digicobus.GOGenieApplication
import com.kt.digicobus.R
import com.kt.digicobus.adapter.BusStopListAdapter
import com.kt.digicobus.data.BusStopContent
import com.kt.digicobus.data.data.Companion.choiceRoute
import com.kt.digicobus.data.model.BusEntireRoute
import com.kt.digicobus.databinding.FragmentCommuteBusEntireRouteBinding
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

//통근버스 - 전체 노선 보기
class CommuteBusEntireRouteFragment : Fragment(), OnMapReadyCallback {
    private lateinit var binding: FragmentCommuteBusEntireRouteBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var busStopListAdapter: BusStopListAdapter
    private lateinit var naverMapAPIService : NaverMapAPIService

    private lateinit var ctx: Context
    private var prevMarker : Marker? = null

    private lateinit var busEntireRouteList: MutableList<BusStopContent>

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

        CoroutineScope(Dispatchers.Main).launch {
            getBusEntireRoute(choiceRoute.busId.toInt())

            setAdapter()

            // 네이버 지도
            val mapView = binding.routeMap
            mapView.onCreate(savedInstanceState)
            mapView.getMapAsync(this@CommuteBusEntireRouteFragment)
        }

        return binding.root
    }

    private fun setAdapter() {

        // RecyclerView 객체 생성
        recyclerView = binding.recyclerview
        recyclerView.layoutManager = LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false)
        OverScrollDecoratorHelper.setUpOverScroll(
            recyclerView,
            OverScrollDecoratorHelper.ORIENTATION_VERTICAL
        )

        // 2. Adapter 객체 생성(한 행을 위해 반복 생성할 Layout과 데이터 전달)
        busStopListAdapter =
            BusStopListAdapter(ctx, binding, R.layout.listview_detail_bus_info, busEntireRouteList, ::onClickItem)

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
                        BusStopContent(item.mainPlace, item.departureTime, item.latitude, item.longitude, false)
                    )
                }

            } else {
                Log.d(TAG, "getBusEntireRoute: error code")
            }
        }
    }

    private fun onClickItem(position: Int) {
        val clickedItem = busEntireRouteList[position]
        val clickedItemLat = LatLng(clickedItem.locationLatitude, clickedItem.locationLongitude)

        if (prevMarker != null) {
            naverMapAPIService.removeMarker(prevMarker!!)
        }
        prevMarker = naverMapAPIService.setMarker(clickedItemLat, clickedItem.busStopLocation)
        naverMapAPIService.setCameraPositionAndZoom(clickedItemLat, 16.0)
    }

    override fun onMapReady(naverMap: NaverMap) {
        naverMapAPIService = NaverMapAPIService(naverMap)

        for (i in 1 until busEntireRouteList.size) {
            val latlngStart = LatLng(busEntireRouteList[i - 1].locationLatitude, busEntireRouteList[i - 1].locationLongitude)
            val latlngEnd = LatLng(busEntireRouteList[i].locationLatitude, busEntireRouteList[i].locationLongitude)

            // 경로 표시
            naverMapAPIService.setPath(latlngStart, latlngEnd)
        }

        val startLatLng = LatLng(busEntireRouteList[0].locationLatitude, busEntireRouteList[0].locationLongitude)
        val goalLatLng = LatLng(busEntireRouteList[busEntireRouteList.size-1].locationLatitude, busEntireRouteList[busEntireRouteList.size-1].locationLongitude)
        naverMapAPIService.showPath(startLatLng, goalLatLng)
    }
}