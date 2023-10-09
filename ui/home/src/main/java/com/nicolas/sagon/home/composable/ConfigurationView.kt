package com.nicolas.sagon.home.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nicolas.sagon.core.theme.MailSorterTheme

@Composable
fun ConfigurationView(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "In this screen we gonna configure the specific label used by this app",
            textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(34.dp))
        TextField(
            value = "",
            onValueChange = {},
            placeholder = {
                Text(text = "Label used by the app")
            }
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = {}) {
            Text(text = "Continue")
        }
    }

}

@Preview
@Composable
fun ConfigurationViewPreview() {
    MailSorterTheme {
        ConfigurationView()
    }
}

