package ru.rustore.sdk.appupdateexample

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.RingtoneManager
import android.net.Uri
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        getFirebaseMessage(
            remoteMessage.notification?.title, remoteMessage.notification?.body, applicationContext
        )
    }

    private fun getFirebaseMessage(title: String?, message: String?, context: Context) {
        val channelId = "myFirebaseChannel"
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(context, channelId)
        builder.setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
        builder.setContentTitle(title)
        builder.setContentText(message)
        builder.setAutoCancel(true)

        // Задаем звук уведомления
        val defaultSoundUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val attributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_NOTIFICATION)
            .build()
        builder.setSound(defaultSoundUri, attributes)

        val notificationManager = NotificationManagerCompat.from(context)

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }

        // Запускаем уведомление
        notificationManager.notify(101, builder.build())
    }
}

private fun NotificationCompat.Builder.setSound(defaultSoundUri: Uri, attributes: AudioAttributes?) {

}
