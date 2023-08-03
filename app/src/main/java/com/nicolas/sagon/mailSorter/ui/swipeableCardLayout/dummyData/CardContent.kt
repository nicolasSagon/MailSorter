package com.nicolas.sagon.mailSorter.ui.swipeableCardLayout.dummyData

data class CardContent(
    val text: String
)

fun getCardTestData(): List<CardContent> {
    return listOf(
        CardContent("Card 1"),
        CardContent("Card 2"),
        CardContent("Card 3"),
        CardContent("Card 4"),
        CardContent("Card 5"),
        CardContent("Card 6"),
        CardContent("Card 7"),
        CardContent("Card 8"),
        CardContent("Card 9"),
        CardContent("Card 10"),
        CardContent("Card 11"),
    )
}
