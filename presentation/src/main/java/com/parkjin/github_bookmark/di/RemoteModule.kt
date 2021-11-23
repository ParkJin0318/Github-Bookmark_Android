package com.parkjin.github_bookmark.di

import com.parkjin.github_bookmark.network.api.UserAPI
import com.parkjin.github_bookmark.network.remote.UserRemote
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    fun provideUserRemote(api: UserAPI): UserRemote = UserRemote(api)

}
