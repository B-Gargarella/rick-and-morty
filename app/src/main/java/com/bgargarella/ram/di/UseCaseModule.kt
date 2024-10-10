package com.bgargarella.ram.di

import com.bgargarella.ram.domain.character.repository.CharacterRepository
import com.bgargarella.ram.domain.character.usecase.GetCharacterUseCase
import com.bgargarella.ram.domain.character.usecase.GetCharactersByEpisodeUseCase
import com.bgargarella.ram.domain.character.usecase.GetCharactersByLocationUseCase
import com.bgargarella.ram.domain.character.usecase.GetCharactersUseCase
import com.bgargarella.ram.domain.episode.repository.EpisodeRepository
import com.bgargarella.ram.domain.episode.usecase.GetEpisodeUseCase
import com.bgargarella.ram.domain.episode.usecase.GetEpisodesByCharacterUseCase
import com.bgargarella.ram.domain.episode.usecase.GetEpisodesUseCase
import com.bgargarella.ram.domain.location.repository.LocationRepository
import com.bgargarella.ram.domain.location.usecase.GetLocationUseCase
import com.bgargarella.ram.domain.location.usecase.GetLocationsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Singleton
    @Provides
    fun providesGetCharactersUseCase(
        repository: CharacterRepository
    ): GetCharactersUseCase =
        GetCharactersUseCase(repository = repository)

    @Singleton
    @Provides
    fun providesGetCharactersByEpisodeUseCase(
        characterRepository: CharacterRepository,
        episodeRepository: EpisodeRepository
    ): GetCharactersByEpisodeUseCase =
        GetCharactersByEpisodeUseCase(
            characterRepository = characterRepository,
            episodeRepository = episodeRepository
        )

    @Singleton
    @Provides
    fun providesGetCharactersByLocationUseCase(
        characterRepository: CharacterRepository,
        locationRepository: LocationRepository
    ): GetCharactersByLocationUseCase =
        GetCharactersByLocationUseCase(
            characterRepository = characterRepository,
            locationRepository = locationRepository
        )

    @Singleton
    @Provides
    fun providesGetCharacterUseCase(
        repository: CharacterRepository
    ): GetCharacterUseCase =
        GetCharacterUseCase(repository = repository)

    @Singleton
    @Provides
    fun providesGetEpisodesUseCase(
        repository: EpisodeRepository
    ): GetEpisodesUseCase =
        GetEpisodesUseCase(repository = repository)

    @Singleton
    @Provides
    fun providesGetEpisodesByCharacterUseCase(
        characterRepository: CharacterRepository,
        episodeRepository: EpisodeRepository
    ): GetEpisodesByCharacterUseCase =
        GetEpisodesByCharacterUseCase(
            characterRepository = characterRepository,
            episodeRepository = episodeRepository
        )

    @Singleton
    @Provides
    fun providesGetEpisodeUseCase(
        repository: EpisodeRepository
    ): GetEpisodeUseCase = GetEpisodeUseCase(repository = repository)

    @Singleton
    @Provides
    fun providesGetLocationsUseCase(
        repository: LocationRepository
    ): GetLocationsUseCase = GetLocationsUseCase(repository = repository)

    @Singleton
    @Provides
    fun providesGetLocationUseCase(
        repository: LocationRepository
    ): GetLocationUseCase = GetLocationUseCase(repository = repository)
}