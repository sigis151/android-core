package com.telesoftas.core.common.permission

import android.content.pm.PackageManager
import com.telesoftas.core.common.permission.PermissionController.PermissionListener
import java.util.concurrent.ConcurrentHashMap

class DefaultPermissionController(private val requester: PermissionRequester)
    : PermissionController {
    private val requests = ConcurrentHashMap<Int, PermissionListener>()

    override fun hasPermission(permission: String): Boolean =
            requester.isPermissionGranted(permission)

    override fun requestPermission(
            permission: String,
            requestCode: Int,
            listener: PermissionListener
    ) {
        grantPermissionOrInvokeAction(permission, listener) {
            requests.put(requestCode, listener)
            requester.requestPermission(arrayOf(permission), requestCode)
        }
    }

    override fun requestPermissionWithRationale(
            permission: String,
            requestCode: Int,
            listener: PermissionListener
    ) {
        grantPermissionOrInvokeAction(permission, listener) {
            if (shouldExplain(permission)) {
                listener.onRationaleRequest(permission)
            } else {
                requests.put(requestCode, listener)
                requester.requestPermission(arrayOf(permission), requestCode)
            }
        }
    }

    private fun grantPermissionOrInvokeAction(
            permission: String,
            listener: PermissionListener,
            action: () -> Unit
    ) {
        if (hasPermission(permission)) {
            listener.onPermissionGranted(permission)
        } else {
            action()
        }
    }

    private fun shouldExplain(permission: String): Boolean =
            requester.shouldShowPermissionRationale(permission)

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        for (i in permissions.indices) {
            val permission = permissions[i]
            val listener = requests.getOrElse(requestCode) { object : PermissionListener {} }
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                listener.onPermissionGranted(permission)
            } else {
                if (shouldExplain(permission)) {
                    listener.onPermissionDenied(permission)
                } else {
                    listener.onPermissionRequestFailed(permission)
                }
            }
        }
        requests.remove(requestCode)
    }
}