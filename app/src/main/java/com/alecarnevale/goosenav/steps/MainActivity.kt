package com.alecarnevale.goosenav.steps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.alecarnevale.goosenav.Goose
import com.alecarnevale.goosenav.steps.color.colorScreen
import com.alecarnevale.goosenav.steps.color.navigateToColor
import com.alecarnevale.goosenav.steps.jumppower.jumpPowerScreen
import com.alecarnevale.goosenav.steps.jumppower.navigateToJumpPower
import com.alecarnevale.goosenav.steps.home.homeNavigationRoute
import com.alecarnevale.goosenav.steps.home.homeScreen
import com.alecarnevale.goosenav.steps.home.navigateToHome
import com.alecarnevale.goosenav.steps.name.nameNavigationRoute
import com.alecarnevale.goosenav.steps.name.nameScreen
import com.alecarnevale.goosenav.steps.name.navigateToName
import com.alecarnevale.goosenav.steps.summary.navigateToSummary
import com.alecarnevale.goosenav.steps.summary.summaryScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      val navController = rememberNavController()
      NavHost(
        navController = navController,
        startDestination = homeNavigationRoute
      ) {
        homeScreen(
          navigateToNext = { navController.navigateTo(Destination.NAME) }
        )
        nameScreen(
          navigateToNext = { navController.navigateTo(Destination.COLOR) },
          exit = { navController.navigateTo(Destination.HOME) }
        )
        colorScreen(
          navigateToBack = {
            updateJumpCounter()
            navController.popBackStack()
          },
          navigateToNext = { navController.navigateTo(Destination.JUMP_POWER) },
          exit = { navController.navigateTo(Destination.HOME) }
        )
        jumpPowerScreen(
          navigateToBackBack = {
            updateJumpCounter()
            navController.popBackStack(route = nameNavigationRoute, inclusive = false)
          },
          navigateToBack = {
            updateJumpCounter()
            navController.popBackStack()
          },
          navigateToNext = { navController.navigateTo(Destination.SUMMARY) },
          exit = { navController.navigateTo(Destination.HOME) }
        )
        summaryScreen()
      }
    }
  }

  private fun NavController.navigateTo(destination: Destination) {
    return when (destination) {
      Destination.HOME -> navigateToHome().also { clearJumpCounter() }
      Destination.NAME -> navigateToName()
      Destination.COLOR -> navigateToColor()
      Destination.JUMP_POWER -> navigateToJumpPower()
      Destination.SUMMARY -> navigateToSummary()
    }.also { updateJumpCounter() }
  }

  private fun clearJumpCounter() {
    jumpCounter = 0
  }

  private fun updateJumpCounter() {
    jumpCounter += 1
  }

  private enum class Destination {
    HOME,
    NAME,
    COLOR,
    JUMP_POWER,
    SUMMARY
  }

  companion object {
    var jumpCounter: Int = 0
    var goose: Goose? = null
  }
}