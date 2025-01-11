package cd.zgeniuscoders.confidences.core.domain.extension

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

val Context.preferences: DataStore<Preferences> by preferencesDataStore(name = "authentication")