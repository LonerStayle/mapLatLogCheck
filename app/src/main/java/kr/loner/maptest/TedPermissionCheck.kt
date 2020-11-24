package kr.loner.maptest

import android.content.Context
import android.widget.Toast
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission

fun tedPermissionCheck(
    context: Context,
    onPermissionGrantedAfterFunction: () -> Unit,
    onPermissionDeniedAfterFunction:() -> Unit?
) {
    val permission = object : PermissionListener {
        override fun onPermissionGranted() {//설정해놓은 위험권한 허용시
            onPermissionGrantedAfterFunction()
        }

        override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
            onPermissionDeniedAfterFunction()
        }
    }

    try{
        TedPermission.with(context)
            .setPermissionListener(permission)
            .setRationaleMessage("앱을 사용하기 위해서\n권한을 허용해주세요")
            .setDeniedMessage("권한이 거부되었습니다. [앱 설정] -> [권한] 항목에서 이용해주세요")
            .setPermissions(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )

            .check()
    }catch (e:Exception){
        e.printStackTrace()
    }



}
