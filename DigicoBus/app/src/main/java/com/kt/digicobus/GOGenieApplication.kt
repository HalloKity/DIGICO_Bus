package com.kt.digicobus

import android.app.Application
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type

class GOGenieApplication: Application() {
    // Application 인스턴스는 앱이 최초 실행될 때 생성되거나
    // 앱 프로세스 소멸로 인해 인스턴스가 소멸된 후 앱이 다시 실행될 때 재생성된다.
    // Manifest에 등록해야 한다.

    private val BOARD_URL = "http://192.168.0.19:7777"

    companion object {
        // 전역변수 문법을 통해 Retrofit 인스턴스를 앱 실행 시 1번만 생성하여 사용 (싱글톤 객체)
        lateinit var retrofit: Retrofit
    }

    override fun onCreate() {
        super.onCreate()

        // 앱이 처음 생성되는 순간, retrofit 인스턴스를 생성
        retrofit = Retrofit.Builder()
            .baseUrl(BOARD_URL)
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    }
}

/**
 * 비어있는(length=0)인 Response를 받았을 경우 처리
 */
class NullOnEmptyConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(type: Type?, annotations: Array<Annotation>?, retrofit: Retrofit?): Converter<ResponseBody, *>? {
        val delegate = retrofit!!.nextResponseBodyConverter<Any>(this, type!!, annotations!!)
        return Converter<ResponseBody, Any> {
            if (it.contentLength() == 0L) return@Converter null
            delegate.convert(it)
        }
    }
}