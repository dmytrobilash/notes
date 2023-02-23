package com.hfad.notebook.notification

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.hfad.notebook.R

class NotificationReceiver : BroadcastReceiver () {

    override fun onReceive(context: Context?, intent: Intent?) {

        val notificationTitle = intent?.getStringExtra("title")
        val notificationMessage = intent?.getStringExtra("description")

        val builder = NotificationCompat.Builder(context!!, "Notify")
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .setContentTitle(notificationTitle)
            .setContentText(notificationMessage)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, builder.build())
    }
}