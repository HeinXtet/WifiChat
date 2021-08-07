package com.deevvdd.wifichat.data.datasource

import android.os.Looper
import androidx.lifecycle.MutableLiveData
import com.deevvdd.wifichat.data.socket.Client
import com.deevvdd.wifichat.data.socket.ServerStatus
import com.deevvdd.wifichat.domain.model.Message
import com.deevvdd.wifichat.utils.Event
import com.deevvdd.wifichat.utils.notifyObserver
import java.util.logging.Handler
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Created by heinhtet deevvdd@gmail.com on 07,August,2021
 */
class ClientDataSource @Inject constructor(
    localDataSource: LocalDataSource
) {
    private val messages =
        MutableLiveData<ArrayList<Message>>(mutableListOf<Message>() as ArrayList<Message>?)
    private val newMessageData = MutableLiveData<Message>()

    private val serverStatus = MutableLiveData<Event<ServerStatus>>()

    val client = Client(localDataSource.getUserName(), localDataSource.getIpAddress())
    fun startServerToCommunicate() {
        Thread(
            client.StartCommunication(
                onNewMessage = { name, message ->
                    updateMessage(message, name)
                },
                onServerClosed = {
                    updateServerStatus(ServerStatus.SERVER_CLOSED)
                },
                unableConnectToServer = { updateServerStatus(ServerStatus.UNABLE_TO_CONNECT_SERVER) })
        ).start()
    }

    private fun updateMessage(message: String, name: String) {
        GlobalScope.launch(Dispatchers.Main) {
            val newMessage = Message(
                senderName = name, message = message
            )
            messages.value?.add(
                newMessage
            )
            messages.notifyObserver()
            newMessageData.value = newMessage
            newMessageData.notifyObserver()
            Timber.d("Server: on New Message name : $name  message:$name")
        }
    }

    fun sendMessage(msg: String) {
        client.sendNewMessage(msg)
    }

    private fun updateServerStatus(status: ServerStatus) {
        GlobalScope.launch(Dispatchers.Main) {
            serverStatus.value = Event(status)
            serverStatus.notifyObserver()
        }
    }

    fun closeServer() {
        updateServerStatus(ServerStatus.DEFAULT)
        android.os.Handler(Looper.getMainLooper()).postDelayed({
            client.closeServer()
        }, 1000)
    }

    fun getMessages() = messages

    fun getServerStatus() = serverStatus

    fun onNewMessage() = newMessageData
}
