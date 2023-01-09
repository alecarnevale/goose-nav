package com.alecarnevale.goosenav.steps.jumppower

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
internal class JumpPowerContentTest {
  @get:Rule
  val composeTestRule = createComposeRule()

  @get:Rule
  var hiltRule = HiltAndroidRule(this)

  private val mockVM = mockk<JumpPowerViewModel>()

  // the same for every destination
  @Test
  fun test_navigateNext() {
    // val vs = MutableStateFlow(ViewState(0, null))
    val vs = MutableStateFlow(ViewState(0, Destination.NEXT))
    every { mockVM.viewState() } returns vs
    every { mockVM.onEvent(any()) } returns Unit

    var navigateToNextCallbackTriggered = false
    // composeTestRule.mainClock.autoAdvance = false
    composeTestRule.setContent {
      content(
        navigateToNext = { navigateToNextCallbackTriggered = true }
      )
    }

    // assertFalse(navigateToNextCallbackTriggered)
    // val x = vs.tryEmit(ViewState(0, Destination.NEXT))
    // composeTestRule.mainClock.advanceTimeByFrame()
    assertTrue(navigateToNextCallbackTriggered)
  }

  @Composable
  private fun content(
    navigateToBackBack: () -> Unit = {},
    navigateToBack: () -> Unit = {},
    navigateToNext: () -> Unit = {},
    exit: () -> Unit = {},
    finishOnEditingMode: (() -> Unit)? = null
  ) =
    JumpPowerContent(
      jumpViewModel = mockVM,
      navigateToBackBack = navigateToBackBack,
      navigateToBack = navigateToBack,
      navigateToNext = navigateToNext,
      exit = exit,
      finishOnEditingMode = finishOnEditingMode
    )
}