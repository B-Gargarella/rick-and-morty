package com.bgargarella.ram.data.base.repository

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.TRANSPORT_BLUETOOTH
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_ETHERNET
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import android.util.Log
import com.bgargarella.ram.domain.base.model.Result
import com.bgargarella.ram.domain.base.model.Result.EmptyState
import com.bgargarella.ram.domain.base.model.Result.Loading
import com.bgargarella.ram.domain.base.model.Result.Offline
import com.bgargarella.ram.domain.base.model.Result.Success
import com.bgargarella.ram.domain.base.model.Result.Unknown
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class BaseRepositoryImpl(@ApplicationContext private val context: Context) {

    protected val pageSize: Int = 20

    protected fun <T, V, W> getEntity(
        getLocal: suspend () -> V?,
        getRemote: suspend () -> Response<T>,
        getData: (T) -> V,
        saveLocal: (V) -> Unit,
        getDomain: (V) -> W,
    ): Flow<Result<W>> =
        flow {
            emit(Loading())
            delay(1000)
            /*
            getLocal()?.let { localValue: V ->
                val mappedData: W = getDomain.invoke(localValue)
            }
            val localValue: V? =
            */
            getLocal()?.let(getDomain::invoke)?.let { emit(Success(it)) }
            if (isActiveNerwork) {
                try {
                    val response: Response<T> = getRemote()
                    if (response.isSuccessful) {
                        val entity: T? = response.body()
                        if (entity == null) {
                            emit(EmptyState())
                        } else {
                            saveLocal(getData(entity))
                            getLocal()?.let(getDomain::invoke)?.let { emit(Success(it)) }
                        }
                    } else {
                        emit(EmptyState())
                    }
                } catch (e: SocketTimeoutException) {
                    emit(Offline())
                } catch (e: UnknownHostException) {
                    emit(Offline())
                } catch (e: IOException) {
                    emit(Offline())
                } catch (e: Exception) {
                    Log.e("EXCEPTION", e.toString())
                    emit(Unknown(message = e.message.toString()))
                }
            } else {
                emit(Offline())
            }
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
}