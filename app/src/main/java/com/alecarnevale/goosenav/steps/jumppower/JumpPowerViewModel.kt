package com.alecarnevale.goosenav.steps.jumppower

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class JumpPowerViewModel @Inject constructor(
  savedStateHandle: SavedStateHandle,
  @JumpPowerViewModelModule.JumpCounter private val jumpCounter: Int
) : ViewModel() {
  private val viewStateFlow =
    MutableStateFlow(savedStateHandle[VIEW_STATE_KEY] ?: defaultViewState())

  private fun defaultViewState() = ViewState(
    jumpPower = jumpCounter,
    destination = null
  )

  fun viewState() = viewStateFlow.asStateFlow()

  fun onEvent(event: ViewEvent) = when (event) {
    ViewEvent.OnClickDoubleBackButton -> onClickDoubleBackButton()
    ViewEvent.OnClickBackButton -> onClickBackButton()
    ViewEvent.OnClickProceedButton -> onClickProceedButton()
    ViewEvent.OnClickExitButton -> onClickExitButton()
    ViewEvent.ClearDestination -> clearDestination()
  }

  private fun onClickDoubleBackButton() {
    viewStateFlow.update { it.copy(destination = Destination.DOUBLE_BACK) }
  }

  private fun onClickBackButton() {
    viewStateFlow.update { it.copy(destination = Destination.BACK) }
  }

  private fun onClickProceedButton() {
    viewStateFlow.update { it.copy(destination = Destination.NEXT) }
  }

  private fun onClickExitButton() {
    viewStateFlow.update { it.copy(destination = Destination.EXIT) }
  }

  private fun clearDestination() {
    viewStateFlow.update { it.copy(destination = null) }
  }

  companion object {
    private const val VIEW_STATE_KEY = "view_state"
  }

}