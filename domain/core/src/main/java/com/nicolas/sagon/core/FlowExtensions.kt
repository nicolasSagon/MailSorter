package com.nicolas.sagon.core

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.runBlocking

inline fun <T, reified E> Flow<T>.retryOnceWithRefreshToken(
    crossinline refreshMethod: () -> Flow<Unit>,
): Flow<T> {
    return this.retryWhen { cause, attempt ->
        if (cause is E && attempt < 1) {
            runBlocking {
                refreshMethod().collectLatest {}
            }
            return@retryWhen true
        } else {
            return@retryWhen false
        }
    }
}