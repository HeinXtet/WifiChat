package com.deevvdd.wifichat.domain.repository

import androidx.lifecycle.LiveData
import com.deevvdd.wifichat.data.socket.ServerStatus
import com.deevvdd.wifichat.domain.model.Message
import com.deevvdd.wifichat.utils.Event

/**
 * Created by heinhtet deevvdd@gmail.com on 07,August,2021
 */
interface ClientRepository {
    fun joinServer()
    fun endChatRoom()
    fun sendMessage(message: String)
    fun getAllMessage(): LiveData<ArrayList<Message>>
    fun getServerStatus(): LiveData<Event<ServerStatus>>
    fun onNewMessage(): LiveData<Message>
}
