package com.bgargarella.ram.di

import android.content.Context
import com.bgargarella.ram.RAMApplication
import com.bgargarella.ram.data.character.repository.CharacterRepositoryImpl
import com.bgargarella.ram.data.db.RamDB
import com.bgargarella.ram.data.episode.repository.EpisodeRepositoryImpl
import com.bgargarella.ram.data.location.repository.LocationRepositoryImpl
import com.bgargarella.ram.domain.character.repository.CharacterRepository
import com.bgargarella.ram.domain.episode.repository.EpisodeRepository
import com.bgargarella.ram.domain.location.repository.LocationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule : NetworkModule() {

    @Provides
    @Singleton
    fun providesApplication(): RAMApplication = RAMApplication()

    @Provides
    @Singleton
    fun providesDatabase(
        @ApplicationContext context: Context,
    ): RamDB = RamDB.getDatabase(context)

    @Singleton
    @Provides
    fun providesCharacterRepository(db: RamDB): CharacterRepository =
        CharacterRepositoryImpl(db = db, service = service)

    @Singleton
    @Provides
    fun providesEpisodeRepository(db: RamDB): EpisodeRepository =
        EpisodeRepositoryImpl(db = db, service = service)

    @Singleton
    @Provides
    fun providesLocationRepository(db: RamDB): LocationRepository =
        LocationRepositoryImpl(db = db, service = service)
}