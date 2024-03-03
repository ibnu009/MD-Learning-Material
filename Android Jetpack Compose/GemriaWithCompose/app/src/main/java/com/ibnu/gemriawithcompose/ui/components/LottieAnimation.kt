package com.ibnu.gemriawithcompose.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ibnu.gemriawithcompose.R

@Composable
fun LottieAnimationLoader() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.gemria_lottie_splash))
    val progress by animateLottieCompositionAsState(composition)
    LottieAnimation(
        composition = composition,
        progress = { progress },
    )
}