package hr.tvz.android.fragmentiHojski

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class ImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_image)

        val img = findViewById<ImageView>(R.id.slikica)

        val imeg = intent?.getIntExtra("image" ,0)
        if (imeg != null) {
            img.setImageResource(imeg)
        }
    }
}