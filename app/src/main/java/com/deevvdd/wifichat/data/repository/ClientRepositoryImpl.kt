package com.deevvdd.wifichat.data.repository

import androidx.lifecycle.LiveData
import com.deevvdd.wifichat.data.datasource.ClientDataSource
import com.deevvdd.wifichat.domain.model.Message
import com.deevvdd.wifichat.domain.repository.ClientRepository
import javax.inject.Inject

/**
 * Created by heinhtet deevvdd@gmail.com on 07,August,2021
 */
class ClientRepositoryImpl @Inject constructor(
    private val clientDataSource: ClientDataSource
) : ClientRepository {
    override fun joinServer() {
        clientDataSource.startServerToCommunicate()
    }

    override fun endChatRoom() {
        clientDataSource.closeServer()
    }

    override fun sendMessage(message: String) {
        clientDataSource.sendMessage(message)
    }

    override fun getServerStatus() = clientDataSource.getServerStatus()
    override fun getAllMessage() = clientDataSource.getMessages()

    override fun onNewMessage(): LiveData<Message> {
        return clientDataSource.onNewMessage()
    }
}
