package com.alecarnevale.goosenav.steps.color

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.alecarnevale.goosenav.GooseColor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ColorViewModel @Inject constructor(
  savedStateHandle: SavedStateHandle
) : ViewModel() {
  private val availableColors: List<GooseColor> = randomizedColors()
  private val viewStateFlow =
    MutableStateFlow(savedStateHandle[VIEW_STATE_KEY] ?: defaultViewState())

  private fun defaultViewState() = ViewState(
    colors = availableColors,
    selectedColor = availableColors.first(),
    destination = null
  )

  fun viewState() = viewStateFlow.asStateFlow()

  fun onEvent(event: ViewEvent) = when (event) {
    is ViewEvent.OnSelectColor -> onSelectColor(event.selectedColor)
    ViewEvent.OnClickBackButton -> onClickBackButton()
    ViewEvent.OnClickProceedButton -> onClickProceedButton()
    ViewEvent.OnClickExitButton -> onClickExitButton()
    ViewEvent.ClearDestination -> clearDestination()
  }

  private fun onSelectColor(selectedColor: GooseColor) {
    viewStateFlow.update { it.copy(selectedColor = selectedColor) }
  }

  private fun onClickBackButton() {
    viewStateFlow.update { it.copy(destination = Destination.Back) }
  }

  private fun onClickProceedButton() {
    viewStateFlow.update { it.copy(destination = Destination.Next(it.selectedColor)) }
  }

  private fun onClickExitButton() {
    viewStateFlow.update { it.copy(destination = Destination.Exit) }
  }

  private fun clearDestination() {
    viewStateFlow.update { it.copy(destination = null) }
  }

  private fun randomizedColors(): List<GooseColor> {
    return GooseColor.values().apply {
      shuffle()
    }.sliceArray(0..2).toList()
  }

  companion object {
    private const val VIEW_STATE_KEY = "view_state"
  }

}