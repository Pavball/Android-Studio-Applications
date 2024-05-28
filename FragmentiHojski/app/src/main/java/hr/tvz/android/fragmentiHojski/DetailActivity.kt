package hr.tvz.android.fragmentiHojski

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment


class DetailActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)


        val name = findViewById<TextView>(R.id.gpuName)
        val desc = findViewById<TextView>(R.id.desc)
        val img = findViewById<ImageButton>(R.id.imid)


        val intent = intent

        val title = intent?.getStringExtra("name")
        val det = intent?.getStringExtra("desc")
        val imeg = intent?.getIntExtra("image" ,0)

        name.text=title
        desc.text = det
        if (imeg != null) {
            img.setImageResource(imeg)
        }

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_share -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Confirmation")
                builder.setMessage("Jeste li sigurni da zelite podijeliti sadrzaj?")
                builder.setPositiveButton("Da") { dialog, _ ->
                    shareContent()
                    dialog.dismiss()
                }
                builder.setNegativeButton("Ne") { dialog, _ ->
                    dialog.dismiss()
                }
                val dialog = builder.create()
                dialog.show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    fun openPage(view: View) {
        val url:String = resources.getString(R.string.links)
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }

    fun getToImage(view: View) {
        val img = findViewById<ImageButton>(R.id.imid)
        val intent = Intent(view.context, ImageActivity::class.java)
        intent.putExtra("image", R.drawable.nvidia)

        view.context.startActivity(intent)
    }

    private fun shareContent() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, "Your shared text here")
        startActivity(Intent.createChooser(intent, "Share via"))
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
