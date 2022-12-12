package com.alecarnevale.goosenav

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Goose(
  val name: String,
  val color: GooseColor,
  val jumpPower: Int
): Parcelable

enum class GooseColor {
  WHITE,
  YELLOW,
  ORANGE,
  GREEN,
  RED,
  PINK,
  VIOLET,
  BLUE,
  BROWN,
  BLACK
}