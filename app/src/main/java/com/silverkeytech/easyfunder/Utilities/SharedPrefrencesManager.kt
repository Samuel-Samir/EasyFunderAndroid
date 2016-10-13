package com.silverkeytech.easyfunder.Utilities

import android.content.Context
import android.content.SharedPreferences

private val FIRST_TIME_LAUNCH = "FirstTimeLaunch"
private val IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch"
private val PRIVATE_MODE = 0
class SharedPrefrencesManager (internal var context: Context){

    internal var pref: SharedPreferences
    internal var editor: SharedPreferences.Editor

    init {
        pref = context.getSharedPreferences(FIRST_TIME_LAUNCH, PRIVATE_MODE)
        editor = pref.edit()
    }

    var isFirstTimeLaunch: Boolean
        get() = pref.getBoolean(IS_FIRST_TIME_LAUNCH, true)
        set(isFirstTime) {
            editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime)
            editor.commit()
        }
}