package com.alecarnevale.goosenav.steps.summary

import com.alecarnevale.goosenav.GooseColor

data class ViewState(
  val name: String,
  val color: GooseColor,
  val jumpPower: Int,
  val destination: Destination?
)

sealed class ViewEvent {
  object OnClickName: ViewEvent()
  object OnClickColor: ViewEvent()
  object OnClickJumpPower: ViewEvent()
  object OnClickFinishButton : ViewEvent()
  object ClearDestination : ViewEvent()
}

enum class Destination {
  NAME, COLOR, JUMP_POWER, EXIT
}