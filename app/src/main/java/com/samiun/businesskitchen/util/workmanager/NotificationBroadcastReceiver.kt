package com.samiun.businesskitchen.util.workmanager

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.samiun.businesskitchen.R
import java.time.LocalDateTime

class NotificationBroadcastReceiver : BroadcastReceiver() {
    private val notificationList = mutableListOf<Int>()
    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationId = intent.getIntExtra("notificationId", 0)
        notificationList.add(notificationId)
        if (notificationList.contains(notificationId)) {
            when (intent.action) {
                context.getString(R.string.cancel_notification_action_string) -> {
                    notificationManager.cancel(notificationId)
                    notificationList.remove(notificationId)
                    Log.d("notification1234", "CancelReceive: $notificationId")
                }

                context.getString(R.string.snooze_notification_action_string) -> {
                    notificationManager.cancel(notificationId)
                    setNotifications(
                        intent.getStringExtra("message").toString(),
                        LocalDateTime.now().plusMinutes(10),
                        context
                    )
                }
            }
        }
    }
}
