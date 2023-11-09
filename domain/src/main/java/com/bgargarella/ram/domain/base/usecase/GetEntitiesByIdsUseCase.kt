package com.bgargarella.ram.domain.base.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.transform
import com.bgargarella.ram.domain.base.model.Result
import com.bgargarella.ram.domain.base.model.Result.EmptyState
import com.bgargarella.ram.domain.base.model.Result.Offline
import com.bgargarella.ram.domain.base.model.Result.Unknown
import com.bgargarella.ram.domain.base.model.Result.Success
import com.bgargarella.ram.domain.base.model.Result.Loading

open class GetEntitiesByIdsUseCase {

    protected suspend fun <T, V> getEntitiesById(
        getEntity: () -> Flow<Result<T>>,
        mappingAction: (T) -> List<Int>,
        getEntities: suspend (List<Int>) -> List<V>,
    ): Flow<Result<List<V>>> =
        getEntity()
            .transform { result: Result<T> ->
                when (result) {
                    is EmptyState -> emit(EmptyState())
                    is Loading -> emit(Loading())
                    is Offline -> emit(Offline())
                    is Unknown -> emit(Unknown(result.message))
                    is Success -> {
                        val ids: List<Int> = mappingAction(result.data)
                        val entities: List<V> = getEntities(ids)
                        emit(Success(data = entities))
                    }
                }
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