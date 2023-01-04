package com.alecarnevale.goosenav.steps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.alecarnevale.goosenav.Goose
import com.alecarnevale.goosenav.GooseColor
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
      MainNav(
        updateName = { name = it },
        updateColor = { color = it },
        updateJumpCounter = { jumpCounter += 1; jumpCounter },
        clearJumpCounter = { jumpCounter = 0 },
        confirmedGoose = { confirmedGoose },
        confirmGoose = { confirmedGoose = it },
        buildingGoose = { Goose(name = name, color = color, jumpPower = jumpCounter) }
      )
    }
  }
}