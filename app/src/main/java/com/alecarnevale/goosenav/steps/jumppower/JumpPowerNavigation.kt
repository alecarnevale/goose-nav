package com.alecarnevale.goosenav.steps.jumppower

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val jumpPowerNavigationRoute = "jump_power"

fun NavController.navigateToJumpPower(navOptions: NavOptions? = null) {
  this.navigate(jumpPowerNavigationRoute, navOptions)
}

fun NavGraphBuilder.jumpPowerScreen(
  navigateToBackBack: () -> Unit,
  navigateToBack: () -> Unit,
  navigateToNext: () -> Unit,
  exit: () -> Unit
) {
  composable(route = jumpPowerNavigationRoute) {
    JumpPowerContent(
      navigateToBackBack = navigateToBackBack,
      navigateToBack = navigateToBack,
      navigateToNext = navigateToNext,
      exit = exit
    )
  }
}