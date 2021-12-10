package com.example.sparmeplease.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "item")
data class Item
    (
        @PrimaryKey(autoGenerate = true) var itemId: Long?,
        @ColumnInfo(name = "name") var name: String,
        @ColumnInfo(name = "category") var category: Int,
        @ColumnInfo(name = "description") var description: String,
        @ColumnInfo(name = "price") var price: Int?,
        @ColumnInfo(name = "status") var status: Boolean
    ) : Serializable