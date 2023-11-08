package com.bgargarella.ram.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bgargarella.ram.data.character.dao.CharacterDao
import com.bgargarella.ram.data.character.model.CharacterModel
import com.bgargarella.ram.data.episode.dao.EpisodeDao
import com.bgargarella.ram.data.episode.model.EpisodeModel
import com.bgargarella.ram.data.location.dao.LocationDao
import com.bgargarella.ram.data.location.model.LocationModel

@Database(
    entities = [
        CharacterModel::class,
        EpisodeModel::class,
        LocationModel::class,
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class RamDB : RoomDatabase() {

    abstract fun characterDao(): CharacterDao

    abstract fun episodeDao(): EpisodeDao

    abstract fun locationDao(): LocationDao

    companion object {

        @Volatile
        private var instance: RamDB? = null

        fun getDatabase(context: Context): RamDB =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }

        private fun buildDatabase(appContext: Context): RamDB =
            Room.databaseBuilder(appContext, RamDB::class.java, "ram_db")
                .fallbackToDestructiveMigration()
                .build()
    }
}