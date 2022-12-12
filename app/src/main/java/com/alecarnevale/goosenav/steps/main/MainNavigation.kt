package com.alecarnevale.goosenav.steps.main

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val mainNavigationRoute = "main"

fun NavController.navigateToMain() {
  this.navigate(mainNavigationRoute) {
    popUpTo(mainNavigationRoute) { inclusive = true }
  }
}

fun NavGraphBuilder.mainScreen(
  navigateToNext: () -> Unit
) {
  composable(route = mainNavigationRoute) {
    MainContent(navigateToNext = navigateToNext)
  }
}