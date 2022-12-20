package com.alecarnevale.goosenav.steps.jumppower

import androidx.navigation.*
import androidx.navigation.compose.composable

private const val EXTRA_IS_EDITING_MODE = "extra_is_editing_mode"
const val EXTRA_JUMP_COUNTER = "extra_jump_counter"
const val jumpPowerNavigationRoute = "jump_power"

fun NavController.navigateToJumpPower(
  jumpCounter: Int,
  isEditingMode: Boolean
) {
  this.navigate("$jumpPowerNavigationRoute/$jumpCounter/$isEditingMode")
}

fun NavGraphBuilder.jumpPowerScreen(
  navigateToBackBack: () -> Unit,
  navigateToBack: () -> Unit,
  navigateToNext: () -> Unit,
  exit: () -> Unit,
  finishOnEditingMode: () -> Unit
) {
  composable(
    route = "$jumpPowerNavigationRoute/{${EXTRA_JUMP_COUNTER}}/{$EXTRA_IS_EDITING_MODE}",
    arguments = listOf(
      navArgument(EXTRA_JUMP_COUNTER) {
        type = NavType.IntType
      },
      navArgument(EXTRA_IS_EDITING_MODE) {
        type = NavType.BoolType
      }
    )
  ) { backStackEntry ->
    val isEditingMode = requireNotNull(backStackEntry.arguments?.getBoolean(EXTRA_IS_EDITING_MODE))
    val jumpCounter = backStackEntry.arguments?.getInt(EXTRA_JUMP_COUNTER)
    backStackEntry.savedStateHandle[EXTRA_JUMP_COUNTER] = jumpCounter
    JumpPowerContent(
      navigateToBackBack = navigateToBackBack,
      navigateToBack = navigateToBack,
      navigateToNext = navigateToNext,
      exit = exit,
      finishOnEditingMode = if (isEditingMode) finishOnEditingMode else null
    )
  }
}