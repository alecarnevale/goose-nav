package com.alecarnevale.goosenav

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.alecarnevale.goosenav.steps.color.ColorContent
import com.alecarnevale.goosenav.steps.jumppower.JumpPowerContent
import com.alecarnevale.goosenav.steps.name.NameContent
import com.alecarnevale.goosenav.steps.summary.SummaryContent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      SummaryContent()
    }
  }

  companion object {
    var jumpCounter: Int = 0
    var goose: Goose? = null
  }
}