package com.myungwoo.gettiming_app.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.myungwoo.gettiming_app.App
import java.util.prefs.PreferenceChangeListener

class MyDataStore {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("user_pref")
    }

    private val context = App.context()
    private val mDataStore: DataStore<Preferences> = context.dataStore
    private val FRIST_FLAG = booleanPreferencesKey("FRIST_FLAG")

    suspend fun setupFirstData() {
        mDataStore.edit { preferences ->
            preferences[FRIST_FLAG] = true
        }
    }

    suspend fun getFirstData(): Boolean {
        var currentValue = false
        mDataStore.edit { preferences ->
            currentValue = preferences[FRIST_FLAG] ?: false
        }
        return currentValue
    }
}