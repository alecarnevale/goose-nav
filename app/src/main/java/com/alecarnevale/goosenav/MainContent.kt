package com.alecarnevale.goosenav

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alecarnevale.goosenav.ui.theme.GooseNavTheme

@Composable
fun MainContent(
  mainViewModel: MainViewModel = viewModel()
) {
  Body {
    mainViewModel.start()
  }
}

@Composable
fun Body(
  onStartButtonClick: () -> Unit
) {
  GooseNavTheme {
    Box(
      modifier = Modifier.fillMaxSize(),
      contentAlignment = Alignment.Center
    ) {
      Button(onClick = onStartButtonClick) {
        Text(text = "Builda la tua oca!")
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun BodyPreview() {
  Body({})
}