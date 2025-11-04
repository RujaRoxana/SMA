package com.example.myapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.util.*

object SmsHandler {
    private const val CHANNEL_ID = "alerts"

    fun handleIncoming(context: Context, from: String, message: String) {
        // Ignoră SMS-urile care nu sunt de tip NTIFY:
        if (!message.startsWith("NTIFY:")) return

        // Format simplu: NTIFY:Titlu|Mesaj
        val payload = message.removePrefix("NTIFY:").trim()
        val parts = payload.split("|")
        if (parts.size < 2) return

        val title = parts[0]
        val body = parts[1]

        showNotification(context, title, body)
    }

    private fun showNotification(context: Context, title: String, message: String) {
        // Creează canalul de notificare (obligatoriu pe Android 8+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "SMS Alerts",
                NotificationManager.IMPORTANCE_HIGH
            )
            val nm = context.getSystemService(NotificationManager::class.java)
            nm.createNotificationChannel(channel)
        }

        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(context)
            .notify(Random().nextInt(), notification)
    }
}
