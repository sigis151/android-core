package com.telesoftas.core.common.presenter

import com.nhaarman.mockito_kotlin.mock
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class BasePresenterTest {
    private lateinit var presenter: TestBasePresenter
    private val view = mock<Any>()

    @Before
    fun setUp() {
        presenter = TestBasePresenter()
    }

    @Test
    fun getView_defaultsToNull() {
        assertNull("Default view not null", presenter.testView)
    }

    @Test
    fun getView_takeView_returnsTakenView() {
        presenter.takeView(view)

        assertEquals("Modified the taken view", view, presenter.testView)
    }

    @Test
    fun getView_dropView_nullifiesView() {
        presenter.takeView(view)

        assertEquals("Modified the taken view", view, presenter.testView)

        presenter.dropView()

        assertNull("Dropped view not null", presenter.testView)
    }

    @Test
    fun getView_setView_returnsSetView() {
        presenter.testView = view

        assertEquals("Modified the taken view", view, presenter.testView)
    }

    @Test
    fun hasView_trueWhenViewTaken() {
        presenter.takeView(view)

        val actual = presenter.testHasView()

        assertTrue("False with taken view", actual)
    }

    @Test
    fun hasView_defaultToFalse() {
        val actual = presenter.testHasView()

        assertFalse("True by default", actual)
    }

    @Test
    fun hasView_dropView_returnsFalse() {
        presenter.takeView(view)

        val actual = presenter.testHasView()

        assertTrue("False with taken view", actual)

        presenter.dropView()

        val droppedActual = presenter.testHasView()

        assertFalse("True with dropped view", droppedActual)
    }

    @Test
    fun onView_takeView_invokesAction() {
        presenter.takeView(view)

        var actionCalled = false
        presenter.doActionOnView { actionCalled = true }

        assertTrue("Action never called", actionCalled)
    }

    @Test
    fun onView_noView_neverInvokesAction() {
        presenter.doActionOnView { throw IllegalStateException("Action should never be called") }
    }

    @Test
    fun onView_dropView_neverInvokesAction() {
        presenter.takeView(view)

        var actionCalled = false
        presenter.doActionOnView { actionCalled = true }

        assertTrue("Action never called", actionCalled)

        presenter.dropView()
        presenter.doActionOnView { throw IllegalStateException("Action should never be called") }
    }

    class TestBasePresenter : BasePresenter<Any>() {
        var testView: Any?
            get() = super.view
            set(value) {
                super.view = value
            }

        fun testHasView(): Boolean = hasView()

        fun doActionOnView(action: Any.() -> Unit) {
            onView { action() }
        }
    }
}