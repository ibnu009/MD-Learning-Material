package com.ibnu.gemriawithcompose.presentation.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.ibnu.gemriawithcompose.ui.components.LottieAnimationLoader
import com.ibnu.gemriawithcompose.ui.navigation.ScreenRoute
import com.ibnu.gemriawithcompose.utils.AnimationConstant.SPLASH_ANIMATION
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    LaunchedEffect(key1 = true) {
        delay(SPLASH_ANIMATION)
        navController.navigate(ScreenRoute.Home.route)
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        LottieAnimationLoader()
    }
}