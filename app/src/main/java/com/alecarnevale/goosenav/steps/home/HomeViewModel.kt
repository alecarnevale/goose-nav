package com.alecarnevale.goosenav.steps.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
  savedStateHandle: SavedStateHandle,
  @HomeViewModelModule.GooseReady private val gooseReady: Boolean
) : ViewModel() {
  private val viewStateFlow =
    MutableStateFlow(savedStateHandle[VIEW_STATE_KEY] ?: defaultViewState())

  private fun defaultViewState() = ViewState(
    gooseReady = gooseReady,
    destination = null
  )

  fun viewState() = viewStateFlow.asStateFlow()

  fun onEvent(event: ViewEvent) = when (event) {
    ViewEvent.OnClickCreateButton -> onClickCreateButton()
    ViewEvent.ClearDestination -> clearDestination()
  }

  private fun onClickCreateButton() {
    viewStateFlow.update { it.copy(destination = Destination.START) }
  }

  private fun clearDestination() {
    viewStateFlow.update { it.copy(destination = null) }
  }

  companion object {
    private const val VIEW_STATE_KEY = "view_state"
  }

}