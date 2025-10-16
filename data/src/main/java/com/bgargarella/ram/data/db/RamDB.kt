package com.bgargarella.ram.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bgargarella.ram.data.dao.CharacterDAO
import com.bgargarella.ram.data.dao.EpisodeDAO
import com.bgargarella.ram.data.dao.LocationDAO
import com.bgargarella.ram.data.entity.CharacterEntity
import com.bgargarella.ram.data.entity.EpisodeEntity
import com.bgargarella.ram.data.entity.LocationEntity

@Database(
    entities = [
        CharacterEntity::class,
        EpisodeEntity::class,
        LocationEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class RamDB : RoomDatabase() {

    abstract fun characterDao(): CharacterDAO

    abstract fun episodeDao(): EpisodeDAO

    abstract fun locationDao(): LocationDAO

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
                .fallbackToDestructiveMigration(dropAllTables = false)
                .build()
    }
}