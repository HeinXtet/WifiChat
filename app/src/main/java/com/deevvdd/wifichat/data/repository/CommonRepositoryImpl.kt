package com.deevvdd.wifichat.data.repository

import com.deevvdd.wifichat.data.datasource.LocalDataSource
import com.deevvdd.wifichat.domain.repository.CommonRepository
import javax.inject.Inject

/**
 * Created by heinhtet deevvdd@gmail.com on 06,August,2021
 */
class CommonRepositoryImpl @Inject constructor(private val localDataSource: LocalDataSource) :
    CommonRepository {
    override fun saveUserName(name: String) {
        localDataSource.saveUserName(name)
    }

    override fun getUserName(): String = localDataSource.getUserName()
    override fun getLastIPAddress() = localDataSource.getIpAddress()

    override fun saveLastIPAddress(ipAddress: String) {
        localDataSource.saveIpAddress(ipAddress)
    }
}
