package com.telesoftas.core.common.permission

import android.content.pm.PackageManager
import com.nhaarman.mockito_kotlin.*
import com.telesoftas.core.common.permission.PermissionController.PermissionListener
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class DefaultPermissionControllerTest {
    private lateinit var controller: DefaultPermissionController
    private val requester = mock<PermissionRequester>()
    private val listener = mock<PermissionListener>()

    @Before
    fun setUp() {
        controller = DefaultPermissionController(requester)
    }

    @Test
    fun hasPermission_noPermission_alwaysFalse() {
        whenever(requester.isPermissionGranted(PERMISSION)).thenReturn(false)

        val actualResult = controller.hasPermission(PERMISSION)

        assertFalse("Has permission", actualResult)
    }

    @Test
    fun hasPermission_hasPermission_alwaysTrue() {
        whenever(requester.isPermissionGranted(PERMISSION)).thenReturn(true)

        val actualResult = controller.hasPermission(PERMISSION)

        assertTrue("Does not have permission", actualResult)
    }

    @Test
    fun requestPermissionWithRationale_hasPermission_callsListenerPermissionGranted() {
        whenever(requester.isPermissionGranted(PERMISSION)).thenReturn(true)

        controller.requestPermissionWithRationale(PERMISSION, REQUEST_CODE, listener)

        verify(listener).onPermissionGranted(PERMISSION)
    }

    @Test
    fun requestPermissionWithRationale_callsRationaleRequestWhenShouldExplain() {
        whenever(requester.isPermissionGranted(PERMISSION)).thenReturn(false)
        whenever(requester.shouldShowPermissionRationale(PERMISSION)).thenReturn(true)

        controller.requestPermissionWithRationale(PERMISSION, REQUEST_CODE, listener)

        verify(listener).onRationaleRequest(PERMISSION)
    }

    @Test
    fun requestPermissionWithRationale_shouldNotExplain_requestsPermission() {
        whenever(requester.isPermissionGranted(PERMISSION)).thenReturn(false)
        whenever(requester.shouldShowPermissionRationale(PERMISSION)).thenReturn(false)

        controller.requestPermissionWithRationale(PERMISSION, REQUEST_CODE, listener)

        verify(requester).requestPermission(arrayOf(PERMISSION), REQUEST_CODE)
    }

    @Test
    fun requestPermission_hasPermission_callsListenerPermissionGranted() {
        whenever(requester.isPermissionGranted(PERMISSION)).thenReturn(true)

        controller.requestPermission(PERMISSION, REQUEST_CODE, listener)

        verify(listener).onPermissionGranted(PERMISSION)
    }

    @Test
    fun requestPermission_shouldNotExplain_neverCallsRationaleRequest() {
        whenever(requester.isPermissionGranted(PERMISSION)).thenReturn(false)
        whenever(requester.shouldShowPermissionRationale(PERMISSION)).thenReturn(true)

        controller.requestPermission(PERMISSION, REQUEST_CODE, listener)

        verify(listener, never()).onRationaleRequest(PERMISSION)
    }

    @Test
    fun requestPermission_shouldExplain_requestsPermissionWithoutRationale() {
        whenever(requester.isPermissionGranted(PERMISSION)).thenReturn(false)
        whenever(requester.shouldShowPermissionRationale(PERMISSION)).thenReturn(true)

        controller.requestPermission(PERMISSION, REQUEST_CODE, listener)

        verify(listener, never()).onRationaleRequest(PERMISSION)
        verify(requester).requestPermission(arrayOf(PERMISSION), REQUEST_CODE)
    }

    @Test
    fun onRequestPermissionsResult_noPermissionRequests_callsNothing() {
        controller.requestPermissionWithRationale(PERMISSION, REQUEST_CODE, listener)

        verifyZeroInteractions(listener)
    }

    @Test
    fun onRequestPermissionsResult_unknownRequest_callsNothing() {
        setUpPermissionRequest()

        controller.onRequestPermissionsResult(REQUEST_CODE_NOT_FOUND, arrayOf(PERMISSION),
                intArrayOf(PackageManager.PERMISSION_GRANTED))

        verifyZeroInteractions(listener)
    }

    @Test
    fun onRequestPermissionsResult_requestGranted_callsListenerPermissionGranted() {
        setUpPermissionRequest()

        controller.onRequestPermissionsResult(REQUEST_CODE, arrayOf(PERMISSION),
                intArrayOf(PackageManager.PERMISSION_GRANTED))

        verify(listener).onPermissionGranted(PERMISSION)
    }

    @Test
    fun onRequestPermissionsResult_shouldExplainDenied_callsListenerPermissionDenied() {
        setUpPermissionRequest()
        whenever(requester.shouldShowPermissionRationale(PERMISSION)).thenReturn(true)

        controller.onRequestPermissionsResult(REQUEST_CODE, arrayOf(PERMISSION),
                intArrayOf(PackageManager.PERMISSION_DENIED))

        verify(listener).onPermissionDenied(PERMISSION)
    }

    @Test
    fun onRequestPermissionsResult_callsFailedWhenRequestDeniedWithoutRationale() {
        setUpPermissionRequest()
        whenever(requester.shouldShowPermissionRationale(PERMISSION)).thenReturn(false)

        controller.onRequestPermissionsResult(REQUEST_CODE, arrayOf(PERMISSION),
                intArrayOf(PackageManager.PERMISSION_DENIED))

        verify(listener).onPermissionRequestFailed(PERMISSION)
    }

    private fun setUpPermissionRequest() {
        whenever(requester.isPermissionGranted(PERMISSION)).thenReturn(false)
        whenever(requester.shouldShowPermissionRationale(PERMISSION)).thenReturn(false)
        controller.requestPermissionWithRationale(PERMISSION, REQUEST_CODE, listener)
    }

    companion object {
        private const val PERMISSION = "permission"
        private const val REQUEST_CODE = 7
        private const val REQUEST_CODE_NOT_FOUND = 8
    }
}