package com.khoalas.breadit.auth

import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.nio.charset.Charset
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.util.Locale
import java.util.UUID
import java.util.concurrent.TimeUnit
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec


const val signingKey: String = "8c7abaa5f905f70400c81bf3a1a101e75f7210104b1991f0cd5240aa80c4d99d"

class XHmac {
    companion object {
        fun createHmacHeaders(
            loginRequest: LoginRequest,
            sessionManager: SessionManager,
            baseHeaders: MutableMap<String, String>
        ): Map<String, String> {

            val seconds: Long = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())

            val body = Gson().toJson(loginRequest)
            val msg = String.format(Locale.US, "Epoch:%d|Body:%s", seconds, body)
            val hmacBody = getSignedHexString(signingKey, msg)
            baseHeaders["x-hmac-signed-body"] = formatting(hmacBody, seconds)

            val deviceId = UUID.randomUUID().toString()
            baseHeaders["client-vendor-id"] = deviceId

            CoroutineScope(Dispatchers.IO).launch {
                sessionManager.saveDeviceId(deviceId)
            }

            val result: String = String.format(
                Locale.US,
                "Epoch:%d|User-Agent:%s|Client-Vendor-ID:%s",
                seconds,
                Constants.USER_AGENT,
                deviceId
            )
            val hmacResult = getSignedHexString(signingKey, result)
            baseHeaders["x-hmac-signed-result"] = formatting(hmacResult, seconds)

            return baseHeaders
        }

        private fun getSignedHexString(key: String, msg: String): String {
            return toHexString(signMessage(key, msg))
        }

        @Throws(NoSuchAlgorithmException::class, InvalidKeyException::class)
        fun signMessage(key: String, msg: String): ByteArray {
            val charSet = Charset.forName("UTF-8")

            val alg = "HmacSHA256"
            val secretKeySpec = SecretKeySpec(key.toByteArray(charSet), alg)

            val mac = Mac.getInstance(alg)
            mac.init(secretKeySpec)

            val doFinal = mac.doFinal(msg.toByteArray(charSet))
            return doFinal
        }

        private fun toHexString(bArr: ByteArray): String {
            val stringBuilder = StringBuilder()
            for (b in bArr) {
                val hexString = Integer.toHexString(b.toInt() and 255)
                if (hexString.length == 1) {
                    stringBuilder.append('0')
                }
                stringBuilder.append(hexString)
            }
            val result = stringBuilder.toString()
            return result
        }

        private fun formatting(str: String, seconds: Long): String {
            val format = String.format(
                Locale.US, "1:android:2:%d:%s", seconds, str
            )
            return format
        }
    }

}