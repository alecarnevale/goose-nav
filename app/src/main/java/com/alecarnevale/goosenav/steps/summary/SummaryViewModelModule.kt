package com.alecarnevale.goosenav.steps.summary

import androidx.lifecycle.SavedStateHandle
import com.alecarnevale.goosenav.Goose
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Qualifier

@Module
@InstallIn(ViewModelComponent::class)
object SummaryViewModelModule {
  @Qualifier
  @Retention(AnnotationRetention.RUNTIME)
  annotation class SummaryGooseData

  @Provides
  @SummaryGooseData
  fun goose(savedStateHandle: SavedStateHandle): Goose = requireNotNull(
    savedStateHandle.get<Goose>(EXTRA_SUMMARY_GOOSE_DATA)
  )
}