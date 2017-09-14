package com.telesoftas.core.common.permission

interface PermissionController {
    fun hasPermission(permission: String): Boolean

    fun requestPermission(
            permission: String,
            requestCode: Int,
            listener: PermissionListener
    )

    fun requestPermissionWithRationale(
            permission: String,
            requestCode: Int,
            listener: PermissionListener
    )

    fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    )

    interface PermissionListener {
        fun onRationaleRequest(permission: String) {}

        fun onPermissionGranted(permission: String) {}

        fun onPermissionDenied(permission: String) {}

        fun onPermissionRequestFailed(permission: String) {}
    }
}