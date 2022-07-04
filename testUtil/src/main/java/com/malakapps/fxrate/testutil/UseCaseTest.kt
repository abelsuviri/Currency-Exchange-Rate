package com.malakapps.fxrate.testutil

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import kotlin.coroutines.ContinuationInterceptor

abstract class UseCaseTest {
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()
}

class MainCoroutineRule : TestWatcher(), TestCoroutineScope by TestCoroutineScope() {
    private val dispatcher = this.coroutineContext[ContinuationInterceptor] as CoroutineDispatcher

    override fun starting(description: Description) {
        super.starting(description)
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description) {
        dispatcher.cancel()
        super.finished(description)
        Dispatchers.resetMain()
    }
}
