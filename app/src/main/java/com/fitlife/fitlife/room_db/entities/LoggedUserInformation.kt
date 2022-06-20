package com.fitlife.fitlife.room_db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LoggedUserInformation(
    var name: String="",
    @PrimaryKey(autoGenerate = false)
    var email:String="",
    var gender:String="",
    var password:String="",
)
