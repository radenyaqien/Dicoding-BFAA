package id.radenyaqien.githubuserdicoding.util

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import id.radenyaqien.githubuserdicoding.R
import java.util.*


const val CHANNEL_ID = "github_user"

class MyReceiver : BroadcastReceiver() {
    companion object {
        private const val ID_REPEATING = 10666
    }

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        sendNotification(context)
    }

    private fun sendNotification(context: Context) {
        val title = context.getString(R.string.bdd)
        val message = context.getString(R.string.lets_find_user_popular)

        val intent = Intent(context, MyReceiver::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val v = longArrayOf(500, 1000)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationBuilder = NotificationCompat.Builder(
            context, CHANNEL_ID
        )
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setSound(defaultSoundUri)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Github user",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationBuilder.setChannelId(CHANNEL_ID)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(ID_REPEATING, notificationBuilder.build())
    }

    fun cancelAlarm(it: Context) {
        val alarmManager = it.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(it, MyReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(it, ID_REPEATING, intent, 0)
        pendingIntent.cancel()

        alarmManager.cancel(pendingIntent)

        Toast.makeText(it, it.getString(R.string.alarm_cancel), Toast.LENGTH_SHORT).show()
    }

    fun setRepeatingAlarm(it: Context) {
        val alarmManager = it.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent: PendingIntent = Intent(it, MyReceiver::class.java).let { inten ->
            PendingIntent.getBroadcast(it, ID_REPEATING, inten, 0)
        }

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 2)
        }

        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            intent
        )

        Toast.makeText(it, it.getString(R.string.alarm_aktif), Toast.LENGTH_SHORT).show()
    }
}