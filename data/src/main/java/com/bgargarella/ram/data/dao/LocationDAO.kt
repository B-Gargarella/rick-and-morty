package com.bgargarella.ram.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bgargarella.ram.data.entity.LocationEntity

@Dao
interface LocationDAO {

    @Query("SELECT * FROM location")
    fun getAll(): List<LocationEntity>

    @Query("SELECT * FROM location WHERE id = :id")
    suspend fun get(id: Int): LocationEntity

    @Query("SELECT * FROM location WHERE id IN (:ids)")
    suspend fun get(ids: List<Int>): List<LocationEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(location: LocationEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(locations: List<LocationEntity>)

    @Query("DELETE FROM location")
    fun deleteAll()
}