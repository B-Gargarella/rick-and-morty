package com.bgargarella.ram

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bgargarella.ram.data.location.repository.LocationRepositoryImpl
import com.bgargarella.ram.domain.location.model.Location
import com.bgargarella.ram.domain.location.repository.LocationRepository
import com.bgargarella.ram.domain.location.usecase.GetLocationUseCase
import com.bgargarella.ram.domain.location.usecase.GetLocationsUseCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LocationTest : BaseTest() {

    private lateinit var repository: LocationRepository

    private lateinit var getLocationsUseCase: GetLocationsUseCase
    private lateinit var getLocationUseCase: GetLocationUseCase

    @Before
    fun setup() {
        baseSetup()

        repository =
            LocationRepositoryImpl(
                context = context,
                db = db,
                service = service,
            )

        getLocationsUseCase =
            GetLocationsUseCase(
                repository = repository,
            )
        getLocationUseCase =
            GetLocationUseCase(
                repository = repository,
            )
    }

    @Test
    fun getLocationUseCaseTest1() {
        getLocationUseCaseTest(1)
    }

    @Test
    fun getLocationUseCaseTest2() {
        getLocationUseCaseTest(2)
    }

    private fun getLocationUseCaseTest(id: Int) {
        getLocationUseCase(id).getObjectResult(
            prefix = "location",
            equalsTo = { equalsTo(it) },
        )
    }

    private fun Location.equalsTo(location: Location): Boolean =
        listOf(
            id == location.id,
            name == location.name,
            type == location.type,
            dimension == location.dimension,
            residents.getIdsList() == location.residents.getIdsList(),
        ).all { it }
}