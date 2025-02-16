package com.khoalas.breadit.auth

import com.apollographql.apollo.mpp.currentTimeMillis
import com.khoalas.breadit.data.model.Scope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthRepository(
    private val sessionManager: SessionManager,
    private val auth: RedditAuth
) {
    private val _authToken = MutableStateFlow<String?>("") // Holds latest token
    val authToken: StateFlow<String?> = _authToken.asStateFlow()

    private val scope = Scope(scopes = listOf("*", "email", "pii"))

    private val _expiry = MutableStateFlow<Long?>(null)

    private val scope = Scope(scopes = listOf("*", "email", "pii"))

    init {
        CoroutineScope(Dispatchers.IO).launch {
            delay(500)
            _authToken.value = sessionManager.getToken()
            val expiry = sessionManager.getTokenExpiry()
            _expiry.value = expiry
            val now = currentTimeMillis() / 1000
            if ((expiry - now) < 600) {
                refreshToken()
            }
        }
    }

    suspend fun login(username: String, password: String): Boolean {

        val loginRequest = LoginRequest(username, password)

        val res =
            auth.api.login(
                loginRequest,
                XHmac.createHmacHeaders(loginRequest, sessionManager, auth.getBasicAuthHeaders())
            )

        if (res.isSuccessful) {
            val resData = res.body() ?: return false
            if (!resData.success) return false

            val sessionCookie = res.headers()["set-cookie"]
            sessionCookie ?: return false

            CoroutineScope(Dispatchers.IO).launch {
                sessionManager.saveSession(sessionCookie)
            }

            // val cookies = parseCookies(sessionCookie)

            val basicHeaders = auth.getBasicAuthHeaders()
            basicHeaders["cookie"] = sessionCookie

            val accessRes = auth.api.getAccessToken(basicHeaders, scope)
            if (!accessRes.isSuccessful) {
                return false
            }

            val tokenResponse = accessRes.body()
            tokenResponse ?: return false

            _authToken.value = tokenResponse.access_token
            _expiry.value = tokenResponse.expiry_ts

            sessionManager.saveToken(tokenResponse.access_token)
            sessionManager.saveTokenExpiry(tokenResponse.expiry_ts)


            return true
        } else {
            return false
        }
    }

    suspend fun refreshToken() {
        val basicHeaders = auth.getBasicAuthHeaders()
        val cookie = sessionManager.getSession() ?: return

        basicHeaders["cookie"] = cookie

        val accessRes = auth.api.getAccessToken(basicHeaders, scope)
        if (!accessRes.isSuccessful) {
            return
        }

        val tokenResponse = accessRes.body() ?: return

        CoroutineScope(Dispatchers.IO).launch {
            sessionManager.saveToken(tokenResponse.access_token)
            sessionManager.saveTokenExpiry(tokenResponse.expiry_ts)
        }

    }

    fun parseCookies(cookieString: String): Map<String, String> {
        return cookieString.split(";")
            .map { it.trim() }
            .mapNotNull {
                val (key, value) = it.split("=", limit = 2)
                if (key.isNotEmpty() && value.isNotEmpty()) {
                    key to value
                } else null
            }
            .toMap()
    }
}