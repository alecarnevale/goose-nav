package com.alecarnevale.goosenav.steps.jumppower

import androidx.lifecycle.SavedStateHandle
import com.alecarnevale.goosenav.steps.MainActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Qualifier

@Module
@InstallIn(ViewModelComponent::class)
object JumpPowerViewModelModule {
  @Qualifier
  @Retention(AnnotationRetention.RUNTIME)
  annotation class JumpCounter

  @Provides
  @JumpCounter
  fun jumpCounter(savedStateHandle: SavedStateHandle): Int = requireNotNull(
    savedStateHandle.get<Int>(EXTRA_JUMP_COUNTER)
  )
}