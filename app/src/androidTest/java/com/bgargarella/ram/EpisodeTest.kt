package com.bgargarella.ram

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bgargarella.ram.data.entity.episode.repository.EpisodeRepositoryImpl
import com.bgargarella.ram.data.util.EPISODE
import com.bgargarella.ram.domain.episode.model.Episode
import com.bgargarella.ram.domain.episode.repository.EpisodeRepository
import com.bgargarella.ram.domain.episode.usecase.GetEpisodeUseCase
import com.bgargarella.ram.domain.episode.usecase.GetEpisodesUseCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EpisodeTest : BaseTest() {

    private val repository: EpisodeRepository by lazy {
        EpisodeRepositoryImpl(
            context = context,
            db = db,
            service = service,
        )
    }

    private val getEpisodesUseCase: GetEpisodesUseCase by lazy {
        GetEpisodesUseCase(
            repository = repository,
        )
    }

    private val getEpisodeUseCase: GetEpisodeUseCase by lazy {
        GetEpisodeUseCase(
            repository = repository,
        )
    }

    @Before
    fun setup() {
        baseSetup()
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
            prefix = EPISODE,
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