package com.alecarnevale.goosenav.steps.summary

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.alecarnevale.goosenav.Goose
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SummaryViewModel @Inject constructor(
  savedStateHandle: SavedStateHandle,
  @SummaryViewModelModule.SummaryGooseData private val goose: Goose
) : ViewModel() {
  private val viewStateFlow =
    MutableStateFlow(savedStateHandle[VIEW_STATE_KEY] ?: defaultViewState())

  private fun defaultViewState() = ViewState(
    name = goose.name,
    color = goose.color,
    jumpPower = goose.jumpPower,
    destination = null
  )

  fun viewState() = viewStateFlow.asStateFlow()

  fun onEvent(event: ViewEvent) = when (event) {
    ViewEvent.OnClickName -> onClickName()
    ViewEvent.OnClickColor -> onClickColor()
    ViewEvent.OnClickJumpPower -> onClickJumpPower()
    ViewEvent.OnClickFinishButton -> onClickFinishButton()
    ViewEvent.ClearDestination -> clearDestination()
  }

  private fun onClickName() {
    viewStateFlow.update { it.copy(destination = Destination.NAME) }
  }

  private fun onClickColor() {
    viewStateFlow.update { it.copy(destination = Destination.COLOR) }
  }

  private fun onClickJumpPower() {
    viewStateFlow.update { it.copy(destination = Destination.JUMP_POWER) }
  }

  private fun onClickFinishButton() {
    viewStateFlow.update { it.copy(destination = Destination.EXIT) }
  }

  private fun clearDestination() {
    viewStateFlow.update { it.copy(destination = null) }
  }

  companion object {
    private const val VIEW_STATE_KEY = "view_state"
  }

}