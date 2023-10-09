package com.nicolas.sagon.mail.model

interface UserThreads {
    val threads: List<UserThread>
    val nextPageToken: String
}

interface UserThread {
    val id: String
    val snippet: String
    val historyId: String
}

fun UserThreads.formattedString(): String {
    return "{\n" +
            "\tthreads: [\n" +
            "\t\t${threads.map { it.formattedString() }}\n" +
            "\t],\n" +
            "nextPageToken: $nextPageToken\n" +
            "}"
}

fun UserThread.formattedString(): String {
    return "{\n" +
            "\t id: $id,\n" +
            "\t snippet: $snippet,\n" +
            "\t historyId: $historyId\n" +
            "}"
}