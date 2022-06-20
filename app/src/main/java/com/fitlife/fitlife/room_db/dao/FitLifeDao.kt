package com.fitlife.fitlife.room_db.dao

import androidx.room.*
import com.fitlife.fitlife.room_db.entities.ExerciseDetailsEntitiy
import com.fitlife.fitlife.room_db.entities.LoggedUserInformation
import com.fitlife.fitlife.room_db.entities.User


@Dao
interface FitLifeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User): Long


    @Query("SELECT COUNT(email) FROM user")
    fun getRowCount(): Long

    @Query("SELECT * FROM User")
    fun getUser(): List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveLoggedUser(loggedUserInformation: LoggedUserInformation)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveTheExerciseOfTheUser(exercises: ExerciseDetailsEntitiy):Long
    @Query("SELECT COUNT(totalExercise) FROM ExerciseDetailsEntitiy")
    fun saveExerciseRowCount(): Long

    @Query("SELECT * FROM ExerciseDetailsEntitiy")
    fun getSaveExerciseOfUser(): List<ExerciseDetailsEntitiy>

    @Query("SELECT * FROM User WHERE email LIKE:email AND password LIKE:password")
    fun getUserData(email: String, password: String): User


    @Query("SELECT * FROM LoggedUserInformation")
    fun getLoginUserInformation(): LoggedUserInformation


    @Delete
    fun clearLoggedUserData(loggedUserInformation: LoggedUserInformation)



    @Delete
    fun deleteExercise(exercise: ExerciseDetailsEntitiy) : Int
}