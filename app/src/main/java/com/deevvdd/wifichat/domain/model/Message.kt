package com.deevvdd.wifichat.domain.model

import java.util.Date

/**
 * Created by heinhtet deevvdd@gmail.com on 06,August,2021
 */
data class Message(
    val senderName: String,
    val message: String,
    val date: Date = Date()
)
