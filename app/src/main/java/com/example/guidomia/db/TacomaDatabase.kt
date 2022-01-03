package com.example.guidomia.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Car::class], version = 1)
@TypeConverters(Converters::class)
abstract class TacomaDatabase : RoomDatabase() {
    abstract val carDao: CarDao

    companion object {
        @Volatile
        private var INSTANCE: TacomaDatabase? = null
        fun getInstance(context: Context): TacomaDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TacomaDatabase::class.java,
                        "car_data_database"
                    ).build()
                }
                return instance
            }
        }
    }
}