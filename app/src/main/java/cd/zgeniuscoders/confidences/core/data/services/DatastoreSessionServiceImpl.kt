package cd.zgeniuscoders.confidences.core.data.services

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import cd.zgeniuscoders.confidences.core.domain.extension.preferences
import cd.zgeniuscoders.confidences.core.domain.models.Session
import cd.zgeniuscoders.confidences.core.domain.services.SessionService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DatastoreSessionServiceImpl(
    private val context: Context
) : SessionService {

    override suspend fun add(session: Session, key: String) {
        val SESSION_KEY = booleanPreferencesKey(key)
        context.preferences.edit { settings ->
            settings[SESSION_KEY] = session.isAuthenticated
        }
    }

    override suspend fun get(key: String): Flow<Boolean> {
        val SESSION_KEY = booleanPreferencesKey(key)
        val prefs: Flow<Boolean> = context.preferences.data.map { prefs ->
            prefs[SESSION_KEY] ?: false
        }

        return prefs
    }


    override suspend fun set(session: Session) {
        TODO("Not yet implemented")
    }

    override suspend fun clear(key: String) {
        val SESSION_KEY = booleanPreferencesKey(key)
        context.preferences.edit { settings ->
            settings[SESSION_KEY] = false
        }
    }

}