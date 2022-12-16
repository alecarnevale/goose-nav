package com.alecarnevale.goosenav.steps.name

data class ViewState(
  val name: String?,
  val nameError: String?,
  val destination: Destination?
)

sealed class ViewEvent {
  data class OnChangeName(val name: String) : ViewEvent()
  object ClearError : ViewEvent()
  object OnClickProceedButton : ViewEvent()
  object OnClickExitButton : ViewEvent()
  object ClearDestination : ViewEvent()
}

sealed class Destination {
  data class Next(val name: String) : Destination()
  object Exit : Destination()
}