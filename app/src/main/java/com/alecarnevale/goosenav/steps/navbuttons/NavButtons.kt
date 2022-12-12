package com.alecarnevale.goosenav.steps.navbuttons

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun NavButtons(
  onDoubleBack: (() -> Unit)?,
  onBack: (() -> Unit)?,
  onProceed: () -> Unit,
  onExit: () -> Unit
) {
  Column(modifier = Modifier.fillMaxWidth()) {
    val doubleBackNavButton = if (onDoubleBack != null) EnabledNavButton(
      text = "Indietro x2",
      onClick = onDoubleBack,
    ) else DisabledNavButton("Indietro x2")

    val backNavButton = if (onBack != null) EnabledNavButton(
      text = "Indietro",
      onClick = onBack,
    ) else DisabledNavButton("Indietro")

    val proceedNavButton = EnabledNavButton(
      text = "Avanti",
      onClick = onProceed,
    )

    val exitNavButton = EnabledNavButton(
      text = "Exit",
      onClick = onExit,
    )

    ButtonsInARow(
      leftNavButton = backNavButton,
      rightNavButton = proceedNavButton
    )
    ButtonsInARow(
      leftNavButton = doubleBackNavButton,
      rightNavButton = exitNavButton
    )
  }
}

@Composable
private fun ButtonsInARow(
  leftNavButton: NavButton,
  rightNavButton: NavButton
) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceEvenly
  ) {
    Button(
      onClick = (leftNavButton as? EnabledNavButton)?.onClick ?: {},
      modifier = Modifier.width(150.dp),
      enabled = leftNavButton is EnabledNavButton
    ) {
      Text(text = leftNavButton.text)
    }
    Button(
      onClick = (rightNavButton as? EnabledNavButton)?.onClick ?: {},
      modifier = Modifier.width(150.dp),
      enabled = rightNavButton is EnabledNavButton
    ) {
      Text(text = rightNavButton.text)
    }
  }
}

private sealed class NavButton() {
  abstract val text: String
}

private data class EnabledNavButton(
  override val text: String,
  val onClick: () -> Unit
) : NavButton()

private data class DisabledNavButton(
  override val text: String
) : NavButton()

@Preview
@Composable
private fun NavButtonsPreview() {
  NavButtons(
    onDoubleBack = {},
    onBack = {},
    onProceed = {},
    onExit = {}
  )
}

@Preview
@Composable
private fun NavButtonsPreviewDisabled() {
  NavButtons(
    onDoubleBack = null,
    onBack = null,
    onProceed = {},
    onExit = {}
  )
}