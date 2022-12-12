package com.alecarnevale.goosenav.steps.name

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val nameNavigationRoute = "name"

fun NavController.navigateToName(navOptions: NavOptions? = null) {
  this.navigate(nameNavigationRoute, navOptions)
}

fun NavGraphBuilder.nameScreen(
  navigateToNext: () -> Unit,
  exit: () -> Unit
) {
  composable(route = nameNavigationRoute) {
    NameContent(navigateToNext = navigateToNext, exit = exit)
  }
}