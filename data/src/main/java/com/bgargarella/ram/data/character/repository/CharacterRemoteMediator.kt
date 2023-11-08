package com.bgargarella.ram.data.character.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.LoadType.APPEND
import androidx.paging.LoadType.PREPEND
import androidx.paging.LoadType.REFRESH
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.paging.RemoteMediator.MediatorResult.Error
import androidx.paging.RemoteMediator.MediatorResult.Success
import androidx.room.withTransaction
import com.bgargarella.ram.data.api.APIService
import com.bgargarella.ram.data.base.model.BaseResponse
import com.bgargarella.ram.data.base.model.BaseResponse.Info
import com.bgargarella.ram.data.character.mapper.toCharacterModel
import com.bgargarella.ram.data.character.model.CharacterModel
import com.bgargarella.ram.data.character.model.CharacterResponse
import com.bgargarella.ram.data.db.RamDB
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

@OptIn(ExperimentalPagingApi::class)
class CharacterRemoteMediator(
    private val db: RamDB,
    private val service: APIService,
    private val ids: List<Int>? = null,
) : RemoteMediator<Int, CharacterModel>() {

    private val STARTING_PAGE_INDEX: Int = 1

    override suspend fun initialize(): InitializeAction =
        InitializeAction.LAUNCH_INITIAL_REFRESH

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterModel>,
    ): MediatorResult {
        val page = when (loadType) {
            REFRESH -> STARTING_PAGE_INDEX
            PREPEND -> {
                return Success(endOfPaginationReached = false)
            }

            APPEND -> {
                val lastItem = state.lastItemOrNull()
                if (lastItem == null) {
                    STARTING_PAGE_INDEX
                } else {
                    (lastItem.id / state.config.pageSize) + 1
                }
            }
        }

        return try {
            val entities: List<CharacterResponse>
            val endOfPaginationReached: Boolean

            if (ids.isNullOrEmpty()) {
                val pagedResponse: Response<BaseResponse<CharacterResponse>> =
                    service.getCharacters(page = page)

                val pagedBody: BaseResponse<CharacterResponse>? = pagedResponse.body()

                entities = pagedBody?.results.orEmpty()

                endOfPaginationReached = pagedBody?.info?.next == null
            } else {
                val notPagedResponse: Response<List<CharacterResponse>> =
                    service.getCharacters(ids = ids)

                entities = notPagedResponse.body().orEmpty()

                endOfPaginationReached = true
            }

            db.withTransaction {
                db.characterDao().apply {
                    saveAll(entities.map { it.toCharacterModel() })
                }
            }

            Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: SocketTimeoutException) {
            Error(e)
        } catch (e: IOException) {
            Error(e)
        } catch (e: HttpException) {
            Error(e)
        }
    }
}