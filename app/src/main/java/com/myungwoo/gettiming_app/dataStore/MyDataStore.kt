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

    private val context = App.context()

    companion object{
        private val Context.dataStore : DataStore<Preferences> by preferencesDataStore("user_pref")
    }

    private val mDataStore : DataStore<Preferences> = context.dataStore

    private val FRIST_FLAG = booleanPreferencesKey("FRIST_FLAG")

    //선택하기 버튼을 누른 후 MainActivity로 갈 때 TRUE로 변경
    suspend fun setupFristData(){
        mDataStore.edit { preferences ->
            preferences[FRIST_FLAG] = true
        }
    }

    suspend fun getFristData() : Boolean {
        var currentValue = false
        mDataStore.edit { preferences ->
            currentValue =  preferences[FRIST_FLAG] ?: false
        }
        return currentValue
    }
}