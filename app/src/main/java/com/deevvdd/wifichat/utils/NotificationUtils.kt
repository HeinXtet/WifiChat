package com.deevvdd.wifichat.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.deevvdd.wifichat.R
import com.deevvdd.wifichat.domain.model.Message
import java.util.Date

/*
 * We need to create a NotificationChannel associated with our CHANNEL_ID before sending a
 * notification.
 */

fun sendNotification(context: Context, message: Message, userName: String) {

    if (message.senderName == userName || message.senderName.isNotEmpty()) {
        return
    }

    val notificationManager = ContextCompat.getSystemService(
        context,
        NotificationManager::class.java
    ) as NotificationManager

    notificationManager.sendNewMessageNotification(
        context,
        message.message,
        message.senderName
    )
}

fun createChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val notificationChannel = NotificationChannel(
            CHANNEL_ID,
            context.getString(R.string.channel_name),

            NotificationManager.IMPORTANCE_HIGH
        )
            .apply {
                setShowBadge(false)
            }

        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.RED
        notificationChannel.enableVibration(true)
        notificationChannel.description =
            context.getString(R.string.notification_channel_description)

        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(notificationChannel)
    }
}

fun NotificationManager.sendNewMessageNotification(
    context: Context,
    message: String,
    senderName: String
) {

    val contentText = "$senderName: sent $message"
    val builder = NotificationCompat.Builder(context, CHANNEL_ID)
        .setContentTitle(context.getString(R.string.app_name))
        .setContentText(contentText)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setSmallIcon(R.drawable.ic_user)
        .setAutoCancel(false)

    notify(NOTIFICATION_ID + Date().time.toInt(), builder.build())
}

private const val NOTIFICATION_ID = 33
private const val CHANNEL_ID = "Wifi Chat Channel"
