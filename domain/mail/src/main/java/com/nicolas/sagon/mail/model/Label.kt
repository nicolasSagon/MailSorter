package com.nicolas.sagon.mail.model

interface Label {
    val id: String
    val name: String
    val messageListVisibility: String
    val labelListVisibility: String
    val type: String
}

fun Label.formattedString(): String {
    return "{\n" +
            "\t id: $id,\n" +
            "\t name: $name,\n" +
            "\t messageListVisibility: $messageListVisibility,\n" +
            "\t labelListVisibility: $labelListVisibility,\n" +
            "\t type: $type\n" +
            "}"
}