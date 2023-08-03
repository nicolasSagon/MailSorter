package com.nicolas.sagon.mailSorter

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nicolas.sagon.mailSorter.ui.swipeableCardLayout.dummyData.CardContent
import com.nicolas.sagon.mailSorter.ui.swipeableCardLayout.dummyData.getCardTestData
import com.nicolas.sagon.mailSorter.ui.swipeableCardLayout.SwipeableCardLayout
import com.nicolas.sagon.mailSorter.ui.swipeableCardLayout.dummyData.DummyNoMoreDataCard
import com.nicolas.sagon.mailSorter.ui.swipeableCardLayout.dummyData.DummySwipeableCard
import com.nicolas.sagon.mailSorter.ui.theme.MailSorterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val cardList by remember {
                mutableStateOf(getCardTestData())
            }

            MailSorterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SwipeableCardLayout(data = cardList, content = {
                        DummySwipeableCard(it)
                    }, noMoreData = {
                        DummyNoMoreDataCard()
                    }, onSwipe = { swipeEvent, data ->
                        Log.d("SwipeableCardLayout", "Card swiped to $swipeEvent : $data")
                    })
                }
            }
        }
    }
}

