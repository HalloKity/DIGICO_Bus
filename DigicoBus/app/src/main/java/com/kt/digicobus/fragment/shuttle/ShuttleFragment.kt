package com.kt.digicobus.fragment.shuttle

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.kt.digicobus.NaverMapAPI
import com.kt.digicobus.R
import com.kt.digicobus.data.ResultPath
import com.kt.digicobus.databinding.FragmentShuttleBinding
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.PathOverlay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.kt.digicobus.BuildConfig

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

        //spinner
        val items = resources.getStringArray(R.array.shuttle_spinner_array)
        val myAdapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, items)
        binding.spinner.adapter = myAdapter

        //출퇴근 fragment 연결
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navController = navHostFragment.navController
//        OverScrollDecoratorHelper.setUpOverScroll(R.id.nav_host, OverScrollDecoratorHelper.ORIENTATION_VERTICAL)
        NavigationUI.setupWithNavController(binding.tabView, navController)

        // 네이버 지도
        mapView = binding.naverMap
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        return binding.root
    }

    override fun onMapReady(naverMap: NaverMap) {
        // 판교 사옥 위치
        val latlngOffice = LatLng(37.407385, 127.090450)
        val latlngStation = LatLng(37.394769, 127.111194)

        // camera position
        val cameraUpdate = CameraUpdate.scrollTo(latlngOffice)
        naverMap.moveCamera(cameraUpdate)

        // 판교사옥 마커 표시
        val markerOffice = Marker()
        markerOffice.position = latlngOffice
        markerOffice.width = Marker.SIZE_AUTO
        markerOffice.height = Marker.SIZE_AUTO
        markerOffice.captionText = "KT 판교사옥"
        markerOffice.map = naverMap

        // 판교역 마커 표시
        val markerStation = Marker()
        markerStation.position = latlngStation
        markerStation.width = Marker.SIZE_AUTO
        markerStation.height = Marker.SIZE_AUTO
        markerStation.map = naverMap

        // ============ 경로 표시 ===============
        val APIKEY_ID = BuildConfig.NAVER_MAP_CLIENT_ID
        val APIKEY = BuildConfig.NAVER_MAP_CLIENT_SECRET

        val retrofit =
            Retrofit.Builder().baseUrl("https://naveropenapi.apigw.ntruss.com/map-direction/")
                .addConverterFactory(GsonConverterFactory.create()).build()
        val api = retrofit.create(NaverMapAPI::class.java)

        val callgetPath = api.getPath(
            APIKEY_ID,
            APIKEY,
            "${latlngStation.longitude}, ${latlngStation.latitude}",
            "${latlngOffice.longitude}, ${latlngOffice.latitude}",
        )

        callgetPath.enqueue(object : Callback<ResultPath> {
            override fun onResponse(
                call: Call<ResultPath>,
                response: Response<ResultPath>
            ) {
                val path_cords_list = response.body()?.route?.traoptimal

                //경로 그리기 응답바디가 List<List<Double>> 이라서 2중 for문 썼음
                val path = PathOverlay()
                //MutableList에 add 기능 쓰기 위해 더미 원소 하나 넣어둠
                val path_container: MutableList<LatLng>? = mutableListOf(LatLng(0.1, 0.1))
                for (path_cords in path_cords_list!!) {
                    for (path_cords_xy in path_cords?.path!!) {
                        //구한 경로를 하나씩 path_container에 추가해줌
                        path_container?.add(LatLng(path_cords_xy[1], path_cords_xy[0]))
                    }
                }
                //더미원소 드랍후 path.coords에 path들을 넣어줌.
                path.coords = path_container?.drop(1)!!
                path.color = Color.RED
                path.map = naverMap

                // 경로를 보여주도록 카메라 이동
                if (path.coords != null) {
                    val cameraUpdate = CameraUpdate.scrollAndZoomTo(path.coords[path.coords.size/2-8]!!, 12.5)
                        .animate(CameraAnimation.Fly, 3000)
                    naverMap!!.moveCamera(cameraUpdate)
                }
            }

            override fun onFailure(call: Call<ResultPath>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}