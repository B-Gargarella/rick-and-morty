package com.bgargarella.ram.domain.usecase

import com.bgargarella.ram.domain.entities.Episode
import com.bgargarella.ram.domain.model.Result
import com.bgargarella.ram.domain.repository.InfoRepository

class GetEpisodesByCharacterUseCase(
    private val repository: InfoRepository
) {

    suspend operator fun invoke(id: Int): Result<List<Episode>> =
        repository.getCharacterEpisodes(id = id)
}