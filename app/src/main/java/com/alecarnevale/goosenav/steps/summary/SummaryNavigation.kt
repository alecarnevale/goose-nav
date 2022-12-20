package com.alecarnevale.goosenav.steps.summary

import android.os.Bundle
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.alecarnevale.goosenav.Goose
import com.alecarnevale.goosenav.steps.home.startDestination
import com.google.gson.Gson

const val EXTRA_SUMMARY_GOOSE_DATA = "extra_summary_goose_data"
const val summaryNavigationRoute = "summary"
private val gson = Gson()

fun NavController.navigateToSummary(
  goose: Goose
) {
  val navOptions = NavOptions
    .Builder()
    .setPopUpTo(route = startDestination, inclusive = false)
    .build()
  val gooseJSON = gson.toJson(goose)
  this.navigate("$summaryNavigationRoute/$gooseJSON", navOptions)
}

fun NavGraphBuilder.summaryScreen(
  navigateToName: () -> Unit,
  navigateToColor: () -> Unit,
  navigateToJumpPower: () -> Unit,
  confirmGoose: (Goose) -> Unit
) {
  composable(
    route = "$summaryNavigationRoute/{${EXTRA_SUMMARY_GOOSE_DATA}}",
    arguments = listOf(
      navArgument(EXTRA_SUMMARY_GOOSE_DATA) {
        type = GooseParamType()
        nullable = false
      }
    )
  ) { backStackEntry ->
    val goose = backStackEntry.arguments?.getParcelable<Goose>(EXTRA_SUMMARY_GOOSE_DATA)
    backStackEntry.savedStateHandle[EXTRA_SUMMARY_GOOSE_DATA] = goose
    SummaryContent(
      navigateToName = navigateToName,
      navigateToColor = navigateToColor,
      navigateToJumpPower = navigateToJumpPower,
      confirmGoose = { confirmGoose(it) }
    )
  }
}

// TODO qualcuno ha detto KSP?
class GooseParamType : NavType<Goose>(isNullableAllowed = true) { // isNullableAllowed perché usato anche in HomeNavigation
  override fun get(bundle: Bundle, key: String): Goose? {
    return bundle.getParcelable(key)
  }

  override fun parseValue(value: String): Goose {
    return Gson().fromJson(value, Goose::class.java)
  }

  override fun put(bundle: Bundle, key: String, value: Goose) {
    bundle.putParcelable(key, value)
  }
}