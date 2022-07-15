package com.malakapps.fxrate.basedomain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RepositoryResource<in Input, out Output>(
    private val remoteFetch: suspend (Input) -> Output?,
    private val localFetch: suspend (Input) -> Output?,
    private val localStore: suspend (Output) -> Unit,
    private val localDelete: suspend () -> Unit,
    private val refreshControl: RefreshControl = RefreshControl(),
) : RefreshControl.Listener, ITimeLimitedResource by refreshControl  {
    init {
        refreshControl.addListener(this)
    }

    suspend fun query(args: Input): Flow<Output?> = flow {
        fetchFromLocal(args)?.run { emit(this) }
        if (refreshControl.isExpired()) {
            fetchFromRemote(args).run { emit(this) }
        }
    }

    override suspend fun cleanup() {
        deleteLocal()
    }

    private suspend fun fetchFromLocal(args: Input) = kotlin.runCatching {
        localFetch(args)
    }.getOrNull()

    private suspend fun deleteLocal() = kotlin.runCatching {
        localDelete()
    }.getOrNull()

    private suspend fun fetchFromRemote(args: Input) = kotlin.run {
        remoteFetch(args)
    }?.also{
        kotlin.runCatching {
            localStore(it)
            refreshControl.refresh()
        }
    }
}
