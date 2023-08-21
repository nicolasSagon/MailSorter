package com.nicolas.sagon.mailSorter.ui.swipeableCardLayout

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.abs

class SwipeableCardLayoutViewModel<T>(
    data: List<T>,
    val dataLoadThreshold: Int,
    val swipeOffset: Float = 200f,
) {

    private val _uiState = MutableStateFlow(SwipeableCardLayoutState(dataList = data))
    val uiState: StateFlow<SwipeableCardLayoutState<T>> = _uiState.asStateFlow()

    private val draggableRatio = 2

    fun startDrag(dragPosition: Float) {
        _uiState.update { currentState ->
            currentState.copy(initDragPosition = dragPosition)
        }
    }

    fun updateDragOffset(dragOffset: Float) {
        _uiState.update { currentState ->
            currentState.copy(touchableCardOffset = (dragOffset - currentState.initDragPosition) / draggableRatio)
        }
    }

    fun pressEnd() {
        val currentStateValue = _uiState.value
        val newAnimationValue = if (currentStateValue.touchableCardOffset < 0) {
            // Not enough return to start position
            if (abs(currentStateValue.touchableCardOffset) < swipeOffset) {
                AnimationState.RETURN_LEFT
            } else {
                AnimationState.PASS_LEFT
            }
        } else {
            if (currentStateValue.touchableCardOffset < swipeOffset) {
                AnimationState.RETURN_RIGHT
            } else {
                AnimationState.PASS_RIGHT
            }
        }
        _uiState.update { currentState ->
            currentState.copy(animationState = newAnimationValue)
        }
    }

    fun getPassRightAnimationTarget(): Float {
        return if (_uiState.value.touchableCardOffset > swipeOffset) _uiState.value.touchableCardOffset else swipeOffset
    }

    fun getPassLeftAnimationTarget(): Float {
        return if (_uiState.value.touchableCardOffset < -swipeOffset) _uiState.value.touchableCardOffset else -swipeOffset
    }

    fun updateOffsetForAnimation(offset: Float) {
        _uiState.update { currentState ->
            currentState.copy(touchableCardOffset = offset)
        }
    }

    fun finishAnimation() {
        _uiState.update { currentState ->
            currentState.copy(
                touchableCardOffset = 0f,
                animationState = AnimationState.NONE,
                initDragPosition = 0f
            )
        }
    }

    fun changeCard() {
        _uiState.update { currentState ->
            currentState.copy(
                currentCardIndex = currentState.currentCardIndex + 1
            )
        }
        if (
            _uiState.value.currentCardIndex + 1 + dataLoadThreshold > _uiState.value.dataList.size
            && !_uiState.value.isLoadingMoreData
        ) {
            Log.d("LOAD", "Need to load more data")
            _uiState.update { currentState ->
                currentState.copy(
                    isLoadingMoreData = true
                )
            }
        }
    }

    fun getCurrentCardData(): T? {
        return if (_uiState.value.currentCardIndex >= _uiState.value.dataList.size) null else _uiState.value.dataList[_uiState.value.currentCardIndex]
    }

    fun getNextCardData(): T? {
        return if (_uiState.value.currentCardIndex + 1 >= _uiState.value.dataList.size) null else _uiState.value.dataList[_uiState.value.currentCardIndex + 1]
    }
}
