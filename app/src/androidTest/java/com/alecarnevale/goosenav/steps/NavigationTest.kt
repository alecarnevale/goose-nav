package com.alecarnevale.goosenav.steps

import android.os.Bundle
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.espresso.Espresso.pressBack
import androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread
import com.alecarnevale.goosenav.Goose
import com.alecarnevale.goosenav.GooseColor
import com.alecarnevale.goosenav.steps.name.EXTRA_IS_EDITING_MODE
import com.alecarnevale.goosenav.steps.name.TAG_FIELD_NAME
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

  @Test
  fun complete_Navigation() {
    // looks like an integration tests
    // però non lo è effettivamente perché le callback di update settate nel before sono fake
    // l'intenzione qui è di testare la navigazione, non la validità dei dati

    // start
    composeTestRule.onNodeWithText("La tua oca non è pronta").assertIsDisplayed()
    composeTestRule.onNodeWithText("Costruisci la tua oca!").performClick()

    // name
    composeTestRule.onNodeWithTag(TAG_FIELD_NAME).performTextInput("camilla")
    composeTestRule.onNodeWithText("Avanti").performClick()

    // color
    composeTestRule.onNodeWithText("Di che colore è l'oca?").assertIsDisplayed()
    composeTestRule.onNodeWithText("Avanti").performClick()

    // jump power
    composeTestRule.onNodeWithText("La tua oca è in grado di saltare").assertIsDisplayed()
    composeTestRule.onNodeWithText("Indietro").performClick()

    // color
    composeTestRule.onNodeWithText("Di che colore è l'oca?").assertIsDisplayed()
    composeTestRule.onNodeWithText("Avanti").performClick()

    // jump power
    composeTestRule.onNodeWithText("La tua oca è in grado di saltare").assertIsDisplayed()
    composeTestRule.onNodeWithText("Avanti").performClick()

    // summary, back stack cleared
    composeTestRule.onNodeWithText("0").performClick()

    // jump power edit mode
    composeTestRule.onNodeWithText("La tua oca è in grado di saltare").assertIsDisplayed()
    composeTestRule.onNodeWithText("OK").performClick()

    // summary
    composeTestRule.onNodeWithText("Una superoca è pronta a scendere in campo").assertIsDisplayed()
    composeTestRule.onNodeWithText("OK").performClick()

    // home
    composeTestRule.onNodeWithText("La tua oca non è pronta").assertIsDisplayed()
  }

  @Test
  fun onSummary_backStackCleared() {
    // start
    composeTestRule.onNodeWithText("La tua oca non è pronta").assertIsDisplayed()
    composeTestRule.onNodeWithText("Costruisci la tua oca!").performClick()

    // name
    composeTestRule.onNodeWithTag(TAG_FIELD_NAME).performTextInput("camilla")
    composeTestRule.onNodeWithText("Avanti").performClick()

    // color
    composeTestRule.onNodeWithText("Di che colore è l'oca?").assertIsDisplayed()
    composeTestRule.onNodeWithText("Avanti").performClick()

    // jump power
    composeTestRule.onNodeWithText("La tua oca è in grado di saltare").assertIsDisplayed()
    composeTestRule.onNodeWithText("Indietro").performClick()

    // color
    composeTestRule.onNodeWithText("Di che colore è l'oca?").assertIsDisplayed()
    composeTestRule.onNodeWithText("Avanti").performClick()

    // jump power
    composeTestRule.onNodeWithText("La tua oca è in grado di saltare").assertIsDisplayed()
    composeTestRule.onNodeWithText("Avanti").performClick()

    // summary, back stack cleared
    composeTestRule.onNodeWithText("Una superoca è pronta a scendere in campo").assertIsDisplayed()
    pressBack()
    composeTestRule.onNodeWithText("La tua oca non è pronta").assertIsDisplayed()
  }
}