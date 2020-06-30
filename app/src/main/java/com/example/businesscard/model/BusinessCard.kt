package com.example.businesscard.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "businessCards")
data class BusinessCard(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "content")
    var content:String?,
    @ColumnInfo(name = "name")
    var name:String?,
    @ColumnInfo(name = "image_uri")
   var image:String
)
