package com.nicolas.sagon.authentification.serializer

import androidx.datastore.core.Serializer
import com.nicolas.sagon.authentification.crypto.CryptoManager
import com.nicolas.sagon.authentification.model.UserDatastore
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

class UserDatastoreSerializer(
    private val cryptoManager: CryptoManager,
) : Serializer<UserDatastore> {

    override val defaultValue: UserDatastore
        get() = UserDatastore()

    override suspend fun readFrom(input: InputStream): UserDatastore {
        val decryptedBytes = cryptoManager.decrypt(input)
        return try {
            Json.decodeFromString(
                deserializer = UserDatastore.serializer(),
                string = decryptedBytes.decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: UserDatastore, output: OutputStream) {
        cryptoManager.encrypt(
            bytes = Json.encodeToString(
                serializer = UserDatastore.serializer(),
                value = t
            ).encodeToByteArray(),
            outputStream = output
        )
    }
}