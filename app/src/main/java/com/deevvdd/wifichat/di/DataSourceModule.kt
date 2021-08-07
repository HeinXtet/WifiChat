package com.deevvdd.wifichat.di

import android.content.Context
import com.deevvdd.wifichat.data.datasource.ClientDataSource
import com.deevvdd.wifichat.data.datasource.LocalDataSource
import com.deevvdd.wifichat.data.datasource.ServerDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by heinhtet deevvdd@gmail.com on 06,August,2021
 */
@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {
    @Provides
    @Singleton
    fun provideServerDataSource(): ServerDataSource {
        return ServerDataSource()
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(@ApplicationContext context: Context): LocalDataSource {
        return LocalDataSource(context)
    }

    @Provides
    @Singleton
    fun provideClientDataSource(localDataSource: LocalDataSource): ClientDataSource {
        return ClientDataSource(localDataSource)
    }
}
