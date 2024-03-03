package com.ibnu.gemriawithcompose.presentation.home

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.ibnu.gemriawithcompose.presentation.util.GameTestData
import com.ibnu.gemriawithcompose.ui.theme.GemriaWithComposeTheme
import com.ibnu.gemriawithcompose.utils.formatPriceThousand
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.androidx.compose.getViewModel

class HomeScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController: TestNavHostController

    private val fakeGame = GameTestData.fakeGame

    @Before
    fun setUp() {
        composeTestRule.setContent {
            GemriaWithComposeTheme {
                navController = TestNavHostController(LocalContext.current)
                navController.navigatorProvider.addNavigator(ComposeNavigator())
                val viewModel = getViewModel<HomeViewModel>()

                HomeScreen(
                    navController,
                    viewModel
                )
            }
        }
        composeTestRule.onRoot().printToLog("currentLabelExists")
    }

    @Test
    fun homeContent_isDisplayed() {
        composeTestRule.onNodeWithText(fakeGame.name).assertIsDisplayed()
        composeTestRule.onNodeWithText(fakeGame.categories).assertIsDisplayed()
        composeTestRule.onNodeWithText(fakeGame.price.formatPriceThousand()).assertIsDisplayed()
    }

}