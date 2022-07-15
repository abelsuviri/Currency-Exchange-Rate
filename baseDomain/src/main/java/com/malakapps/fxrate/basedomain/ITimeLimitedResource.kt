package com.malakapps.fxrate.basedomain

import java.util.Date

interface ITimeLimitedResource {
    var refreshRate: Long
    val lastUpdate: Date?

    suspend fun evict(cleanup: Boolean = false)
}
