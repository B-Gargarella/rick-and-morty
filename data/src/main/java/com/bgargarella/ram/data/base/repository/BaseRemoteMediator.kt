package com.bgargarella.ram.data.base.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator.InitializeAction
import androidx.paging.RemoteMediator.InitializeAction.LAUNCH_INITIAL_REFRESH
import androidx.paging.RemoteMediator.MediatorResult
import androidx.paging.RemoteMediator.MediatorResult.Error
import androidx.paging.RemoteMediator.MediatorResult.Success
import com.bgargarella.ram.data.base.model.BaseModel
import com.bgargarella.ram.data.base.model.BaseResponse
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

interface BaseRemoteMediator<T> {

    val STARTING_PAGE_INDEX: Int
        get() = 1

    @OptIn(ExperimentalPagingApi::class)
    suspend fun initialize(): InitializeAction = LAUNCH_INITIAL_REFRESH

    suspend fun getResponse(page: Int): Response<BaseResponse<T>>

    suspend fun saveResponse(
        loadType: LoadType,
        response: Response<BaseResponse<T>>,
    )

    fun <T, V> Response<BaseResponse<T>>?.getResults(action: (T) -> V): List<V> =
        this?.body()?.results.orEmpty().map(action::invoke)

    fun <T> Response<BaseResponse<T>>?.isEndOfPaginationReached(): Boolean =
        this?.body()?.info?.next == null

    fun <T : BaseModel> PagingState<Int, T>.getAppendPage(): Int {
        val lastItem = lastItemOrNull()
        return if (lastItem == null) {
            STARTING_PAGE_INDEX
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
            LoadType.REFRESH -> STARTING_PAGE_INDEX
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