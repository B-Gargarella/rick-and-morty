package com.bgargarella.ram.domain.base.usecase

import androidx.paging.PagingData
import com.bgargarella.ram.domain.base.model.Result
import com.bgargarella.ram.domain.base.model.Result.EmptyState
import com.bgargarella.ram.domain.base.model.Result.Loading
import com.bgargarella.ram.domain.base.model.Result.Offline
import com.bgargarella.ram.domain.base.model.Result.Success
import com.bgargarella.ram.domain.base.model.Result.Unknown
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

open class GetEntitiesByIdsUseCase {

    private fun List<Int>.getStringIdsList(): String = joinToString(",")

    protected suspend fun <T, V> getEntitiesById(
        getEntity: () -> Flow<Result<T>>,
        getIds: (T) -> List<Int>,
        getEntities: suspend (String) -> List<V>,
    ): Flow<Result<List<V>>> =
        getEntity().transform { result: Result<T> ->
            emit(
                when (result) {
                    is EmptyState -> EmptyState()
                    is Loading -> Loading()
                    is Offline -> Offline()
                    is Unknown -> Unknown(result.message)
                    is Success -> Success(data =
                        getEntities(
                            getIds(result.data)
                                .map { it.toDouble().toInt() }
                                .getStringIdsList()
                        )
                    )
                }
            )
        }

    operator fun <T, V : Any> invoke(
        getLocalModel: () -> Flow<T>,
        getIds: (T) -> List<Int>,
        getRemoteEntities: (String) -> Flow<PagingData<V>>,
    ): Flow<PagingData<V>> =
        getLocalModel().transform { model: T ->
            getRemoteEntities(
                getIds(model).getStringIdsList()
            ).collect {
                emit(it)
            }
        }
}