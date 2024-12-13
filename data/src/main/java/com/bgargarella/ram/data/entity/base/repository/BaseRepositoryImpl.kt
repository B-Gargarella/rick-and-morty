package com.bgargarella.ram.data.entity.base.repository

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.TRANSPORT_BLUETOOTH
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_ETHERNET
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import androidx.paging.PagingConfig
import com.bgargarella.ram.domain.base.model.Result
import com.bgargarella.ram.domain.base.model.Result.EmptyState
import com.bgargarella.ram.domain.base.model.Result.Loading
import com.bgargarella.ram.domain.base.model.Result.Offline
import com.bgargarella.ram.domain.base.model.Result.Success
import com.bgargarella.ram.domain.base.model.Result.Unknown
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class BaseRepositoryImpl(@ApplicationContext private val context: Context) {

    protected val pagingConfig: PagingConfig = PagingConfig(pageSize = 20)

    protected fun <T, V, W : Any> getEntity(
        getLocal: suspend () -> V?,
        getRemote: suspend () -> Response<T>,
        getData: (T) -> V,
        saveLocal: (V) -> Unit,
        getDomain: (V) -> W
    ): Flow<Result<W>> =
        flow {
            emit(Loading())
            val domain: W? = getLocal()?.let(getDomain::invoke)
            domain?.let { emit(Success(it)) }
            emit(
                if (isActiveNerwork) {
                    try {
                        getRemote().run {
                            if (isSuccessful) {
                                body()
                                    ?.let(getData::invoke)
                                    ?.also(saveLocal::invoke)
                                    ?.let(getDomain::invoke)
                                    ?.let { Success(data = it) } ?: EmptyState()
                            } else {
                                EmptyState()
                            }
                        }
                    } catch (e: SocketTimeoutException) {
                        Offline()
                    } catch (e: UnknownHostException) {
                        Offline()
                    } catch (e: IOException) {
                        Offline()
                    } catch (e: Exception) {
                        Unknown(message = e.message.toString())
                    }
                } else {
                    if (domain == null) {
                        EmptyState()
                    } else {
                        Success(data = domain)
                    }
                }
            )
        }

    private val isActiveNerwork: Boolean =
        (context.getSystemService(CONNECTIVITY_SERVICE) as? ConnectivityManager)?.let { cm ->
            cm.getNetworkCapabilities(cm.activeNetwork)?.let { capabilities ->
                listOf(
                    TRANSPORT_BLUETOOTH,
                    TRANSPORT_CELLULAR,
                    TRANSPORT_ETHERNET,
                    TRANSPORT_WIFI
                ).any(capabilities::hasTransport)
            }
        } ?: false

    protected suspend fun <T> getEntities(
        ids: String,
        singleEntity: suspend () -> T?,
        multipleEntities: suspend () -> List<T>
    ): List<T> =
        if (ids.split(",").size == 1) {
            singleEntity.invoke()?.let(::listOf) ?: emptyList()
        } else {
            multipleEntities.invoke()
        }
}