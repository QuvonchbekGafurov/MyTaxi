package com.example.mytaxi.data.room


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locations")
data class LocationEntity(
    @PrimaryKey val id: Int,
    val longitude: Double,
    val latitude: Double
)
