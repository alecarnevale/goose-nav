package com.alecarnevale.goosenav.steps

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.alecarnevale.goosenav.Goose
import com.alecarnevale.goosenav.GooseColor
import com.alecarnevale.goosenav.steps.color.colorScreen
import com.alecarnevale.goosenav.steps.color.navigateToColor
import com.alecarnevale.goosenav.steps.home.homeScreen
import com.alecarnevale.goosenav.steps.home.navigateToHome
import com.alecarnevale.goosenav.steps.home.startDestination
import com.alecarnevale.goosenav.steps.jumppower.jumpPowerScreen
import com.alecarnevale.goosenav.steps.jumppower.navigateToJumpPower
import com.alecarnevale.goosenav.steps.name.nameNavigationRoute
import com.alecarnevale.goosenav.steps.name.nameScreen
import com.alecarnevale.goosenav.steps.name.navigateToName
import com.alecarnevale.goosenav.steps.summary.navigateToSummary
import com.alecarnevale.goosenav.steps.summary.summaryScreen

@Composable
fun MainNav(
  navController: NavHostController = rememberNavController(),
  updateName: (String) -> Unit,
  updateColor: (GooseColor) -> Unit,
  updateJumpCounter: () -> Int,
  clearJumpCounter: () -> Unit,
  confirmedGoose: () -> Goose?,
  confirmGoose: (Goose) -> Unit,
  buildingGoose: () -> Goose
) {
  NavHost(
    navController = navController,
    startDestination = startDestination
  ) {
    homeScreen(
      navigateToNext = {
        clearJumpCounter()
        updateJumpCounter()
        navController.navigateToName(false)
      }
    )
    nameScreen(
      navigateToNext = {
        updateName(it)
        updateJumpCounter()
        navController.navigateToColor(false)
      },
      exit = { navController.navigateToHome(confirmedGoose()) },
      finishOnEditingMode = {
        updateName(it)
        updateJumpCounter()
        navController.navigateToSummary(buildingGoose())
      }
    )
    colorScreen(
      navigateToBack = {
        navController.popBackStack()
      },
      navigateToNext = {
        updateColor(it)
        val currentJumpPower = updateJumpCounter()
        navController.navigateToJumpPower(jumpCounter = currentJumpPower, isEditingMode = false)
      },
      exit = { navController.navigateToHome(confirmedGoose()) },
      finishOnEditingMode = {
        updateColor(it)
        updateJumpCounter()
        navController.navigateToSummary(buildingGoose())
      }
    )
    jumpPowerScreen(
      navigateToBackBack = {
        navController.popBackStack(route = nameNavigationRoute, inclusive = false)
      },
      navigateToBack = {
        navController.popBackStack()
      },
      navigateToNext = {
        navController.navigateToSummary(buildingGoose())
      },
      exit = { navController.navigateToHome(confirmedGoose()) },
      finishOnEditingMode = {
        navController.navigateToSummary(buildingGoose())
      }
    )
    summaryScreen(
      navigateToName = {
        updateJumpCounter()
        navController.navigateToName(true)
      },
      navigateToColor = {
        updateJumpCounter()
        navController.navigateToColor(true)
      },
      navigateToJumpPower = {
        val currentJumpPower = updateJumpCounter()
        navController.navigateToJumpPower(jumpCounter = currentJumpPower, isEditingMode = true)
      },
      confirmGoose = {
        confirmGoose(it)
        navController.navigateToHome(confirmedGoose())
      }
    )
  }
}