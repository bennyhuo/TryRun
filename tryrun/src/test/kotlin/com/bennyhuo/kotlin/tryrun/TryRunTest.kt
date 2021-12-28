package com.bennyhuo.kotlin.tryrun

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test

class TryRunTest {

    @Test
    fun testCatching() {
        tryRun {
            throw IllegalStateException()
        } catch { e: Exception ->
            assertEquals(e.javaClass, IllegalStateException::class.java)
        }
    }

    @Test
    fun testFinally() {
        var a = 1
        tryRun {
            throw IllegalStateException()
        } catching { e: Exception ->
            a = 3
        } finally {
            a = 4
        }

        assertEquals(a, 4)

        a = 1
        tryRun {
            a = 2
        } catching { e: Exception ->
            a = 3
        } finally {
            a = 4
        }

        assertEquals(a, 4)
    }

    fun testExpressinos() {

    }
}