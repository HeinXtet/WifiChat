package com.deevvdd.wifichat.utils

import android.content.Context
import android.net.wifi.WifiManager
import androidx.core.content.ContextCompat.getSystemService

/**
 * Created by heinhtet deevvdd@gmail.com on 07,August,2021
 */
object NetworkUtils {
    fun checkWifiAvailable(context: Context): Boolean {
        val wifiMgr =
            context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        return if (wifiMgr.isWifiEnabled) {
            val wifiInfo = wifiMgr.connectionInfo
            wifiInfo.networkId != -1
        } else {
            false
        }
    }
}
