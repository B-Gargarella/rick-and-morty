package com.bgargarella.ram.data.entity.episode.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bgargarella.ram.data.entity.episode.model.EpisodeModel

@Dao
interface EpisodeDao {

    @Query("SELECT * FROM episode")
    fun getAll(): PagingSource<Int, EpisodeModel>

    @Query("SELECT * FROM episode WHERE id = :id")
    suspend fun get(id: Int): EpisodeModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAll(characters: List<EpisodeModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(character: EpisodeModel)

    @Query("DELETE FROM episode")
    fun deleteAll()
}