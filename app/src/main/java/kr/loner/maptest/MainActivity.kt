package kr.loner.maptest

import android.Manifest
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.PolylineOverlay
import com.naver.maps.map.util.FusedLocationSource

class MainActivity : AppCompatActivity() {

    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 1200
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mapFragment = setMapFragment()
        setMap(mapFragment!!)

    }


    private fun setMapFragment(): MapFragment? {
        return supportFragmentManager.findFragmentById(R.id.mapFragment) as MapFragment?
            ?: MapFragment.newInstance().also {
                supportFragmentManager.beginTransaction().add(R.id.mapFragment, it).commit()
            }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setMap(mapFragment: MapFragment) {
        mapFragment.getMapAsync {
            setPermission()
            setLocation(it)
            setNaverMarker(it)
            setPolyLine(it)
            setCamera(it)

        }
    }

    private fun setCamera(it: NaverMap) {

        fun setCameraUpdate(): CameraUpdate {

            return when {
                markerList.isEmpty() -> {
                    CameraUpdate.scrollAndZoomTo(
                        LatLng(
                            polyLineList.first().latitude,
                            polyLineList.first().longitude
                        ),
                        18.0
                    )
                }
                polyLineList.isEmpty() -> {
                    CameraUpdate.scrollAndZoomTo(
                        LatLng(
                            markerList.first().latitude,
                            markerList.first().longitude
                        ),
                        18.0
                    )

                }

                else -> {
                    CameraUpdate.scrollAndZoomTo(
                        LatLng(
                            markerList.first().latitude,
                            markerList.first().longitude
                        ),
                        18.0
                    )
                }
            }

        }

        it.moveCamera(setCameraUpdate())
    }


    private fun setPolyLine(it: NaverMap) {
        if(polyLineList.isEmpty())
            return

        PolylineOverlay().apply {
            setPattern(10, 5)
            coords = polyLineList
            color = Color.BLACK
            width = 5
            map = it
        }
    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun setPermission() {
        tedPermissionCheck(this, {

            requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                LOCATION_PERMISSION_REQUEST_CODE
            )

        }, {})
    }

    private fun setLocation(it: NaverMap) {
        val fuseLocationSource =
            FusedLocationSource(
                this,
                LOCATION_PERMISSION_REQUEST_CODE
            )
        it.locationSource = fuseLocationSource
        it.locationTrackingMode = LocationTrackingMode.None
    }

    private fun setNaverMarker(it: NaverMap) {
        for (i in markerList.indices) {
            val markerSettingList = List(markerList.size) { Marker() }
            markerSettingList[i].position = markerList[i]
            markerSettingList[i].map = it
            markerSettingList[i].captionText = "${i + 1}번 위치"
        }
    }
}


