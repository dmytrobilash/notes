package com.hfad.notebook.notification

import APP
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
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

        val intent = Intent(APP, NotificationReceiver::class.java).apply {
            putExtra("title", title)
            putExtra("description", description)
        }

        val id = getIdentificatorForPendingIntent(currentTime)

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
        Log.v("id_delete", id.toString())
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

        val intent = Intent(APP, NotificationReceiver::class.java).apply {
            putExtra("title", title)
            putExtra("description", description)
        }

        val id = getIdentificatorForPendingIntent(currentTime)
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
}