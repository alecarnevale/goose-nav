package com.alecarnevale.goosenav.steps.summary

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val summaryNavigationRoute = "summary"

fun NavController.navigateToSummary(navOptions: NavOptions? = null) {
  this.navigate(summaryNavigationRoute, navOptions)
}

fun NavGraphBuilder.summaryScreen() {
  composable(route = summaryNavigationRoute) {
    SummaryContent()
  }
}