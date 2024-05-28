package hr.tvz.android.fragmentiHojski

import android.R
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

class Notifikacija: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun posaljiNotifikaciju(view: android.view.View) {
        var builder = Notification.Builder(this, MainActivity().Kanal)
            .setSmallIcon(android.R.drawable.ic_menu_save)
            .setLargeIcon(BitmapFactory.decodeResource(resources, android.R.drawable.ic_menu_send))
            .setContentTitle("My notification")
            .setContentText("Hello world!")
            .setSubText("20")
            .setTicker("New notification has arrived")

        val resultIntent = Intent(this, MainActivity::class.java)
        resultIntent.putExtra("string", "Mogu se prenositi i podaci")
        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            resultIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        builder.setContentIntent(pendingIntent)

        var notification: Notification = builder.build()
        var notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, notification)
    }


}