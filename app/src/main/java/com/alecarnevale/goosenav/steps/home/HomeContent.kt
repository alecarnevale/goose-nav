package com.alecarnevale.goosenav.steps.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeContent(
  homeViewModel: HomeViewModel = hiltViewModel(),
  navigateToNext: () -> Unit
) {
  val viewState by homeViewModel.viewState().collectAsState()
  val onClickCreateButton =
    remember(homeViewModel) { { homeViewModel.onEvent(ViewEvent.OnClickCreateButton) } }

  Body(
    onClickCreateButton = onClickCreateButton
  )

  LaunchedEffect(viewState.destination) {
    when (viewState.destination) {
      Destination.START -> navigateToNext()
      null -> Unit
    }
    if (viewState.destination != null) {
      homeViewModel.onEvent(ViewEvent.ClearDestination)
    }
  }
}

@Composable
private fun Body(
  onClickCreateButton: () -> Unit
) {
  Column(
    modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.SpaceEvenly
  ) {
    Text(text = "La tua oca non è pronta")
    Spacer(modifier = Modifier.height(30.dp))
    Button(onClick = onClickCreateButton) {
      Text(text = "Costruisci la tua oca!")
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun BodyPreview() {
  Body({})
}