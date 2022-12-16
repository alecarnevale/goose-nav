package com.alecarnevale.goosenav.steps.name

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class NameViewModel @Inject constructor(
  savedStateHandle: SavedStateHandle
) : ViewModel() {
  private val viewStateFlow =
    MutableStateFlow(savedStateHandle[VIEW_STATE_KEY] ?: defaultViewState())

  private fun defaultViewState() = ViewState(
    name = null,
    nameError = null,
    destination = null
  )

  fun viewState() = viewStateFlow.asStateFlow()

  fun onEvent(event: ViewEvent) = when (event) {
    is ViewEvent.OnChangeName -> onChangeName(event.name)
    ViewEvent.ClearError -> clearError()
    ViewEvent.OnClickProceedButton -> onClickProceedButton()
    ViewEvent.OnClickExitButton -> onClickExitButton()
    ViewEvent.ClearDestination -> clearDestination()
  }

  private fun onChangeName(name: String) {
    viewStateFlow.update { it.copy(name = name, nameError = null) }
  }

  private fun clearError() {
    viewStateFlow.update { it.copy(nameError = null) }
  }

  private fun onClickProceedButton() {
    val name = viewStateFlow.value.name
    if (name.isNullOrBlank()) {
      viewStateFlow.update { it.copy(nameError = "Nome necessario") }
      return
    }
    if (name.startsWith("a")) {
      viewStateFlow.update { it.copy(nameError = "Nome non disponibile") }
      return
    }

    viewStateFlow.update { it.copy(destination = Destination.Next(name)) }
  }

  private fun onClickExitButton() {
    viewStateFlow.update { it.copy(destination = Destination.Exit) }
  }

  private fun clearDestination() {
    viewStateFlow.update { it.copy(destination = null) }
  }

  companion object {
    private const val VIEW_STATE_KEY = "view_state"
  }

}