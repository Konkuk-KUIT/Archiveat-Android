package com.kuit.archiveatproject.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.tokenDataStore by preferencesDataStore(name = "token_datastore")

@Singleton
class TokenLocalDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : TokenLocalDataSource {

    private val ds = context.tokenDataStore
    private val KEY_ACCESS_TOKEN = stringPreferencesKey("access_token")
    private val KEY_REFRESH_TOKEN = stringPreferencesKey("refresh_token")

    override val accessTokenFlow: Flow<String?> =
        ds.data.map { prefs -> prefs[KEY_ACCESS_TOKEN] }
    override val refreshTokenFlow: Flow<String?> =
        ds.data.map { prefs -> prefs[KEY_REFRESH_TOKEN] }

    override suspend fun saveAccessToken(token: String) {
        ds.edit { prefs -> prefs[KEY_ACCESS_TOKEN] = token }
    }

    override suspend fun saveRefreshToken(token: String) {
        ds.edit { prefs -> prefs[KEY_REFRESH_TOKEN] = token }
    }

    override suspend fun clearAccessToken() {
        ds.edit { prefs -> prefs.remove(KEY_ACCESS_TOKEN) }
    }

    override suspend fun clearRefreshToken() {
        ds.edit { prefs -> prefs.remove(KEY_REFRESH_TOKEN) }
    }
}
