package hr.tvz.android.fragmentiHojski

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.widget.Toast

class AirModeReciever: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_AIRPLANE_MODE_CHANGED) {
            val isOn = Settings.Global.getInt(
                context?.contentResolver,
                Settings.Global.AIRPLANE_MODE_ON
            ) != 0
            val toast = Toast.makeText(context, "Yeah!", Toast.LENGTH_SHORT).show()
        }
    }
}