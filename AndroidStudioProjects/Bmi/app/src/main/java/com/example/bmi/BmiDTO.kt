package com.example.bmi

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data  class BmiDTO (
    var name: String,
    var age: Int,
    var height: Double,
    var weight: Double,
    var bmi: Double,
    var result: String
) : Parcelable