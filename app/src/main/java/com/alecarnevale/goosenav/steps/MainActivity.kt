package com.alecarnevale.goosenav.steps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.alecarnevale.goosenav.Goose
import com.alecarnevale.goosenav.GooseColor
import com.alecarnevale.goosenav.steps.color.colorScreen
import com.alecarnevale.goosenav.steps.color.navigateToColor
import com.alecarnevale.goosenav.steps.home.*
import com.alecarnevale.goosenav.steps.jumppower.jumpPowerScreen
import com.alecarnevale.goosenav.steps.jumppower.navigateToJumpPower
import com.alecarnevale.goosenav.steps.name.nameNavigationRoute
import com.alecarnevale.goosenav.steps.name.nameScreen
import com.alecarnevale.goosenav.steps.name.navigateToName
import com.alecarnevale.goosenav.steps.summary.navigateToSummary
import com.alecarnevale.goosenav.steps.summary.summaryScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  private var confirmedGoose: Goose? = null

  private lateinit var name: String
  private var jumpCounter: Int = 0
  private lateinit var color: GooseColor

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      val navController = rememberNavController()
      NavHost(
        navController = navController,
        startDestination = startDestination
      ) {
        homeScreen(
          navigateToNext = { navController.navigateTo(Destination.Name(false)) }
        )
        nameScreen(
          navigateToNext = {
            name = it
            navController.navigateTo(Destination.Color(false))
          },
          exit = { navController.navigateTo(Destination.Home) },
          finishOnEditingMode = {
            name = it
            navController.navigateTo(Destination.Summary)
          }
        )
        colorScreen(
          navigateToBack = {
            updateJumpCounter()
            navController.popBackStack()
          },
          navigateToNext = {
            color = it
            navController.navigateTo(Destination.JumpPower(false))
          },
          exit = { navController.navigateTo(Destination.Home) },
          finishOnEditingMode = {
            color = it
            navController.navigateTo(Destination.Summary)
          }
        )
        jumpPowerScreen(
          navigateToBackBack = {
            updateJumpCounter()
            navController.popBackStack(route = nameNavigationRoute, inclusive = false)
          },
          navigateToBack = {
            updateJumpCounter()
            navController.popBackStack()
          },
          navigateToNext = { navController.navigateTo(Destination.Summary) },
          exit = { navController.navigateTo(Destination.Home) },
          finishOnEditingMode = { navController.navigateTo(Destination.Summary) }
        )
        summaryScreen(
          navigateToName = {
            navController.navigateTo(Destination.Name(true))
          },
          navigateToColor = {
            navController.navigateTo(Destination.Color(true))
          },
          navigateToJumpPower = {
            navController.navigateTo(Destination.JumpPower(true))
          },
          confirmGoose = {
            confirmedGoose = it
            navController.navigateTo(Destination.Home)
          }
        )
      }
    }
  }

  private fun NavController.navigateTo(destination: Destination) {
    return when (destination) {
      Destination.Home -> navigateToHome(confirmedGoose).also { clearJumpCounter() }
      is Destination.Name -> navigateToName(destination.isEditingMode)
      is Destination.Color -> navigateToColor(destination.isEditingMode)
      is Destination.JumpPower -> navigateToJumpPower(
        jumpCounter = jumpCounter,
        isEditingMode = destination.isEditingMode
      )
      Destination.Summary -> navigateToSummary(
        Goose(
          name = name,
          color = color,
          jumpPower = jumpCounter
        )
      )
    }.also { updateJumpCounter() }
  }

  private fun clearJumpCounter() {
    jumpCounter = 0
  }

  private fun updateJumpCounter() {
    jumpCounter += 1
  }

  private sealed class Destination {
    object Home: Destination()
    data class Name(val isEditingMode: Boolean): Destination()
    data class Color(val isEditingMode: Boolean): Destination()
    data class JumpPower(val isEditingMode: Boolean): Destination()
    object Summary: Destination()
  }
}