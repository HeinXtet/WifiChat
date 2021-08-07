package com.deevvdd.wifichat.domain.repository

/**
 * Created by heinhtet deevvdd@gmail.com on 06,August,2021
 */
interface CommonRepository {

    fun saveUserName(name: String)
    fun getUserName(): String
    fun getLastIPAddress(): String
    fun saveLastIPAddress(ipAddress: String)
}
