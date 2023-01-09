package com.alecarnevale.goosenav.steps

import android.os.Bundle
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread
import com.alecarnevale.goosenav.Goose
import com.alecarnevale.goosenav.GooseColor
import com.alecarnevale.goosenav.steps.name.EXTRA_IS_EDITING_MODE
import com.alecarnevale.goosenav.steps.name.nameNavigationRoute
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class MainNavTest {

  @get:Rule(order = 0)
  val hiltRule = HiltAndroidRule(this)

  @get:Rule(order = 1)
  val composeTestRule = createAndroidComposeRule<MainActivity>()
  lateinit var navController: TestNavHostController

  @Before
  fun setupAppNavHost() {
    composeTestRule.setContent {
      navController = TestNavHostController(LocalContext.current)
      navController.navigatorProvider.addNavigator(ComposeNavigator())
      MainNav(
        navController = navController,
        updateName = {},
        updateColor = {},
        updateJumpCounter = { 0 },
        clearJumpCounter = {},
        confirmedGoose = { null },
        confirmGoose = {},
        buildingGoose = { Goose("test", GooseColor.BLACK, 0) }
      )
    }
  }

  // Unit test
  @Test
  fun mainNav_verifyStartDestination() {
    composeTestRule
      .onNodeWithText("La tua oca non è pronta")
      .assertIsDisplayed()
  }

  @Test
  fun mainNav_clickStart() {
    composeTestRule.onNodeWithText("Costruisci la tua oca!").assertIsDisplayed().performClick()

    composeTestRule.onNodeWithText("Come si chiama l'oca?").assertIsDisplayed()

    // valuable?
    val route = navController.currentBackStackEntry?.destination?.route
    assertEquals(nameNavigationRoute, route)
  }

  @Test
  fun mainNav_setNameFalseDestination() {
    runOnUiThread {
      navController.setCurrentDestination(
        nameNavigationRoute,
        Bundle().apply { putBoolean(EXTRA_IS_EDITING_MODE, false) })
    }

    composeTestRule.onNodeWithText("Come si chiama l'oca?").assertIsDisplayed()
    composeTestRule.onNodeWithText("Avanti").assertIsDisplayed()
    composeTestRule.onNodeWithText("OK").assertDoesNotExist()
  }

  @Test
  fun mainNav_setNameTrueDestination() {
    runOnUiThread {
      navController.setCurrentDestination(
        nameNavigationRoute,
        Bundle().apply { putBoolean(EXTRA_IS_EDITING_MODE, true) })
    }

    composeTestRule.onNodeWithText("Come si chiama l'oca?").assertIsDisplayed()
    composeTestRule.onNodeWithText("Avanti").assertDoesNotExist()
    composeTestRule.onNodeWithText("OK").assertIsDisplayed()
  }
}