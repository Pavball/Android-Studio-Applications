package android.tvz.hr.kalkulatormatijevic

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.roundToInt


class BMIActivity : AppCompatActivity() {

    var selectedItemVisina = ""
    var selectedItemTezina = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_bmiactivity)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var spinnerTezina = findViewById<Spinner>(R.id.spinnerTezina)
        val spinnerVisina = findViewById<Spinner>(R.id.spinnerVisina)


        val kiloString = getString(R.string.kilograms)
        val poundsString = getString(R.string.pounds)
        val centiString = getString(R.string.centimeters)
        val inchString = getString(R.string.inches)

        val odabirTezina = arrayOf(kiloString, poundsString)
        val odabirVisina = arrayOf(centiString,inchString)

        val arrayAdpTezina = ArrayAdapter(this@BMIActivity, android.R.layout.simple_spinner_dropdown_item, odabirTezina)
        val arrayAdpVisina = ArrayAdapter(this@BMIActivity, android.R.layout.simple_spinner_dropdown_item, odabirVisina)

        spinnerTezina.adapter = arrayAdpTezina
        spinnerVisina.adapter = arrayAdpVisina

        spinnerTezina.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
               selectedItemTezina = spinnerTezina.selectedItem.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        })

        spinnerVisina.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                selectedItemVisina = spinnerVisina.selectedItem.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        })

    }

    @SuppressLint("WrongViewCast")
    fun onGoClick(view: View?){

        val tezinaEditText: EditText = findViewById(R.id.tezinaId)
        val visinaEditText: EditText = findViewById(R.id.visinaId)
        var resultText: TextView = findViewById(R.id.resultView)

        if(tezinaEditText.text!!.isEmpty() || visinaEditText.text!!.isEmpty()){
            resultText.text = "Upisite broj!"
        }
        else{
            var num1 = tezinaEditText.text.toString().toFloat()
            var num2 = visinaEditText.text.toString().toFloat()

            if(selectedItemVisina == "Incevi"){
                num2 /= 0.3937f
            }

            if(selectedItemTezina == "Funte"){
                num1 /= 2.2046f
            }

            if(num2 == 0f){
                resultText.text = "Visina ne moze biti 0!"
            }else{
                var numUMetre = num2/100
                var result = num1 / (numUMetre*numUMetre)

                val roundoff = (result * 100.0).roundToInt() / 100.0
                resultText.text = "Vas BMI je: " + roundoff.toString()
            }

        }
    }

    fun onBackClick(view: View?) {
        // Add code to navigate to the new view/activity
        startActivity(Intent(this, MainActivity::class.java))

    }
}