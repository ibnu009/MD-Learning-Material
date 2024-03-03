package com.ibnu.gemriawithcompose.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ibnu.gemriawithcompose.R
import com.ibnu.gemriawithcompose.ui.components.GameItem
import com.ibnu.gemriawithcompose.ui.components.SearchBar
import com.ibnu.gemriawithcompose.ui.navigation.ScreenRoute
import com.ibnu.gemriawithcompose.utils.rememberFlowWithLifecycle

@Composable
fun HomeScreen(
    navHostController: NavHostController,
    homeViewModel: HomeViewModel
) {
    val homeModelState by rememberFlowWithLifecycle(homeViewModel.homeModelState)
        .collectAsState(initial = HomeModelState.Empty)

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = "Gemria",
                fontWeight = FontWeight.Bold
                )
            Spacer(modifier = Modifier.weight(1.0f))
            IconHome(iconId = R.drawable.ic_person, contentDescription = "about_page") {
                navHostController.navigate(ScreenRoute.Profile.route)
            }
            IconHome(iconId = R.drawable.ic_cart, contentDescription = "cart_page") {
                navHostController.navigate(ScreenRoute.Cart.route)
            }
        }
        SearchBar(
            searchText = homeModelState.searchText,
            onSearchTextChanged = {
                homeViewModel.onSearchTextChanged(it)
            },
            onClearClick = {
                homeViewModel.onClearClick()
            }
        )

        LazyColumn {
            items(homeModelState.games, key = { it.id }) { game ->
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

@Composable
fun IconHome(iconId: Int, contentDescription: String, onClick: () -> Unit){
    IconButton(
        onClick = {
            onClick()
        },
        modifier = Modifier
            .padding(horizontal = 8.dp)
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = contentDescription
        )
    }
}