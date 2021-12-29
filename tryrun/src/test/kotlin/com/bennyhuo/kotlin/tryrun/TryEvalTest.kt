package com.bennyhuo.kotlin.tryrun

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import java.io.IOException
import java.lang.UnsupportedOperationException
import kotlin.random.Random

class TryEvalTest {

    @Test
    fun testCatch() {
        val result = tryEval {
            throw IllegalStateException()
            1
        } catch { e: Exception ->
            2
        }

        assertEquals(result, 2)
    }

    @Test
    fun testCatching() {
        val data = listOf(
            NullPointerException::class.java,
            IllegalAccessException::class.java,
            UnsupportedOperationException::class.java,
            IOException::class.java,
            IllegalStateException::class.java,
            RuntimeException::class.java
        )

        val i = Random.nextInt(0, data.size)
        val result = tryEval {
            throw data[i].newInstance()
            -1
        } catching { e: NullPointerException ->
            0
        } catching { e: IllegalAccessException ->
            1
        } catching { e: UnsupportedOperationException ->
            2
        } catching { e: IOException ->
            3
        } catching { e: IllegalStateException ->
            4
        } catchAll {
            -1
        }

        assertEquals(i, result)
    }

    @Test
    fun testFinally() {
        val a = tryEval {
            throw IllegalStateException()
            1
        } catching { e: Exception ->
            2
        } finally {
            3
        }

        assertEquals(a, 2)

        val b = tryEval {
            1
        } catching { e: Exception ->
            2
        } finally {
            3
        }

        assertEquals(b, 1)
    }
}