package com.malakapps.fxrate.basedomain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.toList

suspend fun <T> Flow<T?>.latest(isValid: (T?) -> Boolean = { it != null }): T? {
    var error: Throwable? = null
    return catch { e -> error = e }
        .filter { isValid(it) }
        .toList()
        .lastOrNull()
        ?: error?.run { throw this }
}
