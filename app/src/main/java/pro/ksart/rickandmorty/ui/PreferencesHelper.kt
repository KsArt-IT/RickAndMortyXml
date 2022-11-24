package pro.ksart.rickandmorty.ui

import android.content.Context
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PreferencesHelper @Inject constructor(
    @ApplicationContext context: Context,
) {

    private val defaultPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(context)
    }

    var isDarkTheme: Boolean
        get() = defaultPreferences.getBoolean(PREF_KEY_DARK_THEME, false)
        private set(value) = defaultPreferences.edit().putBoolean(PREF_KEY_DARK_THEME, value).apply()

    fun switchDarkTheme() {
        isDarkTheme = !isDarkTheme
        setDarkTheme()
    }

    fun setDarkTheme() {
        if (isDarkTheme) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    private companion object {
        const val PREF_KEY_DARK_THEME = "PREF_KEY_DARK_THEME"
    }
}
