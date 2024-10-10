package com.bgargarella.ram.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bgargarella.ram.data.entity.character.dao.CharacterDao
import com.bgargarella.ram.data.entity.character.model.CharacterModel
import com.bgargarella.ram.data.entity.episode.dao.EpisodeDao
import com.bgargarella.ram.data.entity.episode.model.EpisodeModel
import com.bgargarella.ram.data.entity.location.dao.LocationDao
import com.bgargarella.ram.data.entity.location.model.LocationModel

@Database(
    entities = [
        CharacterModel::class,
        EpisodeModel::class,
        LocationModel::class
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

        private fun buildDatabase(context: Context): RamDB =
            Room.databaseBuilder(
                context = context,
                klass = RamDB::class.java,
                name = "ram_db"
            )
                .fallbackToDestructiveMigration()
                .build()
    }
}