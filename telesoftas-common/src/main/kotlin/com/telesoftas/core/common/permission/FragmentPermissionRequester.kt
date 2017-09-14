package com.telesoftas.core.common.permission

import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment

@Suppress("unused")
class FragmentPermissionRequester(private val fragment: Fragment) : PermissionRequester {
    override fun isPermissionGranted(permission: String): Boolean {
        val selfPermission = ActivityCompat.checkSelfPermission(fragment.context, permission)
        return selfPermission == PackageManager.PERMISSION_GRANTED
    }

    override fun requestPermission(strings: Array<String>, requestCode: Int) {
        fragment.requestPermissions(strings, requestCode)
    }

    override fun shouldShowPermissionRationale(permission: String): Boolean =
            fragment.shouldShowRequestPermissionRationale(permission)
}