package com.alecarnevale.goosenav.steps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
          navigateToNext = { navController.navigateToName() }
        )
        nameScreen(
          navigateToNext = { navController.navigateToColor() },
          exit = { navController.navigateToHome() }
        )
        colorScreen(
          navigateToBack = { navController.popBackStack() },
          navigateToNext = { navController.navigateToJumpPower() },
          exit = { navController.navigateToHome() }
        )
        jumpPowerScreen(
          navigateToBackBack = { navController.popBackStack(route = nameNavigationRoute, inclusive = false) },
          navigateToBack = { navController.popBackStack() },
          navigateToNext = { navController.navigateToSummary() },
          exit = { navController.navigateToHome() }
        )
        summaryScreen()
      }
    }
  }

  companion object {
    var jumpCounter: Int = 0
    var goose: Goose? = null
  }
}