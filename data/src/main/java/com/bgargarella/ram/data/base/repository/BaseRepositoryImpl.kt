package com.bgargarella.ram.data.base.repository

import com.bgargarella.ram.domain.base.model.Result
import com.bgargarella.ram.domain.base.model.Result.EmptyState
import com.bgargarella.ram.domain.base.model.Result.Loading
import com.bgargarella.ram.domain.base.model.Result.Offline
import com.bgargarella.ram.domain.base.model.Result.Success
import com.bgargarella.ram.domain.base.model.Result.Unknown
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.net.SocketTimeoutException

open class BaseRepositoryImpl {

    protected val pageSize: Int = 20

    protected fun <T, V, W> getEntity(
        getLocal: suspend () -> V?,
        getRemote: suspend () -> Response<T>,
        mappingDataAction: (T) -> V,
        saveLocal: (V) -> Unit,
        mappingDomainAction: (V) -> W,
    ): Flow<Result<W>> =
        flow {
            emit(Loading())
            getLocal()?.let(mappingDomainAction::invoke)?.let { emit(Success(it)) }
            try {
                val response: Response<T> = getRemote()
                if (response.isSuccessful) {
                    val entity: T? = response.body()
                    if (entity == null) {
                        emit(EmptyState())
                    } else {
                        saveLocal(mappingDataAction(entity))
                        getLocal()?.let(mappingDomainAction::invoke)?.let { emit(Success(it)) }
                    }
                } else {
                    emit(EmptyState())
                }
            } catch (e: SocketTimeoutException) {
                emit(Offline())
            } catch (e: Exception) {
                emit(Unknown(message = e.message.toString()))
            }
        }
}