package com.bgargarella.ram.data.entity.character.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bgargarella.ram.data.entity.character.model.CharacterModel

@Dao
interface CharacterDao {

    @Query("SELECT * FROM character")
    fun getAll(): PagingSource<Int, CharacterModel>

    @Query("SELECT * FROM character WHERE id = :id")
    suspend fun get(id: Int): CharacterModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAll(characters: List<CharacterModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(character: CharacterModel)

    @Query("DELETE FROM character")
    fun deleteAll()
}