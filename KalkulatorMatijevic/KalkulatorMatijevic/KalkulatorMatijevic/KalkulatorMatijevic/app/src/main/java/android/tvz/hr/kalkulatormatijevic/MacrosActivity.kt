package android.tvz.hr.kalkulatormatijevic

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.Console

class MacrosActivity : AppCompatActivity() {

    var isMaleOrFemale = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_macros)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<RadioButton>(R.id.radio_male).setOnCheckedChangeListener { buttonView, isChecked ->
            isMaleOrFemale = 0
            println("Male is pressed")
        }

        findViewById<RadioButton>(R.id.radio_female).setOnCheckedChangeListener { buttonView, isChecked ->
            isMaleOrFemale = 1
            println("Female is pressed")
        }
    }


    @SuppressLint("WrongViewCast")
    fun calculateMacros(view: View?){
        var calories = 0f
        var prot = 0
        var carb = 0
        var fat = 0

        val godineEditText: EditText = findViewById(R.id.editAgeNumber)
        val tezinaEditText: EditText = findViewById(R.id.editWeightNumber)
        val visinaEditText: EditText = findViewById(R.id.editHeightNumber)
        var resultText: TextView = findViewById(R.id.resultView)

        var weight = tezinaEditText.text.toString().toFloat()
        var height = visinaEditText.text.toString().toFloat()
        var age = godineEditText.text.toString().toFloat()
        //Male part
        if(isMaleOrFemale == 0){
            calories = 10 * weight + 6.25f * height - 5 * age + 5
        }
        //Female part
        else if (isMaleOrFemale == 1){
            calories = 10 * weight + 6.25f * height - 5 * age - 161
        }

        resultText.text = "Calorie Intake: " + calories

    }



}