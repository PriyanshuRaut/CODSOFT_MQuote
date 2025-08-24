package com.masters.mquote.objects

import android.content.Context

object PrefsHelper {
    private const val PREFS_NAME = "quotes_prefs"
    private const val KEY_FAV_QUOTES = "fav_quotes"

    fun saveFavQuote(context: Context, quote: String) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val favSet = prefs.getStringSet(KEY_FAV_QUOTES, mutableSetOf())?.toMutableSet() ?: mutableSetOf()
        favSet.add(quote)
        prefs.edit().putStringSet(KEY_FAV_QUOTES, favSet).apply()
    }

    fun getFavQuotes(context: Context): List<String> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getStringSet(KEY_FAV_QUOTES, emptySet())?.toList() ?: emptyList()
    }

    fun removeFavQuote(context: Context, quote: String) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val favSet = prefs.getStringSet(KEY_FAV_QUOTES, mutableSetOf())?.toMutableSet() ?: return
        if (favSet.remove(quote)) {
            prefs.edit().putStringSet(KEY_FAV_QUOTES, favSet).apply()
        }
    }


}
