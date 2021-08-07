package com.deevvdd.wifichat.data.datasource

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Created by heinhtet deevvdd@gmail.com on 06,August,2021
 */
class LocalDataSource @Inject constructor(@ApplicationContext val context: Context) {
    private var sharedPref: SharedPreferences =
        context.getSharedPreferences("wifi_chat", Context.MODE_PRIVATE)

    fun saveUserName(name: String) {
        sharedPref.edit().putString(USER_NAME_KEY, name).apply()
    }

    fun getUserName() = sharedPref.getString(USER_NAME_KEY, "WIFI Chat User").orEmpty()

    fun saveIpAddress(ip: String) {
        sharedPref.edit().putString(SERVER_IP_ADDRESS, ip).apply()
    }

    fun getIpAddress() = sharedPref.getString(SERVER_IP_ADDRESS, "").orEmpty()

    companion object {
        const val USER_NAME_KEY = "WIFI_CHAT_USER_NAME"
        const val SERVER_IP_ADDRESS = "WIFI_CHAT_SERVER_IP_ADDRESS"
    }
}
