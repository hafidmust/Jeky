package id.aej.jeky.core.data.source.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class JekyDataStore constructor(
    private val context: Context
) {
    // At the top level of your kotlin file:
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "jeky_datastore")

    suspend fun <T> storeData(key : Preferences.Key<T>, value: T) {
        context.dataStore.edit {
            it[key] = value
        }
    }

    suspend fun clear(){
        context.dataStore.edit {
            it.clear() // clear() hapus semua data, ada juga remove nanti per key

        }
    }

    val email: Flow<String>
        get() = context.dataStore.data.map { pref ->
            pref[EMAIL] ?: ""
        }

    companion object {
        val EMAIL = stringPreferencesKey("EMAIL")
    }
}