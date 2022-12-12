package com.alecarnevale.goosenav.steps.jumppower

data class ViewState(
  val jumpPower: Int,
  val destination: Destination?
)

sealed class ViewEvent {
  object OnClickDoubleBackButton : ViewEvent()
  object OnClickBackButton : ViewEvent()
  object OnClickProceedButton : ViewEvent()
  object OnClickExitButton : ViewEvent()
  object ClearDestination : ViewEvent()
}

enum class Destination {
  DOUBLE_BACK, BACK, NEXT, EXIT
}