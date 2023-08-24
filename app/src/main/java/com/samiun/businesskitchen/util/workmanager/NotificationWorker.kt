package com.samiun.businesskitchen.util.workmanager

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.work.*
import com.samiun.businesskitchen.R
import timber.log.Timber
import java.time.Duration
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

class NotificationWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : Worker(appContext, workerParams) {

    @SuppressLint("ObsoleteSdkInt")
    @RequiresApi(Build.VERSION_CODES.S)
    override fun doWork(): Result {
        val message = inputData.getString("message")
        // Create a notification channel if it doesn't exist
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                applicationContext.getString(R.string.alarm_channal_id_string),
                applicationContext.getString(R.string.alarm_channel_name_string),
                NotificationManager.IMPORTANCE_HIGH
            )
            val notificationManager =
                applicationContext.getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
        }
        // Set the custom ringtone for the notification
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)

        // Create a notification
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // Define notification ID counter
        val notificationId = (System.currentTimeMillis() % 10000).toInt()
        val channelId = applicationContext.getString(R.string.alarm_channal_id_string)
        val channelName = applicationContext.getString(R.string.app_name)
        val snoozeAction = createSnoozeAction(applicationContext, inputData, notificationId)
        val cancelAction = createCancelAction(applicationContext, notificationId)
        val importance = NotificationManager.IMPORTANCE_HIGH
        val notificationChannel = NotificationChannel(channelId, channelName, importance)
        notificationManager.createNotificationChannel(notificationChannel)

        val notificationBuilder = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.kichen_icon)
            .setContentTitle(applicationContext.getString(R.string.notification_content_title_string))
            .setContentText(message)
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .setSound(alarmSound)
            .addAction(snoozeAction)
            .addAction(cancelAction)
            .setAutoCancel(true)
        notificationManager.notify(notificationId, notificationBuilder.build())
        return Result.success()
    }

    // Create snooze action
    @RequiresApi(Build.VERSION_CODES.S)
    private fun createSnoozeAction(
        context: Context,
        inputData: Data,
        notificationId: Int
    ): NotificationCompat.Action {
        val snoozeIntent = Intent(context, NotificationBroadcastReceiver::class.java).apply {
            action = context.getString(R.string.snooze_notification_action_string)
            putExtra("message", inputData.getString("message"))
            putExtra("notificationId", notificationId)
        }
        val snoozePendingIntent = PendingIntent.getBroadcast(
            context,
            notificationId,
            snoozeIntent,
            PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        return NotificationCompat.Action.Builder(
            R.drawable.snooze,
            context.getString(R.string.snooze_title_string),
            snoozePendingIntent
        ).build()
    }

    // Create cancel action
    @RequiresApi(Build.VERSION_CODES.S)
    private fun createCancelAction(
        context: Context,
        notificationId: Int
    ): NotificationCompat.Action {
        val cancelIntent = Intent(context, NotificationBroadcastReceiver::class.java).apply {
            action = context.getString(R.string.cancel_notification_action_string)
            putExtra("notificationId", notificationId)
        }
        val cancelPendingIntent = PendingIntent.getBroadcast(
            context,
            notificationId,
            cancelIntent,
            PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        return NotificationCompat.Action.Builder(
            R.drawable.cancel,
            context.getString(R.string.cancel_title_string),
            cancelPendingIntent
        ).build()
    }

}

fun setNotifications(itemname: String, time: LocalDateTime, current: Context) {
    val currentTime = LocalDateTime.now()
    val duration = Duration.between(currentTime, time)
    val delay = duration.toMillis()

    val data = Data.Builder()
        .putString("message", itemname)
        .build()

    val notificationWorkRequest = OneTimeWorkRequest.Builder(NotificationWorker::class.java)
        .setInitialDelay(delay, TimeUnit.MILLISECONDS)
        .setInputData(data)
        .build()

    WorkManager.getInstance(current).enqueue(notificationWorkRequest)
    Timber.e("Notification set on $time  of $itemname")
}
