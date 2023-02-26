package com.hfad.notebook.notification

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.hfad.notebook.R

class NotificationReceiver : BroadcastReceiver () {

    override fun onReceive(context: Context?, intent: Intent?) {

        val notificationTitle = intent?.getStringExtra("title")
        val notificationMessage = intent?.getStringExtra("description")

        val builder = NotificationCompat.Builder(context!!, "my_channel_01")
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .setContentTitle(notificationTitle)
            .setContentText(notificationMessage)
            .setPriority(NotificationCompat.PRIORITY_MAX)

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(1, builder.build())
    }
}