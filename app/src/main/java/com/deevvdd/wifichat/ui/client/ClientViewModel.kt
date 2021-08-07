package com.deevvdd.wifichat.ui.client

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.deevvdd.wifichat.data.datasource.LocalDataSource
import com.deevvdd.wifichat.data.socket.ServerStatus
import com.deevvdd.wifichat.domain.model.Message
import com.deevvdd.wifichat.domain.repository.ClientRepository
import com.deevvdd.wifichat.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by heinhtet deevvdd@gmail.com on 07,August,2021
 */
@HiltViewModel
class ClientViewModel @Inject constructor(
    private val clientRepository: ClientRepository,
    private val localDataSource: LocalDataSource
) : ViewModel() {
    var messageText = MutableLiveData<String>()

    val isEnableToSend: LiveData<Boolean> = Transformations.map(messageText) {
        !it.isNullOrEmpty()
    }

    private val _serverStatus = clientRepository.getServerStatus()

    val serverStatus: LiveData<Event<ServerStatus>>
        get() = _serverStatus

    val messages: LiveData<ArrayList<Message>>
        get() = clientRepository.getAllMessage()

    fun sendMessage() {
        clientRepository.sendMessage(messageText.value.orEmpty())
        messageText.value = ""
    }

    fun getUserName() = localDataSource.getUserName()

    val onNewMessage = clientRepository.onNewMessage()

    override fun onCleared() {
        super.onCleared()
        clientRepository.endChatRoom()
    }

    fun stopServer() {
        clientRepository.endChatRoom()
    }

    fun joinServer() {
        clientRepository.joinServer()
    }

    fun getName(): String = localDataSource.getUserName()
}
