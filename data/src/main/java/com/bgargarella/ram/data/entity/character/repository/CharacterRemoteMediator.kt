package com.bgargarella.ram.data.entity.character.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.LoadType.REFRESH
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.bgargarella.ram.data.api.APIService
import com.bgargarella.ram.data.entity.base.model.BasePageResponse
import com.bgargarella.ram.data.entity.base.repository.BaseRemoteMediator
import com.bgargarella.ram.data.entity.character.mapper.toModel
import com.bgargarella.ram.data.entity.character.model.CharacterModel
import com.bgargarella.ram.data.entity.character.model.CharacterResponse
import com.bgargarella.ram.data.db.RamDB
import retrofit2.Response

@OptIn(ExperimentalPagingApi::class)
class CharacterRemoteMediator(
    private val db: RamDB,
    private val service: APIService,
) : RemoteMediator<Int, CharacterModel>(), BaseRemoteMediator<CharacterResponse> {

    override suspend fun initialize(): InitializeAction =
        super<BaseRemoteMediator>.initialize()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterModel>,
    ): MediatorResult = loadMediatorResult(loadType, state)

    override suspend fun getResponse(page: Int): Response<BasePageResponse<CharacterResponse>> =
        service.getCharacters(page = page)

    override suspend fun saveResponse(
        loadType: LoadType,
        response: Response<BasePageResponse<CharacterResponse>>,
    ) {
        db.apply {
            withTransaction {
                characterDao().apply {
                    if (loadType == REFRESH) {
                        deleteAll()
                    }
                    saveAll(response.getResults { it.toModel() })
                }
            }
        }
    }
}