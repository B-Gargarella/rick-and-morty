package com.bgargarella.ram.di

import com.bgargarella.ram.domain.character.usecase.GetCharacterUseCase
import com.bgargarella.ram.domain.character.usecase.GetCharactersByEpisodeUseCase
import com.bgargarella.ram.domain.character.usecase.GetCharactersByLocationUseCase
import com.bgargarella.ram.domain.character.usecase.GetCharactersUseCase
import com.bgargarella.ram.domain.episode.usecase.GetEpisodeUseCase
import com.bgargarella.ram.domain.episode.usecase.GetEpisodesByCharacterUseCase
import com.bgargarella.ram.domain.episode.usecase.GetEpisodesUseCase
import com.bgargarella.ram.domain.location.usecase.GetLocationUseCase
import com.bgargarella.ram.domain.location.usecase.GetLocationsUseCase
import com.bgargarella.ram.presentation.character.viewmodel.CharacterViewModel
import com.bgargarella.ram.presentation.character.viewmodel.CharactersViewModel
import com.bgargarella.ram.presentation.episode.viewmodel.EpisodeViewModel
import com.bgargarella.ram.presentation.episode.viewmodel.EpisodesViewModel
import com.bgargarella.ram.presentation.location.viewmodel.LocationViewModel
import com.bgargarella.ram.presentation.location.viewmodel.LocationsViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ViewModelModule {

    @Singleton
    @Provides
    fun providesCharactersViewModel(
        getCharactersUseCase: GetCharactersUseCase,
        getCharactersByEpisodeUseCase: GetCharactersByEpisodeUseCase,
        getCharactersByLocationUseCase: GetCharactersByLocationUseCase
    ): CharactersViewModel =
        CharactersViewModel(
            getCharactersUseCase = getCharactersUseCase,
            getCharactersByEpisodeUseCase = getCharactersByEpisodeUseCase,
            getCharactersByLocationUseCase = getCharactersByLocationUseCase
        )

    @Singleton
    @Provides
    fun providesCharacterViewModel(
        getCharacterUseCase: GetCharacterUseCase
    ): CharacterViewModel =
        CharacterViewModel(
            getCharacterUseCase = getCharacterUseCase
        )

    @Singleton
    @Provides
    fun providesEpisodesViewModel(
        getEpisodesUseCase: GetEpisodesUseCase,
        getEpisodesByCharacterUseCase: GetEpisodesByCharacterUseCase
    ): EpisodesViewModel =
        EpisodesViewModel(
            getEpisodesUseCase = getEpisodesUseCase,
            getEpisodesByCharacterUseCase = getEpisodesByCharacterUseCase
        )

    @Singleton
    @Provides
    fun providesEpisodeViewModel(
        getEpisodeUseCase: GetEpisodeUseCase
    ): EpisodeViewModel =
        EpisodeViewModel(
            getEpisodeUseCase = getEpisodeUseCase
        )

    @Singleton
    @Provides
    fun providesLocationsViewModel(
        getLocationsUseCase: GetLocationsUseCase
    ): LocationsViewModel =
        LocationsViewModel(
            getLocationsUseCase = getLocationsUseCase
        )

    @Singleton
    @Provides
    fun providesLocationViewModel(
        getLocationUseCase: GetLocationUseCase
    ): LocationViewModel =
        LocationViewModel(
            getLocationUseCase = getLocationUseCase
        )
}