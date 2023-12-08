package com.bgargarella.ram

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bgargarella.ram.data.episode.repository.EpisodeRepositoryImpl
import com.bgargarella.ram.domain.episode.model.Episode
import com.bgargarella.ram.domain.episode.repository.EpisodeRepository
import com.bgargarella.ram.domain.episode.usecase.GetEpisodeUseCase
import com.bgargarella.ram.domain.episode.usecase.GetEpisodesUseCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EpisodeTest : BaseTest() {

    private lateinit var repository: EpisodeRepository

    private lateinit var getEpisodesUseCase: GetEpisodesUseCase
    private lateinit var getEpisodeUseCase: GetEpisodeUseCase

    @Before
    fun setup() {
        baseSetup()

        repository =
            EpisodeRepositoryImpl(
                context = context,
                db = db,
                service = service,
            )

        getEpisodesUseCase =
            GetEpisodesUseCase(
                repository = repository,
            )
        getEpisodeUseCase =
            GetEpisodeUseCase(
                repository = repository,
            )
    }

    @Test
    fun getEpisodeUseCaseTest1() {
        getEpisodeUseCaseTest(1)
    }

    @Test
    fun getEpisodeUseCaseTest2() {
        getEpisodeUseCaseTest(2)
    }

    private fun getEpisodeUseCaseTest(id: Int) {
        getEpisodeUseCase(id).getObjectResult(
            prefix = "episode",
            equalsTo = { equalsTo(it) },
        )
    }

    private fun Episode.equalsTo(episode: Episode): Boolean =
        listOf(
            id == episode.id,
            name == episode.name,
            releaseDate == episode.releaseDate,
            code == episode.code,
            characters.getIdsList() == episode.characters.getIdsList(),
        ).all { it }
}