package com.example.vendettamathgame

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import kotlin.random.Random

class GameActivity : AppCompatActivity(),MathAppGame {

    //variabel untuk memanggil fragment
    var dv = DivisorFragment()
    var om = OperationMathFragment()
    var FAN = FastandNumerousFragment()

    //variabel untuk progressBar
    val maxTimeinMillis = 20000L
    val minTimeinMillis = 0L
    val intervalinMillis = 10L

    //ProgressBar
    lateinit var timerProgressBar : ProgressBar

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        //Mengambil nilai pilihan pada menu homescreen
        var game = intent.getStringExtra("PIL")
        println(game)

        //untuk tombol back/kembali
        val actionBar = supportActionBar
        actionBar!!.title = "Vendetta Math Game"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)

        //Beralih ke fragment game sesuai game yang dipilih pada homescreen
        if(game=="1") {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainerView, dv) // replace
                .commit()
        }
        else if(game=="2") {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainerView, om)
                .commit()
        }
        else {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainerView, FAN)
                .commit()
        }

        //ProgressBar
        timerProgressBar = findViewById(R.id.progressBar)
        timerProgressBar.max = maxTimeinMillis.toInt()
        timerProgressBar.min = minTimeinMillis.toInt()

        //CountDown Timer untuk semua fragment
        val timer = object : CountDownTimer(maxTimeinMillis,intervalinMillis)
        {
            override fun onTick(millisUntilFinished: Long) {
                timerProgressBar.progress = (millisUntilFinished).toInt()
            }

            override fun onFinish() {
                val f = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
                val mathAppGame = f as MathAppGame
                mathAppGame.finishGame()
            }
        }
        //Memulai CountDown Timer
        timer.start()
    }

    // fungsi untuk tombol back/kembali
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun finishGame() {
        TODO("Not yet implemented")
    }


}