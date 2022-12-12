package com.alecarnevale.goosenav.steps.jumppower

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
  // TODO temporary
  fun jumpCounter(): Int = MainActivity.jumpCounter
}