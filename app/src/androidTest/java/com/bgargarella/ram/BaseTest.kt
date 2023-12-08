package com.bgargarella.ram

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import com.bgargarella.ram.data.api.APIService
import com.bgargarella.ram.data.db.RamDB
import com.bgargarella.ram.domain.BuildConfig
import com.bgargarella.ram.domain.base.model.BaseEntity
import com.bgargarella.ram.domain.base.model.Result
import com.bgargarella.ram.domain.base.model.Result.Success
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestResult
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import okhttp3.logging.HttpLoggingInterceptor.Level.NONE
import org.junit.After
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.BufferedReader

open class BaseTest {

    protected val context: Context =
        ApplicationProvider
            .getApplicationContext()

    protected val db: RamDB =
        Room.inMemoryDatabaseBuilder(
            context,
            RamDB::class.java
        ).allowMainThreadQueries().build()

    protected val moshi: Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    protected lateinit var service: APIService

    private val instrumentationContext: Context =
        InstrumentationRegistry
            .getInstrumentation()
            .context

    private val dispatcher: TestDispatcher = StandardTestDispatcher()

    protected val testScope: TestScope = TestScope(dispatcher)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun baseSetup() {
        Dispatchers.setMain(dispatcher)

        val interceptor: HttpLoggingInterceptor =
            HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) BODY else NONE
            }

        val okHttpClient: OkHttpClient =
            OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()

        val factory: MoshiConverterFactory =
            MoshiConverterFactory
                .create(moshi)

        val retrofit: Retrofit =
            Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(factory)
                .build()

        service = retrofit.create(APIService::class.java)
    }

    protected fun List<Int>.getIdsList(): List<Int> =
        map { it.toDouble().toInt() }

    protected inline fun <reified T : BaseEntity> Flow<Result<T>>.getObjectResult(
        prefix: String,
        crossinline equalsTo: T.(T) -> Boolean,
    ): TestResult = runTest {
        testScope.launch {
            collect { result: Result<T> ->
                (result as? Success)?.let {
                    val asset: String = getAsset("$prefix/${it.data.id}.json")
                    moshi
                        .adapter(T::class.java)
                        .fromJson(asset)
                        ?.run {
                            assert(result.data.equalsTo(this))
                        }
                }
            }
        }
    }

    protected fun getAsset(filePath: String): String {
        instrumentationContext
            .resources
            .assets
            .open(filePath)
            .run {
                val reader = BufferedReader(reader())
                return StringBuilder().apply {
                    var line = reader.readLine()
                    while (line != null) {
                        append(line)
                        line = reader.readLine()
                    }
                    reader.close()
                }.toString()
            }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun teardown() {
        db.close()
        Dispatchers.resetMain()
        dispatcher.cancel()
    }
}