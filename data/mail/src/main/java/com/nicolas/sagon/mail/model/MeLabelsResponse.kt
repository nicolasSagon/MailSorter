package com.nicolas.sagon.mail.model

import kotlinx.serialization.Serializable

@Serializable
data class MeLabelsResponse(
    val labels: List<LabelApiResponse>,
)

@Serializable
data class LabelApiResponse(
    val id: String,
    val name: String,
    val messageListVisibility: String,
    val labelListVisibility: String,
    val type: String
)
