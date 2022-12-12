package com.alecarnevale.goosenav.steps.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val homeNavigationRoute = "home"

fun NavController.navigateToHome() {
  this.navigate(homeNavigationRoute) {
    popUpTo(homeNavigationRoute) { inclusive = true }
  }
}

fun NavGraphBuilder.homeScreen(
  navigateToNext: () -> Unit
) {
  composable(route = homeNavigationRoute) {
    HomeContent(navigateToNext = navigateToNext)
  }
}