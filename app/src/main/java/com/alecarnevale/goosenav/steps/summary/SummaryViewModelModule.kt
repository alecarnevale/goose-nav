package com.alecarnevale.goosenav.steps.summary

import com.alecarnevale.goosenav.Goose
import com.alecarnevale.goosenav.steps.MainActivity
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
  annotation class GooseData

  @Provides
  @GooseData
  // TODO temporary
  fun goose(): Goose = requireNotNull(MainActivity.goose)
}