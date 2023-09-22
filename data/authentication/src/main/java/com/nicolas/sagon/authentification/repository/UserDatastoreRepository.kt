package com.nicolas.sagon.authentification.repository

import android.util.Log
import androidx.datastore.core.DataStore
import com.nicolas.sagon.authentification.model.User
import com.nicolas.sagon.authentification.model.UserDatastore
import com.nicolas.sagon.authentification.model.toDomainModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class UserDatastoreRepository(
    private val datastore: DataStore<UserDatastore>,
) : UserRepository {
    override suspend fun saveUser(
        email: String,
        idToken: String,
        serverToken: String,
        accessToken: String?,
        refreshToken: String?,
    ) {
        datastore.updateData {
            UserDatastore(
                email = email,
                idToken = idToken,
                serverToken = serverToken,
                accessToken = accessToken,
                refreshToken = refreshToken
            )
        }
    }

    override suspend fun updateUserTokens(
        idToken: String,
        accessToken: String,
        refreshToken: String,
    ) {
        Log.d("test", "idToken = $idToken; accessToken= $accessToken; refreshToken = $refreshToken")
        datastore.updateData {
            it.copy(idToken = idToken, accessToken = accessToken, refreshToken = refreshToken)
        }
    }

    override suspend fun loadUser(): User? {
        return datastore.data.map {
            return@map if (!it.isFullUser()) {
                null
            } else {
                it.toDomainModel()
            }
        }.first()
    }

    override suspend fun deleteUser() {
        datastore.updateData { UserDatastore() }
    }
}