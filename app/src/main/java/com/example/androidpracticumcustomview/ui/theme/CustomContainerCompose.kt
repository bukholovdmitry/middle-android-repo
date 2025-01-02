package com.example.androidpracticumcustomview.ui.theme

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.unit.dp
import com.example.androidpracticumcustomview.R

/*
Задание:
Реализуйте необходимые компоненты;
Создайте проверку что дочерних элементов не более 2-х;
Предусмотрите обработку ошибок рендера дочерних элементов.
Задание по желанию:
Предусмотрите параметризацию длительности анимации.
 */
@Composable
fun CustomContainerCompose(
    firstChild: @Composable (() -> Unit)?,
    secondChild: @Composable (() -> Unit)?
) {
    var isFirstVisible by remember { mutableStateOf(false) }
    var isSecondVisible by remember { mutableStateOf(false) }
    val localDensity = LocalDensity.current
    var columnHeightDp by remember {
        mutableStateOf(0.dp)
    }
    val durationTranslation = integerResource(R.integer.translation_duration)
    val durationAlpha = integerResource(R.integer.alpha_duration)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .onGloballyPositioned { coordinates ->
                columnHeightDp = with(localDensity) { coordinates.size.height.toDp() }
            }
    ) {
        firstChild?.let {
            AnimatedVisibility(
                visible = isFirstVisible,
                enter = fadeIn(animationSpec = tween(durationMillis = durationAlpha)) +
                        slideInVertically(
                            initialOffsetY = { fullHeight: Int -> fullHeight / 2 },
                            animationSpec = tween(
                                durationMillis = durationTranslation,
                                easing = LinearEasing
                            )
                        )
            ) {
                Box(
                    modifier = Modifier
                        .height(columnHeightDp / 2)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center,
                ) {
                    firstChild()
                }
            }
        }

        secondChild?.let {
            AnimatedVisibility(
                visible = isSecondVisible,
                enter = fadeIn(animationSpec = tween(durationMillis = durationAlpha)) +
                        slideInVertically(
                            initialOffsetY = { fullHeight: Int -> -fullHeight / 2 },
                            animationSpec = tween(
                                durationMillis = durationTranslation,
                                easing = LinearEasing
                            )
                        )
            ) {
                Box(
                    modifier = Modifier
                        .height(columnHeightDp / 2)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    secondChild()
                }
            }

            LaunchedEffect(Unit) {
                isFirstVisible = true
                isSecondVisible = true
            }
        }

    }
}