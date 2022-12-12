package com.alecarnevale.goosenav.steps.name

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alecarnevale.goosenav.steps.navbuttons.NavButtons

@Composable
fun NameContent(
  nameViewModel: NameViewModel = viewModel()
) {
  val viewState by nameViewModel.viewState().collectAsState()
  val onChangeName =
    remember(nameViewModel) {
      { name: String ->
        nameViewModel.onEvent(
          ViewEvent.OnChangeName(
            name
          )
        )
      }
    }
  val onProceed =
    remember(nameViewModel) { { nameViewModel.onEvent(ViewEvent.OnClickProceedButton) } }
  val onExit = remember(nameViewModel) { { nameViewModel.onEvent(ViewEvent.OnClickExitButton) } }

  Body(
    name = viewState.name,
    nameError = viewState.nameError,
    onChangeName = onChangeName,
    onProceed = onProceed,
    onExit = onExit,
  )

  val context = LocalContext.current
  LaunchedEffect(viewState.destination) {
    // TODO
    Toast.makeText(context, viewState.destination?.name ?: "CLEAR", Toast.LENGTH_LONG).show()
    if (viewState.destination != null) {
      nameViewModel.onEvent(ViewEvent.ClearDestination)
    }
  }
}

@Composable
private fun Body(
  name: String?,
  nameError: String?,
  onChangeName: (String) -> Unit,
  onProceed: () -> Unit,
  onExit: () -> Unit
) {
  Column(
    modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    Text("Come si chiama l'oca?")
    Spacer(modifier = Modifier.height(30.dp))
    TextField(value = name.orEmpty(), onValueChange = onChangeName)
    if (nameError != null) {
      Spacer(modifier = Modifier.height(10.dp))
      Text(nameError, color = Color.Red)
    }
    Spacer(modifier = Modifier.height(30.dp))
    NavButtons(
      onDoubleBack = null,
      onBack = null,
      onProceed = onProceed,
      onExit = onExit
    )
  }
}

@Preview
@Composable
private fun BodyPreview() {
  Body(null, null, {}, {}, {})
}