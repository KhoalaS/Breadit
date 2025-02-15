package com.khoalas.breadit.auth

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.firstOrNull
import kotlin.math.exp

private val Context.dataStore by preferencesDataStore("user_prefs")

class SessionManager(private val context: Context) {
    companion object {
        private val TOKEN_KEY = stringPreferencesKey("reddit_auth_token")
        private val DEVICE_ID_KEY = stringPreferencesKey("device_id")
        private val REDDIT_SESSION_KEY = stringPreferencesKey("reddit_session")
        private val TOKEN_EXPIRY_KEY = stringPreferencesKey("token_expiry")
    }

    suspend fun saveSession(cookie: String) {
        context.dataStore.edit { prefs ->
            prefs[REDDIT_SESSION_KEY] = cookie
        }
    }

    suspend fun saveDeviceId(id: String) {
        context.dataStore.edit { prefs ->
            prefs[DEVICE_ID_KEY] = id
        }
    }

    // Save token
    suspend fun saveToken(token: String) {
        context.dataStore.edit { prefs ->
            prefs[TOKEN_KEY] = token
        }
    }

    suspend fun saveTokenExpiry(expiry: Long) {
        context.dataStore.edit { prefs ->
            prefs[TOKEN_EXPIRY_KEY] = expiry.toString()
        }
    }

    suspend fun getToken(): String? {
        return context.dataStore.data
            .map { preferences -> preferences[TOKEN_KEY] }
            .firstOrNull()
    }

    suspend fun getSession(): String? {
        return context.dataStore.data
            .map { preferences -> preferences[REDDIT_SESSION_KEY] }
            .firstOrNull()
    }

    suspend fun getDeviceId(): String? {
        return context.dataStore.data
            .map { preferences -> preferences[DEVICE_ID_KEY] }
            .firstOrNull()
    }

    suspend fun getTokenExpiry(): Long {
        val expiry = context.dataStore.data
            .map { preferences -> preferences[TOKEN_EXPIRY_KEY] }
            .firstOrNull()

        if(expiry != null) {
            return expiry.toLong()
        }

        return 0
    }

    // Retrieve token as a Flow
    val authToken: Flow<String?> = context.dataStore.data
        .map { prefs -> prefs[TOKEN_KEY] }
}