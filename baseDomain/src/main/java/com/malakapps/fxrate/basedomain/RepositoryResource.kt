package com.malakapps.fxrate.basedomain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RepositoryResource<in Input, out Output>(
    private val remoteFetch: suspend (Input) -> Output?,
    private val localFetch: suspend (Input) -> Output?,
    private val localStore: suspend (Output) -> Unit,
) {

    suspend fun query(args: Input): Flow<Output?> = flow {
        fetchFromLocal(args)?.run { emit(this) } ?: fetchFromRemote(args).run { emit(this) }
    }

    private suspend fun fetchFromLocal(args: Input) = kotlin.runCatching {
        localFetch(args)
    }.getOrNull()

    private suspend fun fetchFromRemote(args: Input) = kotlin.run {
        remoteFetch(args)
    }?.also {
        kotlin.runCatching {
            localStore(it)
        }
    }
}
