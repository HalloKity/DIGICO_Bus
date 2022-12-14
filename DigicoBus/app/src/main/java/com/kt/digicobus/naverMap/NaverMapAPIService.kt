package com.kt.digicobus.naverMap

import android.graphics.Color
import com.kt.digicobus.BuildConfig
import com.kt.digicobus.R
import com.kt.digicobus.data.ResultPath
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.LatLngBounds
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.NaverMap
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.overlay.PathOverlay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NaverMapAPIService(naverMapParam: NaverMap, val needBusMarker: Boolean = false) {
    private val APIKEY_ID = BuildConfig.NAVER_MAP_CLIENT_ID
    private val APIKEY = BuildConfig.NAVER_MAP_CLIENT_SECRET
    private val naverMap: NaverMap

    private var busMarker: Marker = Marker()

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

    fun setBusMarker(middleLatLng : LatLng) {
        busMarker.map = null

        val marker = Marker()
        marker.position = middleLatLng
        marker.width = 100
        marker.height = 100
        marker.icon = OverlayImage.fromResource(R.drawable.icon_bus)
        marker.map = naverMap

        busMarker = marker
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

        // api ??????
        callgetPath.enqueue(object : Callback<ResultPath> {
            override fun onResponse(
                call: Call<ResultPath>,
                response: Response<ResultPath>
            ) {
                val path_cords_list = response.body()?.route?.traoptimal

                //?????? ????????? ??????????????? List<List<Double>> ????????? 2??? for??? ??????
                val path = PathOverlay()
                //MutableList??? add ?????? ?????? ?????? ?????? ?????? ?????? ?????????
                val path_container: MutableList<LatLng>? = mutableListOf(LatLng(0.1, 0.1))
                for (path_cords in path_cords_list!!) {
                    for (path_cords_xy in path_cords?.path!!) {
                        //?????? ????????? ????????? path_container??? ????????????
                        path_container?.add(LatLng(path_cords_xy[1], path_cords_xy[0]))
                    }
                }
                //???????????? ????????? path.coords??? path?????? ?????????.
                path.coords = path_container?.drop(1)!!
                path.color = Color.RED
                path.map = naverMap

                // ?????? ?????? ??????
                if(needBusMarker) setBusMarker(path_container[path_container.size/2])
            }

            override fun onFailure(call: Call<ResultPath>, t: Throwable) {
               //?????? ??????
            }
        })
    }
    fun showPath(start: LatLng, goal: LatLng) {
        val cameraUpdate = CameraUpdate.fitBounds(LatLngBounds(start, goal), 100)
                            .animate(CameraAnimation.Fly, 3000)
        naverMap.moveCamera(cameraUpdate)
    }
}