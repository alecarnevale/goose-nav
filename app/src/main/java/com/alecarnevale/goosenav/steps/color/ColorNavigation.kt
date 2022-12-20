package com.alecarnevale.goosenav.steps.color

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.alecarnevale.goosenav.GooseColor
import com.alecarnevale.goosenav.steps.name.nameNavigationRoute

private const val EXTRA_IS_EDITING_MODE = "extra_is_editing_mode"
const val colorNavigationRoute = "color"

fun NavController.navigateToColor(
  isEditingMode: Boolean
) {
  this.navigate("$colorNavigationRoute/$isEditingMode")
}

fun NavGraphBuilder.colorScreen(
  navigateToBack: () -> Unit,
  navigateToNext: (GooseColor) -> Unit,
  exit: () -> Unit,
  finishOnEditingMode: (GooseColor) -> Unit
) {
  composable(
    route = "$colorNavigationRoute/{${EXTRA_IS_EDITING_MODE}}",
    arguments = listOf(
      navArgument(EXTRA_IS_EDITING_MODE) {
        type = NavType.BoolType
      }
    )
  ) { backStackEntry ->
    val isEditingMode = requireNotNull(backStackEntry.arguments?.getBoolean(EXTRA_IS_EDITING_MODE))
    ColorContent(
      navigateToBack = navigateToBack,
      navigateToNext = { navigateToNext(it) },
      exit = exit,
      finishOnEditingMode = if (isEditingMode) finishOnEditingMode else null
    )
  }
}