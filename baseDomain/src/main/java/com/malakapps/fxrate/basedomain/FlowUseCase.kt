package com.malakapps.fxrate.basedomain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

abstract class FlowUseCase<in TParam, out TResult>(
    private val dispatcher: CoroutineDispatcher,
) {
    sealed class Result<out TResultModel> {
        companion object {
            fun <TResultModel> ofNullable(result: TResultModel?) = when (result) {
                null -> Failure()
                else -> Success(result)
            }
        }

        data class Success<out TResultModel>(val result: TResultModel) : Result<TResultModel>()
        data class Failure(val error: Throwable? = null) : Result<Nothing>()
    }

    suspend operator fun invoke(param: TParam) =
        performAction(param)
            .catch { exception ->
                emit(Result.Failure(exception))
            }
            .flowOn(dispatcher)

    fun call(param: TParam) = flow { emitAll(invoke(param)) }

    protected abstract suspend fun performAction(param: TParam): Flow<Result<TResult>>
}

fun <R> FlowUseCase<Unit, R>.call() = call(Unit)
val <T> FlowUseCase.Result<T>.data: T? get() = (this as? FlowUseCase.Result.Success<T>)?.result
