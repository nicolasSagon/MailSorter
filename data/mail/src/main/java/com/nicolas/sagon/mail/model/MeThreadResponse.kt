package com.nicolas.sagon.mail.model

import kotlinx.serialization.Serializable

@Serializable
data class MeThreadResponse(
    val threads: List<ThreadApiResponse>,
    val nextPageToken: String,
    val resultSizeEstimate: Int,
)

@Serializable
data class ThreadApiResponse(
    val id: String,
    val snippet: String,
    val historyId: String,
)

fun MeThreadResponse.toDomainModel(): UserThreads {
    return object : UserThreads {
        override val threads: List<UserThread>
            get() = this@toDomainModel.threads.map {
                it.toDomainModel()
            }
        override val nextPageToken: String
            get() = this@toDomainModel.nextPageToken

    }
}

fun ThreadApiResponse.toDomainModel(): UserThread {
    return object : UserThread {
        override val id: String
            get() = this@toDomainModel.id
        override val snippet: String
            get() = this@toDomainModel.snippet
        override val historyId: String
            get() = this@toDomainModel.historyId

    }
}
