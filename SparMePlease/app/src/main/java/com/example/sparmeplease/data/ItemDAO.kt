package com.example.sparmeplease.data

import androidx.room.*

@Dao
interface ItemDAO {
    @Query("SELECT * FROM item")
    fun getAllItems(): List<Item>

    @Insert
    fun insertItem(item: Item): Long

    @Delete
    fun deleteItem(item: Item)

    @Query("DELETE FROM item")
    fun deleteAllItems()

    @Update
    fun updateItem(item: Item)
}