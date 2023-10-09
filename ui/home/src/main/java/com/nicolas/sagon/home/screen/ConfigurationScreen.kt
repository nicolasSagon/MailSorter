package com.nicolas.sagon.home.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.nicolas.sagon.core.theme.MailSorterTheme
import com.nicolas.sagon.home.composable.MainView

@Composable
fun ConfigurationScreen(modifier: Modifier = Modifier) {
    MainView(modifier)
}

@Preview
@Composable
fun ConfigurationScreenPreview() {
    MailSorterTheme {
        HomeScreen()
    }
}