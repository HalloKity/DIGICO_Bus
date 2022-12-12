package com.kt.digicobus.naverMap

import android.graphics.Color
import com.kt.digicobus.BuildConfig
import com.kt.digicobus.data.ResultPath
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.LatLngBounds
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.NaverMap
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.PathOverlay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NaverMapAPIService(naverMapParam: NaverMap) {
    private val APIKEY_ID = BuildConfig.NAVER_MAP_CLIENT_ID
    private val APIKEY = BuildConfig.NAVER_MAP_CLIENT_SECRET
    private val naverMap: NaverMap

    init {
        naverMap = naverMapParam
    }

    fun setCameraPosition(latlng: LatLng) {
        val cameraUpdate = CameraUpdate.scrollTo(latlng)
                                .animate(CameraAnimation.Fly, 3000)
        naverMap.moveCamera(cameraUpdate)
    }

    fun setCameraPositionAndZoom(latlng: LatLng, zoom: Double) {
        val cameraUpdate = CameraUpdate.scrollAndZoomTo(latlng, zoom)
                            .animate(CameraAnimation.Fly, 3000)
        naverMap.moveCamera(cameraUpdate)
    }

    fun setMarker(latlng: LatLng, text: String = ""): Marker {
        val marker = Marker()

        marker.position = latlng
        marker.width = Marker.SIZE_AUTO
        marker.height = Marker.SIZE_AUTO
        marker.captionText = text
        marker.map = naverMap

        return marker
    }

    fun removeMarker(prevMarker: Marker) {
        prevMarker.map = null
    }

    fun setPath(start: LatLng, goal: LatLng) {
        val retrofit =
            Retrofit.Builder().baseUrl("https://naveropenapi.apigw.ntruss.com/map-direction/")
                .addConverterFactory(GsonConverterFactory.create()).build()

        val api = retrofit.create(NaverMapAPI::class.java)
        val callgetPath = api.getPath(
            APIKEY_ID,
            APIKEY,
            "${start.longitude}, ${start.latitude}",
            "${goal.longitude}, ${goal.latitude}",
        )

        // api 호출
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
            }

            override fun onFailure(call: Call<ResultPath>, t: Throwable) {
               //주석 지움
            }
        })
    }
    fun showPath(start: LatLng, goal: LatLng) {
        val cameraUpdate = CameraUpdate.fitBounds(LatLngBounds(start, goal), 100)
                            .animate(CameraAnimation.Fly, 3000)
        naverMap.moveCamera(cameraUpdate)
    }
}