package com.example.guidomia.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cars_data_table")
data class Car(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "consList")
    var consList: List<String>,

    @ColumnInfo(name = "customerPrice")
    var customerPrice: Int,

    @ColumnInfo(name = "make")
    var make: String,

    @ColumnInfo(name = "marketPrice")
    var marketPrice: Int,

    @ColumnInfo(name = "model")
    var model: String,

    @ColumnInfo(name = "prosList")
    var prosList: List<String>,

    @ColumnInfo(name = "rating")
    var rating: Int

)
