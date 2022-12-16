package com.alecarnevale.goosenav.steps.home

data class ViewState(
  val destination: Destination?
)

sealed class ViewEvent {
  object OnClickCreateButton : ViewEvent()
  object ClearDestination : ViewEvent()
}

enum class Destination {
  START
}