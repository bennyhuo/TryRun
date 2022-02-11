package com.bennyhuo.kotlin.tryrun

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import java.io.IOException
import java.lang.UnsupportedOperationException
import kotlin.random.Random

class TryRunTest {

    @Test
    fun testCatch() {
        tryRun {
            throw IllegalStateException()
        } catch { e: Exception ->
            assertEquals(e.javaClass, IllegalStateException::class.java)
        }
    }

    @Test
    fun testCatch2() {
        try {
            tryRun {
                throw IllegalStateException()
            } catch { e: IOException ->
                assert(false)
            }
        } catch (e: Exception) {
            assertEquals(e.javaClass, IllegalStateException::class.java)
            return
        }
        assert(false)
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
        tryRun {
            throw data[i].newInstance()
        } catching { e: NullPointerException ->
            assertEquals(i, 0)
        } catching { e: IllegalAccessException ->
            assertEquals(i, 1)
        } catching { e: UnsupportedOperationException ->
            assertEquals(i, 2)
        } catching { e: IOException ->
            assertEquals(i, 3)
        } catching { e: IllegalStateException ->
            assertEquals(i, 4)
        } catching { e: RuntimeException ->
            assertEquals(i, 5)
        } catchAll {
            assertEquals(i, -1)
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
}