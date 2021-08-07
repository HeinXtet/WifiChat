package com.deevvdd.wifichat.domain.repository

import androidx.lifecycle.LiveData
import com.deevvdd.wifichat.domain.model.Message

/**
 * Created by heinhtet deevvdd@gmail.com on 06,August,2021
 */
interface ServerRepository {
    fun startServer()
    fun stopServer()
    fun sendMessage(message: String)
    fun getAllMessage(): LiveData<ArrayList<Message>>
    fun onNewMessage(): LiveData<Message>
}
