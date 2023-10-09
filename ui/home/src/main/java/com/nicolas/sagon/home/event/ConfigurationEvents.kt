package com.nicolas.sagon.home.event

sealed interface ConfigurationEvents {
    class OnLabelChanged(val text: String) : ConfigurationEvents
}