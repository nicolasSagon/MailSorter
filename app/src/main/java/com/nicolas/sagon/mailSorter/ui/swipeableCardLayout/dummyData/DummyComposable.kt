package com.nicolas.sagon.mailSorter.ui.swipeableCardLayout.dummyData

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun DummySwipeableCard(data: CardContent) {
    Card {
        Text(text = data.text, modifier = Modifier.padding(12.dp))
    }
}

@Composable
fun DummyNoMoreDataCard() {
    Card {
        Text(text = "No more data", textAlign = TextAlign.Center, modifier = Modifier.fillMaxSize())
    }
}