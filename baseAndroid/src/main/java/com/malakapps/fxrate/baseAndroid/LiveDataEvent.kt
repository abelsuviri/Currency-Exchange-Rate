package com.malakapps.fxrate.baseAndroid

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.io.Serializable

open class LiveDataEvent<out T>(private val content: T): Serializable {

    var hasBeenHandled = false
        private set

    fun consume(): T? = if (hasBeenHandled) {
        null
    } else {
        hasBeenHandled = true
        content
    }
}

fun <T : Any> LiveData<LiveDataEvent<T>>.consume(owner: LifecycleOwner, onChanged: (T) -> Unit) =
    this.observe(owner, Observer {
        it.consume()?.let { value ->
            onChanged(value)
        }
    })

fun <T : Any> LiveData<LiveDataEvent<T>>.consume(owner: LifecycleOwner, fresh: Boolean, onChanged: (T) -> Unit) {
    if(fresh) value?.consume()
    return this.consume(owner, onChanged)
}
