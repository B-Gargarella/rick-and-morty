package com.bgargarella.ram.domain.base.model

sealed class Result<T> {
    class EmptyState<T>: Result<T>()
    class Loading<T>: Result<T>()
    class Offline<T>: Result<T>()
    class Unknown<T>(val message: String): Result<T>()
    class Success<T>(val data: T): Result<T>()
}