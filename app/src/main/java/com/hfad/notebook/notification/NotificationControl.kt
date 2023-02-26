package com.hfad.notebook.notification

import APP
import android.R
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings.Global.getString
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import java.text.SimpleDateFormat
import java.util.*


class NotificationControl {

    @SuppressLint("UnspecifiedImmutableFlag")
    fun setNotificationAtSelectedTime(
        currentTime: Long,
        title: String,
        description: String,
        difference: Long

    ) {
        notificationChanel()
        val id = getIdentificatorForPendingIntent(currentTime)
        val intent = Intent(APP, NotificationReceiver::class.java).apply {
            putExtra("title", title)
            putExtra("description", description)
            putExtra("id", id)
        }

        val pI = PendingIntent.getBroadcast(
            APP,
            id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = APP.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        alarmManager.set(AlarmManager.RTC_WAKEUP, currentTime + difference, pI)

    }

    @SuppressLint("UnspecifiedImmutableFlag")
    fun cancelNotification(creationTime: String, context: Context) {
        val dateFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US)
        val timeCreationLong = dateFormat.parse(creationTime)?.time ?: return
        val id = getIdentificatorForPendingIntent(timeCreationLong)
        val intent = Intent(context, NotificationReceiver::class.java)
        val pI = PendingIntent.getBroadcast(
            context,
            id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pI)
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    fun updateNotificationAtSelectedTime(
        currentTime: Long,
        title: String,
        description: String,
        difference: Long

    ) {
        notificationChanel()
        val id = getIdentificatorForPendingIntent(currentTime)
        val intent = Intent(APP, NotificationReceiver::class.java).apply {
            putExtra("title", title)
            putExtra("description", description)
            putExtra("id", id)
        }

        Log.v("id_update", id.toString())
        val pI = PendingIntent.getBroadcast(
            APP,
            id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmManager = APP.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pI)
        alarmManager.set(AlarmManager.RTC_WAKEUP, currentTime + difference, pI)

    }

    private fun getIdentificatorForPendingIntent(currentTime: Long): Int {
        return currentTime.toString().toCharArray().concatToString(4, 10).toInt()
    }

    private fun notificationChanel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notebook"
            val descriptionText = "This is my notification channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT //if it is not working use _MAX or _HIGH
            val channel = NotificationChannel("my_channel_01", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                APP.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}