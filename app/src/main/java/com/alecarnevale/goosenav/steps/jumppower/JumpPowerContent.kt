package com.alecarnevale.goosenav.steps.jumppower

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alecarnevale.goosenav.steps.navbuttons.NavButtons

@Composable
fun JumpPowerContent(
  jumpViewModel: JumpPowerViewModel = viewModel()
) {
  val viewState by jumpViewModel.viewState().collectAsState()
  val onDoubleBack =
    remember(jumpViewModel) { { jumpViewModel.onEvent(ViewEvent.OnClickDoubleBackButton) } }
  val onBack = remember(jumpViewModel) { { jumpViewModel.onEvent(ViewEvent.OnClickBackButton) } }
  val onProceed =
    remember(jumpViewModel) { { jumpViewModel.onEvent(ViewEvent.OnClickProceedButton) } }
  val onExit =
    remember(jumpViewModel) { { jumpViewModel.onEvent(ViewEvent.OnClickExitButton) } }

  Body(
    jumpPower = viewState.jumpPower,
    onDoubleBack = onDoubleBack,
    onBack = onBack,
    onProceed = onProceed,
    onExit = onExit
  )

  val context = LocalContext.current
  LaunchedEffect(viewState.destination) {
    // TODO
    Toast.makeText(context, viewState.destination?.name ?: "CLEAR", Toast.LENGTH_LONG).show()
    if (viewState.destination != null) {
      jumpViewModel.onEvent(ViewEvent.ClearDestination)
    }
  }
}

@Composable
private fun Body(
  jumpPower: Int,
  onDoubleBack: () -> Unit,
  onBack: () -> Unit,
  onProceed: () -> Unit,
  onExit: () -> Unit
) {
  Column(
    modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    Text("La tua oca è in grado di saltare")
    Spacer(modifier = Modifier.height(30.dp))
    val annotatedString = buildAnnotatedString {
      withStyle(style = SpanStyle(fontSize = 28.sp)) {
        append("$jumpPower")
      }
      append(" caselle")
    }
    Text(annotatedString)
    Spacer(modifier = Modifier.height(30.dp))
    NavButtons(
      onDoubleBack = onDoubleBack,
      onBack = onBack,
      onProceed = onProceed,
      onExit = onExit
    )
  }
}

@Preview
@Composable
private fun BodyPreview() {
  Body(0, {}, {}, {}, {})
}