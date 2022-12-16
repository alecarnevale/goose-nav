package com.alecarnevale.goosenav.steps.home

import androidx.lifecycle.SavedStateHandle
import com.alecarnevale.goosenav.Goose
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
  annotation class HomeGooseData

  @Provides
  @HomeGooseData
  fun gooseReady(savedStateHandle: SavedStateHandle): Goose? =
    savedStateHandle.get<Goose>(EXTRA_HOME_GOOSE_DATA)
}