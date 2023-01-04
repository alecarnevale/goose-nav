package com.alecarnevale.goosenav.steps.name

import androidx.navigation.*
import androidx.navigation.compose.composable

private const val EXTRA_IS_EDITING_MODE = "extra_is_editing_mode"
private const val _nameNavigationRoute = "name"
const val nameNavigationRoute = "$_nameNavigationRoute/{$EXTRA_IS_EDITING_MODE}"

fun NavController.navigateToName(
  isEditingMode: Boolean
) {
  this.navigate("$_nameNavigationRoute/$isEditingMode")
}

fun NavGraphBuilder.nameScreen(
  navigateToNext: (String) -> Unit,
  exit: () -> Unit,
  finishOnEditingMode: (String) -> Unit
) {
  composable(
    route = nameNavigationRoute,
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