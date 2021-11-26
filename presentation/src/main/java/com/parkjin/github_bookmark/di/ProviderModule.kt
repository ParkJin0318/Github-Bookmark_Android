package com.parkjin.github_bookmark.di

import com.parkjin.github_bookmark.provider.SchedulerProvider
import com.parkjin.github_bookmark.provider.SchedulerProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ProviderModule {

    @Binds
    abstract fun bindSchedulerProvider(providerImpl: SchedulerProviderImpl): SchedulerProvider
}
