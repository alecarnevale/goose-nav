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

enum class Destination {
  BACK, NEXT, EXIT
}