package com.alecarnevale.goosenav.steps.summary

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alecarnevale.goosenav.GooseColor

@Composable
fun SummaryContent(
  summaryViewModel: SummaryViewModel = hiltViewModel(),
) {
  val viewState by summaryViewModel.viewState().collectAsState()
  val onClickName =
    remember(summaryViewModel) { { summaryViewModel.onEvent(ViewEvent.OnClickName) } }
  val onClickColor =
    remember(summaryViewModel) { { summaryViewModel.onEvent(ViewEvent.OnClickColor) } }
  val onClickJumpPower =
    remember(summaryViewModel) { { summaryViewModel.onEvent(ViewEvent.OnClickJumpPower) } }
  val onClickFinish =
    remember(summaryViewModel) { { summaryViewModel.onEvent(ViewEvent.OnClickFinishButton) } }

  Body(
    name = viewState.name,
    color = viewState.color,
    jumpPower = viewState.jumpPower,
    onClickName = onClickName,
    onClickColor = onClickColor,
    onClickJumpPower = onClickJumpPower,
    onClickFinish = onClickFinish,
  )

  val context = LocalContext.current
  LaunchedEffect(viewState.destination) {
    // TODO
    Toast.makeText(context, viewState.destination?.name ?: "CLEAR", Toast.LENGTH_LONG).show()
    if (viewState.destination != null) {
      summaryViewModel.onEvent(ViewEvent.ClearDestination)
    }
  }
}

@Composable
private fun Body(
  name: String,
  color: GooseColor,
  jumpPower: Int,
  onClickName: () -> Unit,
  onClickColor: () -> Unit,
  onClickJumpPower: () -> Unit,
  onClickFinish: () -> Unit
) {
  Column(
    modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {

    Stat(name = "Nome", value = name, onClick = onClickName)
    Spacer(modifier = Modifier.height(15.dp))
    Stat(name = "Colore", value = color.name, onClick = onClickColor)
    Spacer(modifier = Modifier.height(15.dp))
    Stat(name = "JumpPower", value = jumpPower.toString(), onClick = onClickJumpPower)

    Spacer(modifier = Modifier.height(30.dp))
    Button(onClick = onClickFinish) {
      Text(text = "OK")
    }
  }
}

@Composable
private fun Stat(
  name: String,
  value: String,
  onClick: () -> Unit
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 50.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(text = name)
    Button(onClick = onClick) {
      Text(text = value)
    }
  }
}

@Preview
@Composable
private fun BodyPreview() {
  Body("Lucrezia", GooseColor.PINK, 42, {}, {}, {}, {})
}