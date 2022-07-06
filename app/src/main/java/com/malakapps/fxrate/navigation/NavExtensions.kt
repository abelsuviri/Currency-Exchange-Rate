package com.malakapps.fxrate.navigation

import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.malakapps.fxrate.baseAndroid.LiveDataEvent
import com.malakapps.fxrate.baseAndroid.consume
import java.io.Serializable

fun <T : Serializable> NavController.postResult(key: String, value: T, destinationId: Int? = null) {
    destinationId?.let {
        postResultToId(key, value, destinationId)
    } ?: previousBackStackEntry?.savedStateHandle?.set(key, LiveDataEvent(value))
}

private fun <T : Serializable> NavController.postResultToId(key: String, value: T, destinationId: Int) {
    runCatching {
        getBackStackEntry(destinationId)
    }.getOrNull()?.savedStateHandle?.set(key, LiveDataEvent(value))
}

fun <T : Serializable> NavController.subscribeResultForKey(
    owner: LifecycleOwner,
    key: String,
    callback: (value: T) -> Unit
) {
    currentBackStackEntry?.savedStateHandle?.let { savedStateHandle ->
        savedStateHandle.remove<Unit>(key)
        savedStateHandle.getLiveData<LiveDataEvent<T>>(key).consume(owner, callback)
    }
}

fun NavController.unsubscribeKey(owner: LifecycleOwner, key: String) {
    currentBackStackEntry?.savedStateHandle?.let { savedStateHandle ->
        savedStateHandle.getLiveData<LiveDataEvent<Unit>>(key).let { event ->
            event.removeObservers(owner)
            savedStateHandle.remove<Unit>(key)
        }
    }
}

fun <T : Serializable> NavController.observeResultForKey(
    owner: LifecycleOwner,
    key: String,
    callback: (value: T) -> Unit
) {
    currentBackStackEntry?.savedStateHandle
        ?.let { savedStateHandle ->
            savedStateHandle.remove<T>(key)
            savedStateHandle.getLiveData<LiveDataEvent<T>>(key).let { event ->
                event.removeObservers(owner)
                event.consume(owner) {
                    savedStateHandle.remove<T>(key)
                    callback(it)
                }
            }
        }
}
