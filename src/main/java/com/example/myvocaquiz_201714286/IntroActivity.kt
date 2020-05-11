package com.example.myvocaquiz_201714286

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_intro.*
import java.io.PrintStream

class IntroActivity : AppCompatActivity() {

    val textArray = arrayListOf<String>("단어리스트","퀴즈")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        init()
    }

    private fun init(){

        PrintStream(openFileOutput("out.txt", Context.MODE_APPEND))

        vocaBtn.setOnClickListener {
            val i = Intent(this, VocListActivity::class.java)
            startActivity(i)
        }

        quizBtn.setOnClickListener {
            val i = Intent(this, OptionActivity::class.java)
            startActivity(i)
        }



    }
}
