package com.rickandmorty.core

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

/**
 * Created by Carmelo Iriti
 */

abstract class BaseViewModel(dispatcher : CoroutineContext) : ViewModel(), CoroutineScope {

    var job = SupervisorJob()
    override val coroutineContext: CoroutineContext by lazy { dispatcher }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}