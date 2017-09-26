package com.telesoftas.core.samples

import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).

 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals("Simple math does not work", 4, 2 + 2)
    }

    /**
     * Should not work without mockito-inline in before Mockito releases an in-built system.
     */
    @Test
    fun finalClasses_canBeMocked() {
        val finalClass = mock<FinalClass>()
        given(finalClass.doNothing()).will { }

        tryFinal(finalClass)

        verify(finalClass).doNothing()
    }

    private fun tryFinal(bundle: FinalClass) {
        bundle.doNothing()
    }

    class FinalClass {
        fun doNothing() {
            // Do nothing
        }
    }
}