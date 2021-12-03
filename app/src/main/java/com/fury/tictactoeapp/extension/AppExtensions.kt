package com.fury.tictactoeapp.extension

import android.content.Context
import android.widget.TextView


/***
 * Created By Amir Fury on December 3 2021
 *
 * Email: Fury.amir93@gmail.com
 * */

fun TextView.setTextOnView(value: String?) {
    text = value.toString()
}

fun Context.string(strResId: Int): String = resources.getString(strResId)