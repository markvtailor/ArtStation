package com.markvtls.artstation.workers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import com.markvtls.artstation.MainActivity

import com.markvtls.artstation.R
import com.markvtls.artstation.data.Constants.NOTIFICATIONS_CHANNEL_ID

class NotificationsManager(
    private val context: Context) {



    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notifications"
            val descriptionText = "ArtStation notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(NOTIFICATIONS_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            context.getSystemService<NotificationManager>()?.createNotificationChannel(channel)
        }

    }

    private val intent = Intent(context, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }

    @RequiresApi(Build.VERSION_CODES.M)
    val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

    @RequiresApi(Build.VERSION_CODES.M)
    var builder = NotificationCompat.Builder(context, NOTIFICATIONS_CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_brush)
        .setContentTitle("ArtStation")
        .setContentText("Доступно новое изображение!")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)




}