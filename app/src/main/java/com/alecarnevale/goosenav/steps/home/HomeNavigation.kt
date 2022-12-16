package com.alecarnevale.goosenav.steps.home

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.alecarnevale.goosenav.Goose
import com.alecarnevale.goosenav.steps.summary.GooseParamType
import com.google.gson.Gson

const val EXTRA_HOME_GOOSE_DATA = "extra_home_goose_data"
const val homeNavigationRoute = "home"
private val gson = Gson()

fun NavController.navigateToHome(
  goose: Goose?
) {
  val route = if (goose != null) {
    val gooseJSON = gson.toJson(goose)
    "$homeNavigationRoute?$EXTRA_HOME_GOOSE_DATA=$gooseJSON"
  } else {
    homeNavigationRoute
  }
  this.navigate(route) {
    popUpTo(route) { inclusive = true }
  }
}

fun NavGraphBuilder.homeScreen(
  navigateToNext: () -> Unit
) {
  val route = "$homeNavigationRoute?$EXTRA_HOME_GOOSE_DATA={$EXTRA_HOME_GOOSE_DATA}"
  val arguments = listOf(
    navArgument(EXTRA_HOME_GOOSE_DATA) {
      type = GooseParamType()
      nullable = true
    }
  )
  composable(route = route, arguments = arguments) { backStackEntry ->
    val goose = backStackEntry.arguments?.getParcelable<Goose>(EXTRA_HOME_GOOSE_DATA)
    backStackEntry.savedStateHandle[EXTRA_HOME_GOOSE_DATA] = goose
    HomeContent(navigateToNext = navigateToNext)
  }
}
