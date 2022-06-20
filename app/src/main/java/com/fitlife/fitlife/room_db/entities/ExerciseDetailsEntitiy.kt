package com.fitlife.fitlife.room_db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class ExerciseDetailsEntitiy(
    @PrimaryKey(autoGenerate = true)
    var totalExercise:Int=0,
    var useremail:String?="",
    var date:String?="",
    var username:String?="",
    var usergender:String?="",
    var exerciseImage: Int? = 0,
    var exersiseName: String? = "",
    var exerciseDescription: String? = "",
    var sets: String? = "",
    var reps: String? = "",
    var weight: String? = "",
    )
