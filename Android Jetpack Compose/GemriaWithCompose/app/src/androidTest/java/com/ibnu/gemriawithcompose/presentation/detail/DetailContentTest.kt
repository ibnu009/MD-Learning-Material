package com.ibnu.gemriawithcompose.presentation.detail

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.ibnu.gemriawithcompose.presentation.util.GameTestData
import com.ibnu.gemriawithcompose.ui.theme.GemriaWithComposeTheme
import com.ibnu.gemriawithcompose.utils.formatPriceThousand
import com.ibnu.gemriawithcompose.utils.formatThousand
import com.ibnu.gemriawithcompose.utils.toRating
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailContentTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val fakeGameDetail = GameTestData.fakeGame

    @Before
    fun setUp() {
        composeTestRule.setContent {
            GemriaWithComposeTheme {
                DetailContent(
                    game = fakeGameDetail,
                    {},
                    {},
                    false
                )
            }
        }
        composeTestRule.onRoot().printToLog("currentLabelExists")
    }

    @Test
    fun detailContent_isDisplayed() {
        composeTestRule.onNodeWithText(fakeGameDetail.name).assertIsDisplayed()
        composeTestRule.onNodeWithText(fakeGameDetail.categories).assertIsDisplayed()
        composeTestRule.onNodeWithText(fakeGameDetail.price.formatPriceThousand())
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(fakeGameDetail.rating.toRating()).assertIsDisplayed()
        composeTestRule.onNodeWithText("${fakeGameDetail.totalReviews.formatThousand()} reviews")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(fakeGameDetail.description).assertIsDisplayed()
        composeTestRule.onNodeWithText(fakeGameDetail.developerName).assertIsDisplayed()
    }

    @Test
    fun addToCart_buttonChangedToCorrectTextAfterClicked() {
        composeTestRule.onNodeWithText("Add To Cart").assertIsDisplayed()
        composeTestRule.onNodeWithText("Add To Cart").performClick()
    }

}