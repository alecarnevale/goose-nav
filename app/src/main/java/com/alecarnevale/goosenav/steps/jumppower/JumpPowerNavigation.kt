package com.alecarnevale.goosenav.steps.jumppower

import androidx.navigation.*
import androidx.navigation.compose.composable

const val EXTRA_JUMP_COUNTER = "extra_jump_counter"
const val jumpPowerNavigationRoute = "jump_power"

fun NavController.navigateToJumpPower(
  jumpCounter: Int,
  navOptions: NavOptions? = null
) {
  this.navigate("$jumpPowerNavigationRoute/$jumpCounter", navOptions)
}

fun NavGraphBuilder.jumpPowerScreen(
  navigateToBackBack: () -> Unit,
  navigateToBack: () -> Unit,
  navigateToNext: () -> Unit,
  exit: () -> Unit
) {
  composable(
    route = "$jumpPowerNavigationRoute/{${EXTRA_JUMP_COUNTER}}",
    arguments = listOf(
      navArgument(EXTRA_JUMP_COUNTER) {
        type = NavType.IntType
      }
    )
  ) { backStackEntry ->
    val jumpCounter = backStackEntry.arguments?.getInt(EXTRA_JUMP_COUNTER)
    backStackEntry.savedStateHandle[EXTRA_JUMP_COUNTER] = jumpCounter
    JumpPowerContent(
      navigateToBackBack = navigateToBackBack,
      navigateToBack = navigateToBack,
      navigateToNext = navigateToNext,
      exit = exit
    )
  }
}