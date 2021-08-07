package com.deevvdd.wifichat.di

import com.deevvdd.wifichat.data.repository.ClientRepositoryImpl
import com.deevvdd.wifichat.data.repository.CommonRepositoryImpl
import com.deevvdd.wifichat.data.repository.ServerRepositoryImpl
import com.deevvdd.wifichat.domain.repository.ClientRepository
import com.deevvdd.wifichat.domain.repository.CommonRepository
import com.deevvdd.wifichat.domain.repository.ServerRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by heinhtet deevvdd@gmail.com on 06,August,2021
 */

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindServerRepository(
        serverRepositoryImpl: ServerRepositoryImpl
    ): ServerRepository

    @Binds
    abstract fun bindClientRepository(
        clientRepositoryImpl: ClientRepositoryImpl
    ): ClientRepository

    @Binds
    abstract fun bindCommonRepository(
        commonRepositoryImpl: CommonRepositoryImpl
    ): CommonRepository
}
