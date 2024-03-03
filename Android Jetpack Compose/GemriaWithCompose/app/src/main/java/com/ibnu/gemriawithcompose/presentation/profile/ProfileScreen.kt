package com.ibnu.gemriawithcompose.presentation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
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

@Composable
fun ProfileScreen(
    navController: NavController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "About Me")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                modifier = Modifier
                    .width(200.dp)
                    .height(250.dp)
                    .align(Alignment.CenterHorizontally)
                    .padding(start = 16.dp, top = 16.dp),
                elevation = 8.dp,
                shape = RoundedCornerShape(8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.my_photo),
                    contentDescription = "Profile",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
            ContentLabel(label = "Nama Lengkap")
            Spacer(modifier = Modifier.height(8.dp))
            ContentText(text = "Ibnu Batutah")

            Spacer(modifier = Modifier.height(24.dp))

            ContentLabel(label = "Email")
            Spacer(modifier = Modifier.height(8.dp))
            ContentText(text = "ibnubatutah001@gmail.com")
        }
    }
}

@Composable
private fun ContentLabel(label: String) {
    Text(
        text = label,
        color = Color.White,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
    )
}

@Composable
private fun ContentText(text: String) {
    Text(
        text = text,
        modifier = Modifier
            .fillMaxWidth()
            .background(
                shape = RoundedCornerShape(8.dp),
                color = Color.White
            )
            .padding(16.dp),
        fontWeight = FontWeight.Bold,
        color = Color.Black
    )
}

