package com.alecarnevale.goosenav.steps.name

import androidx.navigation.*
import androidx.navigation.compose.composable

private const val EXTRA_IS_EDITING_MODE = "extra_is_editing_mode"
const val nameNavigationRoute = "name"

fun NavController.navigateToName(
  isEditingMode: Boolean
) {
  this.navigate("$nameNavigationRoute/$isEditingMode")
}

fun NavGraphBuilder.nameScreen(
  navigateToNext: (String) -> Unit,
  exit: () -> Unit,
  finishOnEditingMode: (String) -> Unit
) {
  composable(
    route = "$nameNavigationRoute/{$EXTRA_IS_EDITING_MODE}",
    arguments = listOf(
      navArgument(EXTRA_IS_EDITING_MODE) {
        type = NavType.BoolType
      }
    )
  ) { backStackEntry ->
    val isEditingMode = requireNotNull(backStackEntry.arguments?.getBoolean(EXTRA_IS_EDITING_MODE))
    NameContent(
      navigateToNext = navigateToNext,
      exit = exit,
      finishOnEditingMode = if (isEditingMode) finishOnEditingMode else null
    )
  }
}