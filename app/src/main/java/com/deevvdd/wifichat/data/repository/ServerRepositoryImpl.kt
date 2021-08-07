package com.deevvdd.wifichat.data.repository

import androidx.lifecycle.LiveData
import com.deevvdd.wifichat.data.datasource.LocalDataSource
import com.deevvdd.wifichat.data.datasource.ServerDataSource
import com.deevvdd.wifichat.domain.model.Message
import com.deevvdd.wifichat.domain.repository.ServerRepository
import javax.inject.Inject

/**
 * Created by heinhtet deevvdd@gmail.com on 06,August,2021
 */
class ServerRepositoryImpl @Inject constructor(
    private val serverDataSource: ServerDataSource,
    private val localDataSource: LocalDataSource
) :
    ServerRepository {
    override fun startServer() {
        serverDataSource.startServerToCommunicate(localDataSource.getUserName())
    }

    override fun stopServer() {
        serverDataSource.stopServer()
    }

    override fun sendMessage(message: String) {
        serverDataSource.sendMessage(message, localDataSource.getUserName())
    }

    override fun getAllMessage(): LiveData<ArrayList<Message>> {
        return serverDataSource.getMessages()
    }

    override fun onNewMessage(): LiveData<Message> {
        return serverDataSource.onNewMessage()
    }
}
