package com.bgargarella.ram.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bgargarella.ram.data.entity.EpisodeEntity

@Dao
interface EpisodeDAO {

    @Query("SELECT * FROM episode")
    fun getAll(): List<EpisodeEntity>

    @Query("SELECT * FROM episode WHERE id = :id")
    suspend fun get(id: Int): EpisodeEntity

    @Query("SELECT * FROM episode WHERE id IN (:ids)")
    suspend fun get(ids: List<Int>): List<EpisodeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(episode: EpisodeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(episodes: List<EpisodeEntity>)

    @Query("DELETE FROM episode")
    fun deleteAll()
}