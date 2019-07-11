package com.kovrizhkin.moviesapp.model

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object SharedPreferenceManager {

    private const val MOVIES_PREF = "MOVIES_PREF"

    private const val FAVORITES = "FAVORITES"

    fun saveFavorites(context: Context, favorites: List<Int>) {
        val mPrefs = context.getSharedPreferences(MOVIES_PREF, 0)
        val editor = mPrefs.edit()
        val gson = Gson()
        val json = gson.toJson(favorites)
        editor.putString(FAVORITES, json)
        editor.apply()

    }

    fun getFavorites(context: Context): List<Int> {
        val mPrefs = context.getSharedPreferences(MOVIES_PREF, 0)
        var result: List<Int> = emptyList()
        val gson = Gson()
        val json: String? = mPrefs.getString(FAVORITES, "")
        val type = object : TypeToken<List<Int>>() {}.type

        if (!json.isNullOrBlank()) {
            result = gson.fromJson(json, type)
        }

        return result
    }
}