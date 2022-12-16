package com.alecarnevale.goosenav.steps.color

import com.alecarnevale.goosenav.GooseColor

data class ViewState(
  val colors: List<GooseColor>,
  val selectedColor: GooseColor,
  val destination: Destination?
)

sealed class ViewEvent {
  data class OnSelectColor(val selectedColor: GooseColor) : ViewEvent()
  object OnClickBackButton : ViewEvent()
  object OnClickProceedButton : ViewEvent()
  object OnClickExitButton : ViewEvent()
  object ClearDestination : ViewEvent()
}

sealed class Destination {
  object Back : Destination()
  data class Next(val gooseColor: GooseColor) : Destination()
  object Exit : Destination()
}