package com.example.exerciseapp.utils

import android.content.Context
import android.net.Uri
import android.text.Editable
import com.example.exerciseapp.R

fun Float.checkBMI(context: Context): Pair<String, String> {
    val bmiLabel: String
    val bmiDescription: String

    when {
        this <= 15f -> {
            bmiLabel = context.getString(R.string.very_severely_underweight)
            bmiDescription = context.getString(R.string.underweight_description)
        }

        this > 15f && this <= 16f -> {
            bmiLabel = context.getString(R.string.severely_underweight)
            bmiDescription = context.getString(R.string.underweight_description)
        }

        this > 16f && this <= 18.5f -> {
            bmiLabel = context.getString(R.string.underweight)
            bmiDescription = context.getString(R.string.underweight_description)
        }

        this > 18.5f && this <= 25f -> {
            bmiLabel = context.getString(R.string.normal)
            bmiDescription = context.getString(R.string.normal_description)
        }

        this > 25f && this <= 30f -> {
            bmiLabel = context.getString(R.string.overweight)
            bmiDescription = context.getString(R.string.overweight_description)
        }

        this > 30f && this <= 35f -> {
            bmiLabel = context.getString(R.string.obese_moderately)
            bmiDescription = context.getString(R.string.overweight_description)
        }

        this > 35f && this <= 40f -> {
            bmiLabel = context.getString(R.string.obese_severely)
            bmiDescription = context.getString(R.string.obese_severely_description)
        }

        else -> {
            bmiLabel = context.getString(R.string.obese_very_severely)
            bmiDescription = context.getString(R.string.obese_severely_description)
        }
    }

    return Pair(bmiLabel, bmiDescription)
}

fun Editable?.editableToFloat(): Float = toString().toFloat()

fun Int.toSoundUri(): Uri = Uri.parse("android.resource://com.example.exerciseapp/$this")