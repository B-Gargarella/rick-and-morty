package com.bgargarella.ram

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bgargarella.ram.data.character.repository.CharacterRepositoryImpl
import com.bgargarella.ram.domain.character.model.Character
import com.bgargarella.ram.domain.character.repository.CharacterRepository
import com.bgargarella.ram.domain.character.usecase.GetCharacterUseCase
import com.bgargarella.ram.domain.character.usecase.GetCharactersUseCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CharacterTest : BaseTest() {

    private lateinit var repository: CharacterRepository

    private lateinit var getCharactersUseCase: GetCharactersUseCase
    private lateinit var getCharacterUseCase: GetCharacterUseCase

    @Before
    fun setup() {
        baseSetup()

        repository =
            CharacterRepositoryImpl(
                context = context,
                db = db,
                service = service,
            )

        getCharactersUseCase =
            GetCharactersUseCase(
                repository = repository
            )
        getCharacterUseCase =
            GetCharacterUseCase(
                repository = repository,
            )
    }

    @Test
    fun getCharacterUseCaseTest1() {
        getCharacterUseCaseTest(1)
    }

    @Test
    fun getCharacterUseCaseTest2() {
        getCharacterUseCaseTest(2)
    }

    private fun getCharacterUseCaseTest(id: Int) {
        getCharacterUseCase(id).getObjectResult(
            prefix = "character",
            equalsTo = { equalsTo(it) },
        )
    }

    private fun Character.equalsTo(character: Character): Boolean =
        listOf(
            id == character.id,
            name == character.name,
            avatar == character.avatar,
            status == character.status,
            species == character.species,
            type == character.type,
            gender == character.gender,
            origin == character.origin,
            location == character.location,
            episodes.getIdsList() == character.episodes.getIdsList(),
        ).all { it }
}