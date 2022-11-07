package com.example.vendettamathgame

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random
import kotlin.system.measureTimeMillis

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class DivisorFragment : Fragment(), MathAppGame {

    // deklarasi button,textview, dan validation view
    lateinit var buttons: Array<Button>
    lateinit var resultTextView: TextView
    lateinit var validationTextView : TextView

    // inisialisasi nilai value
    var firstButtonPressed = false
    var firstValue = 0
    var secondValue = 0
    var currentScore = 0
    var randomGenerator = Random(System.currentTimeMillis())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_divisor, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Array button
        buttons = arrayOf(
            view.findViewById(R.id.button1),
            view.findViewById(R.id.button2),
            view.findViewById(R.id.button3),
            view.findViewById(R.id.button4),
        )

        //Memberi nilai/parameter kepada fungsi checkButton
        for (button in buttons)
        {
            button.setOnClickListener()
            {
                    view: View -> checkButton(view)
            }
        }

        resultTextView = view.findViewById(R.id.resultTextView)

        //Mengosongkan isi validationTextView
        validationTextView = view.findViewById(R.id.validationTextView)
        validationTextView.text =" "

        //Menampilkan soal
        generateQuestion()

    }
    private fun generateQuestion()
    {
        // Meng-enable kembali button agar dapat ditekan
        for (button in buttons)
            button.isEnabled = true

        //Reset kembali
        firstButtonPressed = false
        firstValue = 0
        secondValue = 0

        //Mendapatkan angka pembagi <6 dan !=5
        var result = randomGenerator.nextInt(2, 6)
        if(result == 5)
            result -=1

        var firstCorrectValue = randomGenerator.nextInt(result+2, 50)
        var secondCorrectValue = randomGenerator.nextInt(result+2, 50)

        // Bila angka tidak habis dibagi dengan angka result
        while (firstCorrectValue % result != 0 || secondCorrectValue % result != 0)
        {
            // maka akan dilakukan kembali randomize angka hingga didapat 2 angka yang sesuai
            firstCorrectValue = randomGenerator.nextInt(result+2, 50)
            secondCorrectValue = randomGenerator.nextInt(result+2, 50)
        }

        resultTextView.text = result.toString()

        var firstRandomValue =  randomGenerator.nextInt(result+2, 50)
        var secondRandomValue = randomGenerator.nextInt(result+2, 50)

        // Bila angka habis dibagi dengan angka result
        while (firstRandomValue % result == 0 || secondRandomValue % result == 0)
        {
            // maka akan dilakukan kembali randomize untuk mendapatkan 2 angka yang tidak sesuai
            firstRandomValue = randomGenerator.nextInt(result+2, 50)
            secondRandomValue = randomGenerator.nextInt(result+2, 50)
        }

        // Mengacak urutan angka pada array
        var arrayInt = arrayOf(0,1,2,3)
        arrayInt.shuffle(randomGenerator)

        // Button dengan 2 angka yang benar
        buttons[arrayInt[0]].text = firstCorrectValue.toString()
        buttons[arrayInt[1]].text = secondCorrectValue.toString()

        // Button dengan 2 angka yang tidak sesuai
        buttons[arrayInt[2]].text = firstRandomValue.toString()
        buttons[arrayInt[3]].text = secondRandomValue.toString()
        }


    @SuppressLint("SuspiciousIndentation")
    override fun finishGame()
    {
        //Button tidak dapat lagi ditekan dan menampilkan skor
        for (button in buttons)
            button.isEnabled = false
        resultTextView.setTextSize(40f)
        resultTextView.setTypeface(Typeface.DEFAULT_BOLD)
        resultTextView.setTextColor(Color.parseColor("#3F51B5"))
        resultTextView.text = "YOUR SCORE: "+ currentScore.toString()

    }

    private fun checkButton(view: View)
    {
        var buttonPressed = view as Button

        //Bila sudah ada button yang ditekan
        if(firstButtonPressed)
        {
            secondValue = buttonPressed.text.toString().toInt()

            //validasi 2 angka yang ditekan dapat habis dibagi dengan val answer
            val answer = resultTextView.text.toString().toInt()
            if ((firstValue%answer == 0) && (secondValue%answer == 0))
            {
                validationTextView.text = "CORRECT!"
                validationTextView.setTextColor(Color.GREEN)
                currentScore = currentScore + 1
            }
            else
            {
                validationTextView.text = "WRONG!!!"
                validationTextView.setTextColor(Color.RED)
            }

            //Menampilkan soal berikutnya
            generateQuestion()
        }
        else
        {
            //Bila salah satu button ditekan, maka button tersebut akan di-enable
            firstValue = buttonPressed.text.toString().toInt()
            firstButtonPressed = true
            buttonPressed.isEnabled = false
        }
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DivisorFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


}