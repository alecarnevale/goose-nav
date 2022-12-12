package com.alecarnevale.goosenav.steps.home

import com.alecarnevale.goosenav.steps.MainActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Qualifier

@Module
@InstallIn(ViewModelComponent::class)
object HomeViewModelModule {
  @Qualifier
  @Retention(AnnotationRetention.RUNTIME)
  annotation class GooseReady

  @Provides
  @GooseReady
  // TODO temporary
  fun gooseReady(): Boolean = MainActivity.goose != null
}