package data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

interface ProfileRepository {
    suspend fun saveUsername(username: String)
    suspend fun getUsername(): Flow<String>
}

class ProfileRepositoryImpl(
    private val dataStorePrefs: DataStore<Preferences>,
) : ProfileRepository {

    override suspend fun saveUsername(username: String) {
        dataStorePrefs.edit {
            val usernameKey = stringPreferencesKey("username")
            it[usernameKey] = username
        }
    }

    override suspend fun getUsername(): Flow<String> {
        val usernameKey = stringPreferencesKey("username")
        return dataStorePrefs.data
            .filter { it.contains(usernameKey) }
            .map { it[usernameKey] ?: "" }
    }

}