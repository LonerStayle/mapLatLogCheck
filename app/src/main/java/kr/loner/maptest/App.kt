package kr.loner.maptest

import android.app.Application
import com.naver.maps.map.NaverMapSdk

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        NaverMapSdk.getInstance(this).client =
            NaverMapSdk.NaverCloudPlatformClient("h1dx83el1s")
    }
}