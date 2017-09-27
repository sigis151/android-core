package com.telesoftas.core.common.permission

interface PermissionRequester {
    fun isPermissionGranted(permission: String): Boolean

    fun requestPermission(strings: Array<String>, requestCode: Int)

    fun shouldShowPermissionRationale(permission: String): Boolean
}