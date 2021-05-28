package kr.loner.maptest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.naver.maps.geometry.LatLng
import kotlinx.android.synthetic.main.activity_setting.*

var markerList: MutableList<LatLng> = mutableListOf()
var polyLineList: MutableList<LatLng> = mutableListOf()

class SettingActivity : AppCompatActivity() {

    private var firstPointStart = 0
    private var firstPointEnd = 9
    private var secondPointStart = 9
    private var secondPointEnd = 19

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)



        btn_start.setOnClickListener {


            if (et_marker.text.toString().isEmpty() && et_polyLine.text.toString().isEmpty()
            ) {
                Toast.makeText(this, "경로선 리스트나 혹은 마커리스트 둘중 하나라도 채워주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            markerList.clear()
            polyLineList.clear()

//            try {

            val marker = et_marker.text.toString()
            val polyLine = et_polyLine.text.toString()

            if (marker.isNotEmpty())
                setReplace(marker, markerList)
            if (polyLine.isNotEmpty())
                setReplace(polyLine, polyLineList)

            startActivity(Intent(this, MainActivity::class.java))
//            }catch (e:Exception){
//                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
////                Toast.makeText(this, "숫자나 . , 를 제외한 한글,영어,특수문자 등등이 들어갔는지 확인해보세요", Toast.LENGTH_LONG).show()
//            }

        }

        btn_markerList.setOnClickListener {
            et_marker.text.clear()
        }
        btn_resetPolyLineList.setOnClickListener {
            et_polyLine.text.clear()
        }

    }


    private fun setReplace(newList: String, list: MutableList<LatLng>) {

        val filterString = newList.replace(" ", "")
        val cleanString = filterString.replace(",", "")
        val finalString = cleanString.replace("\n", "")
        val max = finalString.length/19
        Log.d("maxSize", max.toString())
        repeat(max) {
            Log.d("firstStart", firstPointStart.toString())
            Log.d("firstEnd", firstPointEnd.toString())
            val markerLat =
                cleanString.subSequence(firstPointStart, firstPointEnd).toString().toDouble()
            Log.d("checkk", markerLat.toString())
            val markerLong =
                cleanString.subSequence(secondPointStart, secondPointEnd).toString().toDouble()
            Log.d("checkk", markerLong.toString())
            list.add(LatLng(markerLat, markerLong))

            firstPointStart = secondPointEnd
            firstPointEnd = secondPointEnd + 10
            secondPointStart = firstPointEnd
            secondPointEnd = secondPointStart + 10

        }
        firstPointStart = 0
        firstPointEnd = 9
        secondPointStart = 9
        secondPointEnd = 19
    }

}