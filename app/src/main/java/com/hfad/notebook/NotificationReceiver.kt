package com.hfad.notebook

import APP
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

class NotificationReceiver : BroadcastReceiver (){

    override fun onReceive(context: Context?, intent: Intent?) {

        val notificationTitle = intent?.getStringExtra("title")
        val notificationMessage = intent?.getStringExtra("message")

        val builder = NotificationCompat.Builder(context!!, "my_channel_id")
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .setContentTitle(notificationTitle)
            .setContentText(notificationMessage)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationIntent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0)
        builder.setContentIntent(pendingIntent)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, builder.build())
    }

}