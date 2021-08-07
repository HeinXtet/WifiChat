package com.deevvdd.wifichat.data.datasource

import androidx.lifecycle.MutableLiveData
import com.deevvdd.wifichat.data.socket.Server
import com.deevvdd.wifichat.domain.model.Message
import com.deevvdd.wifichat.utils.notifyObserver
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Created by heinhtet deevvdd@gmail.com on 06,August,2021
 */
class ServerDataSource @Inject constructor() {
    private val server = Server()
    private val messages =
        MutableLiveData<ArrayList<Message>>(mutableListOf<Message>() as ArrayList<Message>?)

    private val newMessageData = MutableLiveData<Message>()

    fun startServerToCommunicate(serverOwnerName: String) {
        Thread(
            server.StartCommunication(
                { name, message -> updateMessage(message, name) },
                serverStarted = {
                    Timber.d("Server Started $it")
                    updateMessage(it, serverOwnerName)
                })
        ).start()
    }

    fun sendMessage(msg: String, serverOwnerName: String) {
        server.sendNewMessage(msg, serverOwnerName) { name, message ->
            updateMessage(message, name)
        }
    }

    fun stopServer() {
        server.stopServer()
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

    fun getMessages() = messages

    fun onNewMessage() = newMessageData
}
