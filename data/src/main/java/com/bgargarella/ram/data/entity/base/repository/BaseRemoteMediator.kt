package com.bgargarella.ram.data.entity.base.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator.InitializeAction
import androidx.paging.RemoteMediator.InitializeAction.SKIP_INITIAL_REFRESH
import androidx.paging.RemoteMediator.MediatorResult
import androidx.paging.RemoteMediator.MediatorResult.Error
import androidx.paging.RemoteMediator.MediatorResult.Success
import com.bgargarella.ram.data.entity.base.model.BaseModel
import com.bgargarella.ram.data.entity.base.model.BasePageResponse
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

interface BaseRemoteMediator<T> {

    val startPageIndex: Int
        get() = 1

    @OptIn(ExperimentalPagingApi::class)
    suspend fun initialize(): InitializeAction = SKIP_INITIAL_REFRESH

    suspend fun getResponse(page: Int): Response<BasePageResponse<T>>

    suspend fun saveResponse(
        loadType: LoadType,
        response: Response<BasePageResponse<T>>,
    )

    fun <T, V> Response<BasePageResponse<T>>?.getResults(action: (T) -> V): List<V> =
        this?.body()?.results.orEmpty().map(action::invoke)

    fun <T> Response<BasePageResponse<T>>?.isEndOfPaginationReached(): Boolean =
        this?.body()?.info?.next == null

    fun <T : BaseModel> PagingState<Int, T>.getAppendPage(): Int {
        val lastItem = lastItemOrNull()
        return if (lastItem == null) {
            startPageIndex
        } else {
            (lastItem.id / config.pageSize) + 1
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    suspend fun getMediatorResult(
        action: suspend () -> Boolean,
    ): MediatorResult =
        try {
            Success(endOfPaginationReached = action())
        } catch (e: SocketTimeoutException) {
            Error(e)
        } catch (e: UnknownHostException) {
            Error(e)
        } catch (e: IOException) {
            Error(e)
        } catch (e: HttpException) {
            Error(e)
        } catch (e: Exception) {
            Error(e)
        }

    @OptIn(ExperimentalPagingApi::class)
    suspend fun <T : BaseModel> loadMediatorResult(
        loadType: LoadType,
        state: PagingState<Int, T>,
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> startPageIndex
            LoadType.PREPEND -> return Success(endOfPaginationReached = false)
            LoadType.APPEND -> state.getAppendPage()
        }

        return getMediatorResult {
            getResponse(page = page).run {
                saveResponse(loadType, this)
                return@getMediatorResult isEndOfPaginationReached()
            }
        }
    }
}