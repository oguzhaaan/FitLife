package com.fitlife.fitlife.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExerciseDetail(
    var exerciseImage:Int?=null,
    var exersiseName:String?=null,
    var exerciseDescription:String?=null,
) :Parcelable
