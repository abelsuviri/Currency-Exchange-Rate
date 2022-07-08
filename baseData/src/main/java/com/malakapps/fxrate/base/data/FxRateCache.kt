package com.malakapps.fxrate.base.data

abstract class FxRateCache {
    protected suspend fun <T> runQuery(query: suspend () -> T) =
        query.runCatching { invoke() }.getOrNull()
}
