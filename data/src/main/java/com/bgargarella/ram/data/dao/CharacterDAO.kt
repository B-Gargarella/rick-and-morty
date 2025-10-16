package com.bgargarella.ram.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bgargarella.ram.data.entity.CharacterEntity

@Dao
interface CharacterDAO {

    @Query("SELECT * FROM character")
    fun getAll(): List<CharacterEntity>

    @Query("SELECT * FROM character WHERE id = :id")
    suspend fun get(id: Int): CharacterEntity

    @Query("SELECT * FROM character WHERE id IN (:ids)")
    suspend fun get(ids: List<Int>): List<CharacterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(character: CharacterEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(characters: List<CharacterEntity>)

    @Query("DELETE FROM character")
    fun deleteAll()
}