package com.alecarnevale.goosenav.steps.color

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.alecarnevale.goosenav.GooseColor

const val colorNavigationRoute = "color"

fun NavController.navigateToColor(navOptions: NavOptions? = null) {
  this.navigate(colorNavigationRoute, navOptions)
}

fun NavGraphBuilder.colorScreen(
  navigateToBack: () -> Unit,
  navigateToNext: (GooseColor) -> Unit,
  exit: () -> Unit
) {
  composable(route = colorNavigationRoute) {
    ColorContent(
      navigateToBack = navigateToBack,
      navigateToNext = { navigateToNext(it) },
      exit = exit
    )
  }
}