package com.nicolas.sagon.mailSorter.ui.swipeableCardLayout

data class SwipeableCardLayoutState<T>(
    val animationState: AnimationState = AnimationState.NONE,
    val touchableCardOffset: Float = 0f,
    val initDragPosition: Float = 0f,
    val dataList: List<T>,
    val currentCardIndex: Int = 0,
    val isLoadingMoreData: Boolean = false,
)

enum class AnimationState {
    NONE,
    RETURN_RIGHT,
    RETURN_LEFT,
    PASS_RIGHT,
    PASS_LEFT
}