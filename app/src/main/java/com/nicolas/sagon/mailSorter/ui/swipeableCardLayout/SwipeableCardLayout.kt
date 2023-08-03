package com.nicolas.sagon.mailSorter.ui.swipeableCardLayout

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nicolas.sagon.mailSorter.ui.swipeableCardLayout.dummyData.DummyNoMoreDataCard
import com.nicolas.sagon.mailSorter.ui.swipeableCardLayout.dummyData.DummySwipeableCard
import com.nicolas.sagon.mailSorter.ui.swipeableCardLayout.dummyData.getCardTestData
import com.nicolas.sagon.mailSorter.ui.theme.MailSorterTheme
import kotlin.math.abs

const val POINTER_INPUT_KEY = "box_pointer_input_key"

@Composable
fun <T> SwipeableCardLayout(
    transitionTrigger: Int = 0,
    data: List<T>,
    content: @Composable (data: T) -> Unit,
    noMoreData: @Composable () -> Unit,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
    onSwipe: (swipeEvent: SwipeEvent, data: T) -> Unit = { _, _ -> },
) {

    val swipeableCardLayoutViewModel by remember {
        mutableStateOf(SwipeableCardLayoutViewModel(data = data))
    }
    val swipeableCardLayoutState by swipeableCardLayoutViewModel.uiState.collectAsState()

    val animationSpec = tween<Float>(500, easing = CubicBezierEasing(0.4F, 0.0F, 0.8F, 0.8F))

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        val boxModifier = Modifier
            .pointerInput(POINTER_INPUT_KEY) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        if (event.type == PointerEventType.Move) {
                            swipeableCardLayoutViewModel.updateDragOffset(event.changes.first().position.x)
                        }
                        if (event.type == PointerEventType.Press) {
                            swipeableCardLayoutViewModel.startDrag(event.changes.first().position.x)
                        }
                        if (event.type == PointerEventType.Release) {
                            swipeableCardLayoutViewModel.pressEnd()
                        }
                    }
                }
            }
            .weight(1F)
            .padding(all = 48.dp)
        Box(
            modifier = boxModifier
        ) {
            val nextCard = swipeableCardLayoutViewModel.getNextCardData()
            val currentCard = swipeableCardLayoutViewModel.getCurrentCardData()

            if (nextCard == null && currentCard == null) {
                noMoreData()
            } else {
                if (nextCard !== null) {
                    AnimatedCard(
                        data = nextCard,
                        contentComposable = content,
                        modifier = Modifier.fillMaxSize(),
                    )
                }
                AnimatedCard(
                    data = currentCard!!,
                    contentComposable = content,
                    maxOffset = swipeableCardLayoutViewModel.swipeOffset,
                    offset = swipeableCardLayoutState.touchableCardOffset,
                    isDragged = swipeableCardLayoutState.initDragPosition != 0f,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }

        when (swipeableCardLayoutState.animationState) {
            AnimationState.RETURN_RIGHT, AnimationState.RETURN_LEFT -> {
                LaunchedEffect(key1 = transitionTrigger) {
                    animate(
                        initialValue = swipeableCardLayoutState.touchableCardOffset,
                        targetValue = 0f,
                        animationSpec = animationSpec
                    ) { value: Float, _: Float ->
                        swipeableCardLayoutViewModel.updateOffsetForAnimation(value)
                    }
                    swipeableCardLayoutViewModel.finishAnimation()
                }
            }

            AnimationState.PASS_RIGHT -> {
                swipeableCardLayoutViewModel.getCurrentCardData()?.let {
                    onSwipe(SwipeEvent.RIGHT, it)
                }
                LaunchedEffect(key1 = transitionTrigger) {
                    animate(
                        initialValue = swipeableCardLayoutState.touchableCardOffset,
                        targetValue = swipeableCardLayoutViewModel.getPassRightAnimationTarget()
                    ) { value: Float, _: Float ->
                        swipeableCardLayoutViewModel.updateOffsetForAnimation(value)
                    }
                    swipeableCardLayoutViewModel.finishAnimation()
                    swipeableCardLayoutViewModel.changeCard()
                }
            }

            AnimationState.PASS_LEFT -> {
                swipeableCardLayoutViewModel.getCurrentCardData()?.let {
                    onSwipe(SwipeEvent.LEFT, it)
                }
                LaunchedEffect(key1 = transitionTrigger) {
                    animate(
                        initialValue = swipeableCardLayoutState.touchableCardOffset,
                        targetValue = swipeableCardLayoutViewModel.getPassLeftAnimationTarget()
                    ) { value: Float, _: Float ->
                        swipeableCardLayoutViewModel.updateOffsetForAnimation(value)
                    }
                    swipeableCardLayoutViewModel.finishAnimation()
                    swipeableCardLayoutViewModel.changeCard()
                }
            }

            else -> {
                // Do nothing
            }
        }
    }
}

@Composable
fun <T> AnimatedCard(
    data: T,
    contentComposable: @Composable (data: T) -> Unit,
    modifier: Modifier = Modifier,
    isDragged: Boolean = false,
    maxOffset: Float = 0f,
    offset: Float = 0f,
) {
    var isCardVisible = true
    if (!isDragged && abs(offset) > maxOffset) {
        isCardVisible = false
    }
    AnimatedVisibility(
        visible = isCardVisible,
        enter = EnterTransition.None,
        exit = fadeOut(),
        modifier = modifier.absoluteOffset(x = offset.dp)
    ) {
        contentComposable(data = data)
    }
}

@Preview(showBackground = true)
@Composable
fun SwipeableCardLayoutPreview() {
    MailSorterTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            SwipeableCardLayout(data = getCardTestData(), content = {
                DummySwipeableCard(it)
            }, noMoreData = {
                DummyNoMoreDataCard()
            })
        }
    }
}