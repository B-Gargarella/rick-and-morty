package com.bgargarella.ram.domain.base.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.transform

open class GetEntitiesByIdsUseCase {

    protected suspend fun <T, V> getEntitiesById(
        getEntity: () -> Flow<T>,
        mappingAction: (T) -> List<Int>,
        getEntities: suspend (List<Int>) -> List<V>,
    ): Flow<List<V>> =
        getEntity()
            .transform { entity: T ->
                val ids: List<Int> = mappingAction(entity)
                val entities: List<V> = getEntities(ids)
                emit(entities)
            }

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
}