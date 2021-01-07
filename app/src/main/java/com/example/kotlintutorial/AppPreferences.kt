package com.example.kotlintutorial

import android.content.Context
import android.content.SharedPreferences

object AppPreferences {

    private const val NAME = "QARMATEK"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    //SharedPreferences variables
    private val IS_LOGIN = Pair("is_login", false)
    private val USERID = Pair("USERID", "")
    private val KEY_USERNAME = Pair("KEY_USERNAME", "")
    private val KEY_EMPID = Pair("KEY_EMPID","")
    private val KEY_EMPLOYEENAME = Pair("KEY_EMPLOYEENAME","")
    private val KEY_PLANTCODE = Pair("KEY_PLANTCODE","")

    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    //an inline function to put variable and save it
    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    //SharedPreferences variables getters/setters
    var isLogin: Boolean
        get() = preferences.getBoolean(IS_LOGIN.first, IS_LOGIN.second)
        set(value) = preferences.edit {
            it.putBoolean(IS_LOGIN.first, value)
        }

    var userid: String
        get() = preferences.getString(USERID.first, USERID.second) ?: ""
        set(value) = preferences.edit {
            it.putString(USERID.first, value)
        }

    var username: String
        get() = preferences.getString(KEY_USERNAME.first, KEY_USERNAME.second) ?: ""
        set(value) = preferences.edit {
            it.putString(KEY_USERNAME.first, value)
        }

    var empid: String
        get() = preferences.getString(KEY_EMPID.first, KEY_EMPID.second) ?: ""
        set(value) = preferences.edit {
            it.putString(KEY_EMPID.first, value)
        }

    var empname: String
        get() = preferences.getString(KEY_EMPLOYEENAME.first, KEY_EMPLOYEENAME.second) ?: ""
        set(value) = preferences.edit {
            it.putString(KEY_EMPLOYEENAME.first, value)
        }

    var plantcode: String
        get() = preferences.getString(KEY_PLANTCODE.first, KEY_PLANTCODE.second) ?: ""
        set(value) = preferences.edit {
            it.putString(KEY_PLANTCODE.first, value)
        }
}