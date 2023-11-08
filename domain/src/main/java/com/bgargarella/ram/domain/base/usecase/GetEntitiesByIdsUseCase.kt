package com.bgargarella.ram.domain.base.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.transform

open class GetEntitiesByIdsUseCase {

    operator fun <T, V : Any> invoke(
        getLocalModel: () -> Flow<T>,
        getIds: (T) -> List<Int>,
        getRemoteEntities: (List<Int>) -> Flow<PagingData<V>>,
    ): Flow<PagingData<V>> =
        getLocalModel()
            .transform { model: T ->
                val ids: List<Int> = getIds(model)
                emit(ids)
            }
            .transform { ids ->
                val pagingData: Flow<PagingData<V>> = getRemoteEntities(ids)
                emit(pagingData.first())
            }

    fun <T, V : Any> invokeTest(
        getLocalModel: () -> Flow<T>,
        getIds: (T) -> List<Int>,
        getRemoteEntities: (List<Int>) -> Flow<PagingData<V>>,
    ): Flow<PagingData<V>> =
        getLocalModel()
            .transform { model: T ->
                val ids: List<Int> = getIds(model)
                emit(ids)
            }
            .transform { ids ->
                val pagingData: Flow<PagingData<V>> = getRemoteEntities(ids)
                emit(pagingData.first())
            }
}