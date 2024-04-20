package android.tvz.hr.kalkulatormatijevic


import android.content.Intent
import android.os.Bundle
import android.tvz.hr.kalkulatormatijevic.databinding.ActivityCalculatorBinding
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.roundToLong

private lateinit var binding: ActivityCalculatorBinding


class CalculatorActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityCalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    fun onBackClick(view: View?) {
        // Add code to navigate to the new view/activity
        startActivity(Intent(this, MainActivity::class.java))
    }
    fun ButtonAdd(view: View?) {

        if(binding.firstNumber.text!!.isEmpty() || binding.secondNumber.text!!.isEmpty()){
            binding.answerView.text = "Upisite broj!"
        }
        else{
            var num1 = binding.firstNumber.text.toString().toFloat()
            var num2 = binding.secondNumber.text.toString().toFloat()

            var result = num1 + num2

            binding.answerView.text = "Rezultat: " + result.toString()
        }


    }

    fun ButtonSub(view: View?) {

        if(binding.firstNumber.text!!.isEmpty() || binding.secondNumber.text!!.isEmpty()){
            binding.answerView.text = "Upisite broj!"
        }
        else{
            var num1 = binding.firstNumber.text.toString().toFloat()
            var num2 = binding.secondNumber.text.toString().toFloat()

            var result = num1 - num2

            binding.answerView.text = "Rezultat: " + result.toString()
        }
    }

    fun ButtonMulti(view: View?) {

        if(binding.firstNumber.text!!.isEmpty() || binding.secondNumber.text!!.isEmpty()){
            binding.answerView.text = "Upisite broj!"
        }
        else{
            var num1 = binding.firstNumber.text.toString().toFloat()
            var num2 = binding.secondNumber.text.toString().toFloat()

            var result = num1 * num2
            result.roundToLong()

            binding.answerView.text = "Rezultat: " + result.toString()
        }

    }
    fun ButtonDiv(view: View?) {

        if(binding.firstNumber.text!!.isEmpty() || binding.secondNumber.text!!.isEmpty()){
            binding.answerView.text = "Upisite broj!"
        }
        else{
            var num1 = binding.firstNumber.text.toString().toFloat()
            var num2 = binding.secondNumber.text.toString().toFloat()

            if(num2 == 0f){
                binding.answerView.text = "Ne mozete dijeliti sa 0!"
            }else{
                var result = num1 / num2
                binding.answerView.text = "Rezultat: " + result.toString()
            }

        }

    }

}