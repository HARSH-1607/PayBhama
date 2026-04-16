package com.paybhama.app.utils

import android.util.Base64
import java.security.Key
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

object EncryptionUtils {
    // In a real application, the encryption key shouldn't be hardcoded
    // You'd typically use Android Keystore system.
    private const val ALGORITHM = "AES"
    private val KEY = "PayBhamaSecret12".toByteArray() // 16 bytes for AES-128

    fun generateKey(): Key {
        return SecretKeySpec(KEY, ALGORITHM)
    }

    fun encrypt(data: String): String {
        return try {
            val key = generateKey()
            val c = Cipher.getInstance(ALGORITHM)
            c.init(Cipher.ENCRYPT_MODE, key)
            val encVal = c.doFinal(data.toByteArray())
            Base64.encodeToString(encVal, Base64.DEFAULT)
        } catch (e: Exception) {
            e.printStackTrace()
            data // fallback
        }
    }

    fun decrypt(encryptedData: String): String {
        return try {
            val key = generateKey()
            val c = Cipher.getInstance(ALGORITHM)
            c.init(Cipher.DECRYPT_MODE, key)
            val decodedValue = Base64.decode(encryptedData, Base64.DEFAULT)
            val decValue = c.doFinal(decodedValue)
            String(decValue)
        } catch (e: Exception) {
            e.printStackTrace()
            encryptedData // fallback
        }
    }
}
