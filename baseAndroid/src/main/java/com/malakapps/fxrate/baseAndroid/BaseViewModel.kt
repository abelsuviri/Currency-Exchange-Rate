package com.malakapps.fxrate.baseAndroid

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {
    companion object {
        const val LOADING_SPINNER_DELAY = 500L
    }

    private var spinnerJob: Job? = null

    private val _coroutineJobFinished = MutableLiveData(true)

    private val _dialogSpinnerVisible = MutableLiveData(false)
    val dialogSpinnerVisible: LiveData<Boolean>
        get() = _dialogSpinnerVisible

    @Synchronized
    fun switchSpinnerVisibility(shouldDisplay: Boolean) {
        if (!shouldDisplay) {
            spinnerJob?.cancel()
            spinnerJob = null
            _dialogSpinnerVisible.value = false
        } else if (spinnerJob == null && _dialogSpinnerVisible.value != true) {
            spinnerJob = viewModelScope.launch {
                delay(LOADING_SPINNER_DELAY)
                _dialogSpinnerVisible.value = true
            }
        }
    }

    fun <T> Flow<T>.bindSpinner(lockExpression: Boolean = true) =
        this.onStart {
            _coroutineJobFinished.value = lockExpression.not()
            switchSpinnerVisibility(true)
        }
            .onCompletion {
                _coroutineJobFinished.value = true
                switchSpinnerVisibility(false)
            }
}
