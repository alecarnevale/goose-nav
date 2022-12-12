package com.alecarnevale.goosenav.steps.main

data class ViewState(
  val gooseReady: Boolean,
  val destination: Destination?
)

sealed class ViewEvent {
  object OnClickCreateButton : ViewEvent()
  object ClearDestination : ViewEvent()
}

enum class Destination {
  START
}