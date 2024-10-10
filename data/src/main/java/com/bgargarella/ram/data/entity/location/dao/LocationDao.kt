package com.bgargarella.ram.data.entity.location.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bgargarella.ram.data.entity.location.model.LocationModel

@Dao
interface LocationDao {

    @Query("SELECT * FROM location")
    fun getAll(): PagingSource<Int, LocationModel>

    @Query("SELECT * FROM location WHERE id = :id")
    suspend fun get(id: Int): LocationModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAll(characters: List<LocationModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(character: LocationModel)

    @Query("DELETE FROM location")
    fun deleteAll()
}