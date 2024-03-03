package com.ibnu.gemriawithcompose.presentation.cart

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ibnu.gemriawithcompose.R
import com.ibnu.gemriawithcompose.ui.components.GameItem
import com.ibnu.gemriawithcompose.ui.navigation.ScreenRoute

@Composable
fun CartScreen(
    navHostController: NavHostController,
    cartViewModel: CartViewModel
) {
    cartViewModel.retrieveCartGames()
    val cartGames by cartViewModel.gameResult.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Cart")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navHostController.popBackStack()
                        },
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = "Back to previous screen"
                        )
                    }
                },
                elevation = 12.dp
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            ) {
            val commentsAlpha = if (cartGames.isEmpty()) 1f else 0f
            Text(
                modifier = Modifier
                    .align(Alignment.Center)
                    .alpha(commentsAlpha),
                text = "No Data",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            LazyColumn {
                items(cartGames,  key = { it.id }) { game ->
                    GameItem(
                        name = game.name,
                        categories = game.categories,
                        price = game.price,
                        rating = game.rating,
                        reviews = game.totalReviews,
                        posterImage = game.posterImage
                    ) {
                        navHostController.navigate(ScreenRoute.Detail.createRoute(game.id))
                    }
                }
            }
        }
    }
}
