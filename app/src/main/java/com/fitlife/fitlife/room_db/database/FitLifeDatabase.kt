package com.fitlife.fitlife.room_db.database

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fitlife.fitlife.room_db.dao.FitLifeDao
import com.fitlife.fitlife.room_db.entities.ExerciseDetailsEntitiy
import com.fitlife.fitlife.room_db.entities.LoggedUserInformation
import com.fitlife.fitlife.room_db.entities.User

@Database(entities = [User::class,
    LoggedUserInformation::class,
    ExerciseDetailsEntitiy::class,
                     ], version = 1
)
abstract class FitLifeDatabase :RoomDatabase(){


    abstract fun schoolDao(): FitLifeDao

    companion object {
        @Volatile
        private var INSTANCE: FitLifeDatabase? = null

        fun getInstance(context: Context): FitLifeDatabase {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,

                    FitLifeDatabase::class.java,
                    "FitLife_db"
                ).allowMainThreadQueries().build().also {
                    INSTANCE = it
                }
            }
        }
    }
}