package com.example.vendettamathgame

import android.annotation.SuppressLint
import android.graphics.Color
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
import kotlin.random.Random

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class OperationMathFragment : Fragment(), MathAppGame {

    lateinit var buttons: Array<Button>
    lateinit var validationTextView: TextView
    lateinit var resultTextView : TextView

    // deklarasi variabel awal
    var answerPressed = true
    var chosenAnswer = 0
    var secondValue = 0
    var currentScore = 0
    var answerFromBefore = 0

    var btnValue1 = 0
    var btnValue2 = 0
    var btnValue3 = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_operation_math, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Array button
        buttons = arrayOf(
            view.findViewById(R.id.btnOp1),
            view.findViewById(R.id.btnOp2),
            view.findViewById(R.id.btnOp3)
        )


        resultTextView = view.findViewById(R.id.resultTextView)

        //Mengosongkan isi validationTextView
        validationTextView = view.findViewById(R.id.validationTextView)
        validationTextView.text =" "

        //Memberi nilai/parameter kepada fungsi checkButton

        //do generateQuestion() function
        generateQuestion()


    }
    private fun generateQuestion()
    {
        for (button in buttons)
            button.isEnabled = true

        // deklarasi value yang akan dipakai
        answerPressed = false
        chosenAnswer = 0
        secondValue = 0

        // inisialisasi nilai acak
        var randomGenerator = Random(System.currentTimeMillis())

        // generasi nilai acak menggunakan randomGeneraton
        var result      = randomGenerator.nextInt(1, 6)
        answerFromBefore = result

        var btn_Op1_Value1 = randomGenerator.nextInt(1, 30)
        var btn_Op1_Value2 = randomGenerator.nextInt(1, 30)
        btnValue1 = btn_Op1_Value1 + btn_Op1_Value2

        var btn_Op2_Value1 = randomGenerator.nextInt(1, 30)
        var btn_Op2_Value2 = randomGenerator.nextInt(1, 30)
        btnValue2 = btn_Op2_Value1 + btn_Op2_Value2

        var btn_Op3_Value1 = randomGenerator.nextInt(1, 50)
        var btn_Op3_Value2 = randomGenerator.nextInt(1, 50)
        btnValue3 = btn_Op3_Value1 - btn_Op3_Value2


        // pengacakan lokasi tombol (button)
        var arrayInt = arrayOf(0, 1, 2)
        arrayInt.shuffle(randomGenerator)

        //button values based on arrayInt
        buttons[arrayInt[0]].text = (btn_Op1_Value1.toString()) + "+" + (btn_Op1_Value2.toString())
        buttons[arrayInt[1]].text = (btn_Op2_Value1.toString()) + "+" + (btn_Op2_Value2.toString())
        buttons[arrayInt[2]].text = (btn_Op3_Value1.toString()) + "-" + (btn_Op3_Value2.toString())
        buttons[arrayInt[0]].setOnClickListener {
            chosenAnswer = btnValue1;
            checkButton()
        }
        buttons[arrayInt[1]].setOnClickListener {
            chosenAnswer = btnValue2
            checkButton()
        }
        buttons[arrayInt[2]].setOnClickListener {
            chosenAnswer = btnValue3
            checkButton()
        }
    }


    @SuppressLint("SuspiciousIndentation")
    override fun finishGame()
    {
        // Skor ditampilkan dan button kembali menjadi disabled
        for (button in buttons)
            button.isEnabled = false
        resultTextView.isVisible = true
        resultTextView.text = "YOUR SCORE: "+ currentScore.toString()

    }

    private fun checkButton()
    {
        var correctAnswer = 0

        // pemeriksaan button berdasarkan masing - masing value
        if (btnValue1 >= btnValue2 && btnValue1 >= btnValue3)
            correctAnswer = btnValue1
        else if (btnValue2 >= btnValue1 && btnValue2 >= btnValue3)
            correctAnswer = btnValue2
        else correctAnswer = btnValue3

        // validasi jawaban
        if (chosenAnswer == correctAnswer) {
            validationTextView.text = "CORRECT!"
            validationTextView.setTextColor(Color.GREEN)
            currentScore = currentScore + 1
            generateQuestion()
        } else {
            validationTextView.text = "WRONG!!!"
            validationTextView.setTextColor(Color.RED)
            generateQuestion()
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