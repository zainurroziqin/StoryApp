package com.rozi.storyapp.data.lokal

import android.content.Context

internal class TokenPreferences(context: Context) {

    companion object {
        private const val TOKEN = "token"
        private const val USER = ""
    }

    private val preferences = context.getSharedPreferences(USER, Context.MODE_PRIVATE)

    fun setToken(token: String) {
        val editor = preferences.edit()
        editor.putString(TOKEN, token)
        editor.apply()
    }

    fun getToken(): String {
        return preferences.getString(TOKEN, "") ?: ""
    }
}