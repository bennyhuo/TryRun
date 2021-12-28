package com.bennyhuo.kotlin.tryrun

import kotlin.coroutines.cancellation.CancellationException

/**
 * Created by benny at 2021/12/21 11:54 AM.
 */

@JvmInline
value class TryRunResult(val throwable: Throwable?)

inline fun tryRun(block: () -> Unit): TryRunResult {
    return try {
        block()
        TryRunResult(null)
    } catch (e: Throwable) {
        TryRunResult(e)
    }
}

inline infix fun <reified T : Throwable> TryRunResult.catch(block: (t: T) -> Unit) {
    if (throwable is CancellationException) throw throwable
    if (throwable is T) {
        block(throwable)
    }
}

inline infix fun TryRunResult.catchAll(block: (t: Throwable) -> Unit) {
    if (throwable is CancellationException) throw throwable
    if (throwable != null) block(throwable)
}

inline infix fun <reified T : Throwable> TryRunResult.catching(block: (t: T) -> Unit): TryRunResult {
    if (throwable is T) {
        block(throwable)
        return TryRunResult(null)
    } else {
        return this
    }
}

inline infix fun TryRunResult.finally(block: () -> Unit) {
    block()
    if (throwable != null) throw throwable
}