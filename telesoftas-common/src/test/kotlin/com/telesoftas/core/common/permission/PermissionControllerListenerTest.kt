package com.telesoftas.core.common.permission

import org.junit.Test

class PermissionControllerListenerTest {
    private val listener = object : PermissionController.PermissionListener {}

    @Test
    fun onRationaleRequest_neverCausesExceptions() {
        listener.onRationaleRequest(TEST_PERMISSION)
    }

    @Test
    fun onPermissionGranted_neverCausesExceptions() {
        listener.onPermissionGranted(TEST_PERMISSION)
    }

    @Test
    fun onPermissionDenied_neverCausesExceptions() {
        listener.onPermissionDenied(TEST_PERMISSION)
    }

    @Test
    fun onPermissionRequestFailed_neverCausesExceptions() {
        listener.onPermissionRequestFailed(TEST_PERMISSION)
    }

    companion object {
        private const val TEST_PERMISSION = "permission.test"
    }
}