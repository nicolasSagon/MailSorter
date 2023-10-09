package com.nicolas.sagon.home.state

sealed class ConfigurationState {
    data object Loading : ConfigurationState()

    data class Data(
        val labelValue: String = ""
    ): ConfigurationState()

    data class Error(
        val errorMessage: String,
    ) : ConfigurationState()
}