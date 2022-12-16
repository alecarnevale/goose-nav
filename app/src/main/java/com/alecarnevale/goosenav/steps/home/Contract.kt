package com.alecarnevale.goosenav.steps.home

import com.alecarnevale.goosenav.Goose

data class ViewState(
  val goose: Goose?,
  val destination: Destination?
)

sealed class ViewEvent {
  object OnClickCreateButton : ViewEvent()
  object ClearDestination : ViewEvent()
}

enum class Destination {
  START
}