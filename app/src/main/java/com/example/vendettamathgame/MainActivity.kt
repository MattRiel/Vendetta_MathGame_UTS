package com.example.vendettamathgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    // deklarasi variabel button dan pilihan
    lateinit var buttonDivisor: Button
    lateinit var buttonOpMath: Button
    lateinit var buttonFAN: Button
    val btnClick = false

    var pilihan = 0

    // inisialisasi tampilan awal homescreen
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonDivisor = findViewById(R.id.btnDivisor)

        // pengiriman intent menuju GameActivity sebagai pemicu button
        buttonDivisor.setOnClickListener() {

            val intent = Intent(this, GameActivity::class.java)
            pilihan = 1
            intent.putExtra("PIL", pilihan.toString())
            startActivity(intent)
        }

        buttonOpMath = findViewById(R.id.btnOpMath)
        buttonOpMath.setOnClickListener() {
            pilihan = 2
            val intent = Intent(this@MainActivity, GameActivity::class.java)
            intent.putExtra("PIL", pilihan.toString())
            startActivity(intent)
        }

        buttonFAN = findViewById(R.id.btnFAN)
        buttonFAN.setOnClickListener() {
            var pilihan = 3
            val intent = Intent(this@MainActivity, GameActivity::class.java)
            intent.putExtra("PIL", pilihan.toString())
            startActivity(intent)
        }

    }
}
