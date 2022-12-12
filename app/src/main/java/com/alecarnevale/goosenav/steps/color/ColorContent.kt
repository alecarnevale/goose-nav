package com.alecarnevale.goosenav.steps.color

import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alecarnevale.goosenav.GooseColor
import com.alecarnevale.goosenav.steps.navbuttons.NavButtons

@Composable
fun ColorContent(
  colorViewModel: ColorViewModel = viewModel(),
  navigateToBack: () -> Unit,
  navigateToNext: () -> Unit,
  exit: () -> Unit
) {
  val viewState by colorViewModel.viewState().collectAsState()
  val onSelectColor =
    remember(colorViewModel) {
      { selectedColor: GooseColor ->
        colorViewModel.onEvent(
          ViewEvent.OnSelectColor(
            selectedColor
          )
        )
      }
    }
  val onBack = remember(colorViewModel) { { colorViewModel.onEvent(ViewEvent.OnClickBackButton) } }
  val onProceed =
    remember(colorViewModel) { { colorViewModel.onEvent(ViewEvent.OnClickProceedButton) } }
  val onExit =
    remember(colorViewModel) { { colorViewModel.onEvent(ViewEvent.OnClickExitButton) } }

  Body(
    colors = viewState.colors,
    selectedColor = viewState.selectedColor,
    onSelectColor = onSelectColor,
    onBack = onBack,
    onProceed = onProceed,
    onExit = onExit
  )

  LaunchedEffect(viewState.destination) {
    when (viewState.destination) {
      Destination.BACK -> navigateToBack()
      Destination.NEXT -> navigateToNext()
      Destination.EXIT -> exit()
      null -> Unit
    }
    if (viewState.destination != null) {
      colorViewModel.onEvent(ViewEvent.ClearDestination)
    }
  }
}

@Composable
private fun Body(
  colors: List<GooseColor>,
  selectedColor: GooseColor,
  onSelectColor: (GooseColor) -> Unit,
  onBack: () -> Unit,
  onProceed: () -> Unit,
  onExit: () -> Unit
) {
  Column(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text("Di che colore è l'oca?")
    Spacer(modifier = Modifier.height(30.dp))
    SelectionMenu(colors = colors, selectedColor = selectedColor, onSelectColor = onSelectColor)
    Spacer(modifier = Modifier.height(30.dp))
    NavButtons(
      onDoubleBack = null,
      onBack = onBack,
      onProceed = onProceed,
      onExit = onExit
    )
  }
}

@Composable
private fun SelectionMenu(
  colors: List<GooseColor>,
  selectedColor: GooseColor,
  onSelectColor: (GooseColor) -> Unit
) {
  var expanded by remember { mutableStateOf(false) }
  Box(
    modifier = Modifier
      .wrapContentSize(align = Alignment.Center),
    contentAlignment = Alignment.Center
  ) {
    ClickableText(
      text = AnnotatedString(selectedColor.name),
      style = TextStyle(fontSize = 28.sp),
      onClick = { expanded = true }
    )
    DropdownMenu(
      expanded = expanded,
      onDismissRequest = { expanded = false }
    ) {
      colors.forEach { color ->
        DropdownMenuItem(
          onClick = {
            onSelectColor(color)
            expanded = false
          }
        ) {
          Text(text = color.name)
        }
      }
    }
  }
}

@Preview
@Composable
private fun BodyPreview() {
  Body(emptyList(), GooseColor.BLACK, {}, {}, {}, {})
}