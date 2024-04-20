package android.tvz.hr.kalkulatormatijevic

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.tvz.hr.kalkulatormatijevic.databinding.ActivityMainBinding
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

private lateinit var binding: ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private var isDarkTheme = false
    private lateinit var typeface: Typeface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var spinnerFont = binding.spinnerMain

        val robotoString = getString(R.string.roboto)
        val poppinsString = getString(R.string.poppins)
        val typewriterString = getString(R.string.typewriter)
        val vogueString = getString(R.string.vogue)

        val odabirFontSize = listOf(typewriterString, robotoString, poppinsString, vogueString)

        val arrayAdpFont = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_dropdown_item, odabirFontSize)

        spinnerFont.adapter = arrayAdpFont

        spinnerFont.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {

                if(odabirFontSize[position] == "16sp"){
                    binding.titleView.textSize = 16f
                    binding.changeFontView.textSize = 16f
                }
                else if(odabirFontSize[position] == "24sp"){
                    binding.titleView.textSize = 24f
                    binding.changeFontView.textSize = 24f
                }
                else if(odabirFontSize[position] == "36sp"){
                    binding.titleView.textSize = 36f
                    binding.changeFontView.textSize = 36f
                }
                else if(odabirFontSize[position] == "48sp"){
                    binding.titleView.textSize = 48f
                    binding.changeFontView.textSize = 48f
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        })


    }


    fun onCalculatorClick(view: View?) {
        // Add code to navigate to the new view/activity
        startActivity(Intent(this, CalculatorActivity::class.java))
    }

    fun onBmiClick(view: View?) {
        // Add code to navigate to the new view/activity
        startActivity(Intent(this, BMIActivity::class.java))
    }


    fun onMacrosClick(view: View?) {
        // Add code to navigate to the new view/activity
        startActivity(Intent(this, MacrosActivity::class.java))
    }
}

