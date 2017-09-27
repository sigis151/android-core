package com.telesoftas.core.common.permission

import android.app.Activity
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat

@Suppress("unused")
class ActivityPermissionRequester(private val activity: Activity) : PermissionRequester {
    override fun isPermissionGranted(permission: String): Boolean {
        val selfPermission = ActivityCompat.checkSelfPermission(activity, permission)
        return selfPermission == PackageManager.PERMISSION_GRANTED
    }

    override fun requestPermission(strings: Array<String>, requestCode: Int) {
        ActivityCompat.requestPermissions(activity, strings, requestCode)
    }

    override fun shouldShowPermissionRationale(permission: String): Boolean =
            ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)
}