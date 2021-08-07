package com.deevvdd.wifichat.data.socket

/**
 * Created by heinhtet deevvdd@gmail.com on 07,August,2021
 */
sealed class ServerStatus {
    object SERVER_CLOSED : ServerStatus()
    object UNABLE_TO_CONNECT_SERVER : ServerStatus()
    object DEFAULT : ServerStatus()
}
