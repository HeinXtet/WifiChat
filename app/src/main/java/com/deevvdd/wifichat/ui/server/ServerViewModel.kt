package com.deevvdd.wifichat.ui.server

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.deevvdd.wifichat.data.datasource.LocalDataSource
import com.deevvdd.wifichat.domain.model.Message
import com.deevvdd.wifichat.domain.repository.ServerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by heinhtet deevvdd@gmail.com on 06,August,2021
 */
@HiltViewModel
class ServerViewModel @Inject constructor(
    private val serverRepository: ServerRepository,
    private val localDataSource: LocalDataSource
) : ViewModel() {

    var messageText = MutableLiveData<String>()

    val isEnableToSend: LiveData<Boolean> = Transformations.map(messageText) {
        !it.isNullOrEmpty()
    }

    fun startServer() {
        serverRepository.startServer()
    }

    val messages: LiveData<ArrayList<Message>>
        get() = serverRepository.getAllMessage()

    val serverStartedMessage = Transformations.map(messages) {
        it.firstOrNull()
    }

    fun getUserName() = localDataSource.getUserName()

    fun sendMessage() {
        serverRepository.sendMessage(messageText.value.orEmpty())
        messageText.value = ""
    }

    val onNewMessage = serverRepository.onNewMessage()

    override fun onCleared() {
        super.onCleared()
        serverRepository.stopServer()
    }

    fun stopServer() {
        serverRepository.stopServer()
    }
}
