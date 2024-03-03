package com.ibnu.gemriawithcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ibnu.gemriawithcompose.presentation.cart.CartScreen
import com.ibnu.gemriawithcompose.presentation.cart.CartViewModel
import com.ibnu.gemriawithcompose.presentation.detail.DetailScreen
import com.ibnu.gemriawithcompose.presentation.detail.DetailViewModel
import com.ibnu.gemriawithcompose.presentation.home.HomeScreen
import com.ibnu.gemriawithcompose.presentation.home.HomeViewModel
import com.ibnu.gemriawithcompose.presentation.profile.ProfileScreen
import com.ibnu.gemriawithcompose.presentation.splash.SplashScreen
import com.ibnu.gemriawithcompose.presentation.splash.SplashViewModel
import com.ibnu.gemriawithcompose.ui.navigation.ScreenRoute
import com.ibnu.gemriawithcompose.ui.theme.GemriaWithComposeTheme
import org.koin.androidx.compose.getViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GemriaWithComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController()
) {
    Scaffold{ innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ScreenRoute.Splash.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(ScreenRoute.Splash.route) {
                val viewModel = getViewModel<SplashViewModel>()
                SplashScreen(navController = navController)
            }

            composable(ScreenRoute.Home.route) {
                val viewModel = getViewModel<HomeViewModel>()
                HomeScreen(homeViewModel = viewModel, navHostController = navController)
            }

            composable(
                route = ScreenRoute.Detail.route,
                arguments = listOf(navArgument("id") { type = NavType.IntType }),
            ) {
                val viewModel = getViewModel<DetailViewModel>()
                val id = it.arguments?.getInt("id") ?: -1
                DetailScreen(navController, viewModel, id)
            }

            composable(ScreenRoute.Cart.route) {
                val viewModel = getViewModel<CartViewModel>()
                CartScreen(cartViewModel = viewModel, navHostController = navController)
            }

            composable(ScreenRoute.Profile.route) {
                ProfileScreen(navController)
            }
        }
    }
}