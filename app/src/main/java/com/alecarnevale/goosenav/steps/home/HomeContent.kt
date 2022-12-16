package com.alecarnevale.goosenav.steps.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alecarnevale.goosenav.Goose
import com.alecarnevale.goosenav.GooseColor

@Composable
fun HomeContent(
  homeViewModel: HomeViewModel = hiltViewModel(),
  navigateToNext: () -> Unit
) {
  val viewState by homeViewModel.viewState().collectAsState()
  val onClickCreateButton =
    remember(homeViewModel) { { homeViewModel.onEvent(ViewEvent.OnClickCreateButton) } }

  Body(
    goose = viewState.goose,
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
  goose: Goose?,
  onClickCreateButton: () -> Unit
) {
  Column(
    modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.SpaceEvenly
  ) {
    if (goose == null) {
      Text(text = "La tua oca non è pronta")
    } else {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Text(text = "La tua oca è pronta per l'avventura")
        Spacer(modifier = Modifier.height(32.dp))
        Text(text = goose.name, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(32.dp))
        Text(text = goose.color.name, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(32.dp))
        Text(text = goose.jumpPower.toString(), fontSize = 24.sp, fontWeight = FontWeight.Bold)
      }
    }
    Spacer(modifier = Modifier.height(30.dp))
    Button(onClick = onClickCreateButton) {
      Text(text = "Costruisci la tua oca!")
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun BodyNoGoosePreview() {
  Body(null, {})
}

@Preview(showBackground = true)
@Composable
private fun BodyGoosePreview() {
  Body(Goose("Lucrezia", GooseColor.PINK, 42), {})
}