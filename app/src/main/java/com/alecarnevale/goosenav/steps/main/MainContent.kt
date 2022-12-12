package com.alecarnevale.goosenav.steps.main

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
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MainContent(
  mainViewModel: MainViewModel = viewModel()
) {
  val viewState by mainViewModel.viewState().collectAsState()
  val onClickCreateButton =
    remember(mainViewModel) { { mainViewModel.onEvent(ViewEvent.OnClickCreateButton) } }

  Body(
    gooseReady = viewState.gooseReady,
    onClickCreateButton = onClickCreateButton
  )

  val context = LocalContext.current
  LaunchedEffect(viewState.destination) {
    // TODO
    Toast.makeText(context, viewState.destination?.name ?: "CLEAR", Toast.LENGTH_LONG).show()
    if (viewState.destination != null) {
      mainViewModel.onEvent(ViewEvent.ClearDestination)
    }
  }
}

@Composable
private fun Body(
  gooseReady: Boolean,
  onClickCreateButton: () -> Unit
) {
  Column(
    modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.SpaceEvenly
  ) {
    val message = if (gooseReady) {
      "La tua oca è pronta"
    } else {
      "Costruisci la tua oca"
    }
    Text(text = message)
    Spacer(modifier = Modifier.height(30.dp))
    Button(onClick = onClickCreateButton) {
      Text(text = "Costruisci la tua oca!")
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun BodyPreview() {
  Body(false, {})
}