package com.ibnu.gemriawithcompose.presentation.detail

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ibnu.gemriawithcompose.R
import com.ibnu.gemriawithcompose.data.model.Game
import com.ibnu.gemriawithcompose.utils.formatPriceThousand
import com.ibnu.gemriawithcompose.utils.formatThousand
import com.ibnu.gemriawithcompose.utils.rememberFlowWithLifecycle
import com.ibnu.gemriawithcompose.utils.toRating

@Composable
fun DetailScreen(
    navController: NavController,
    detailViewModel: DetailViewModel,
    id: Int
) {
    detailViewModel.retrieveGameDetail(id)

    val detailModelState by rememberFlowWithLifecycle(detailViewModel.detailModelState)
        .collectAsState(initial = DetailModelState.Loading)

    val game = detailModelState.game
    if (game != null) {
        DetailContent(
            game = game,
            onClickBack = {
                navController.popBackStack()
            },
            onClickCart = {
                detailViewModel.onAddToCart()
            },
            isAddedToCart = detailModelState.isAddedToCart
        )
    }

}

@Composable
fun DetailContent(
    game: Game,
    onClickBack: () -> Unit,
    onClickCart: () -> Unit,
    isAddedToCart: Boolean
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        GameBackGroundImage(game.backgroundImage) {
            onClickBack()
        }

        GamePoster(game.posterImage)

        Text(
            text = game.name,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.LightGray,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
        )

        CategoryAndPrice(category = game.categories, price = game.price)
        GameRating(rating = game.rating, totalReview = game.totalReviews)

        ContentLabel(label = "Description")
        ContentText(text = game.description)

        ContentLabel(label = "Developer")
        ContentText(text = game.developerName)

        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 32.dp),
            onClick = {
                onClickCart()
            }
        ) {
            Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "action_add_to_cart")
            Spacer(modifier = Modifier.width(width = 8.dp))
            Text(
                text = if (isAddedToCart)
                    "Remove From Cart"
                else "Add To Cart"
            )
        }
    }
}

@Composable
fun GamePoster(posterImage: Int) {
    Card(
        modifier = Modifier
            .width(100.dp)
            .height(100.dp)
            .padding(start = 16.dp, top = 16.dp),
        elevation = 8.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Image(
            painter = painterResource(id = posterImage),
            contentDescription = "Game poster image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

@Composable
private fun GameBackGroundImage(backgroundImage: Int, onClickBack: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f)
            .background(Color.Gray)
    ) {
        Image(
            painter = painterResource(id = backgroundImage),
            contentDescription = "Game background image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )
        IconButton(
            onClick = {
                onClickBack()
            },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.TopStart)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = "Back to previous screen"
            )
        }
    }
}

@Composable
private fun GameRating(rating: Double, totalReview: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(top = 8.dp)
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = rating.toRating(),
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.secondary,
        )
        Spacer(modifier = Modifier.width(4.dp))
        Divider(
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
            thickness = 1.dp,
            modifier = Modifier
                .width(1.dp)
                .height(12.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "${totalReview.formatThousand()} reviews",
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
        )
    }
}

@Composable
private fun CategoryAndPrice(category: String, price: Int) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = category,
            style = MaterialTheme.typography.caption,
            modifier = Modifier
        )

        Text(
            text = price.formatPriceThousand(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.secondary,
            modifier = Modifier
        )
    }
}

@Composable
private fun ContentLabel(label: String) {
    Text(
        text = label,
        fontSize = 14.sp,
        color = Color.White,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
    )
}

@Composable
private fun ContentText(text: String) {
    Text(
        text = text,
        fontSize = 14.sp,
        color = Color.LightGray,
        modifier = Modifier
            .padding(horizontal = 16.dp)
    )
}