package com.example.myvocaquiz_201714286

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_finish_quiz.*

class FinishQuizActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish_quiz)

        init()
    }

    private fun init(){
        var totalquiz = intent.getIntExtra("totalquiz", 0)
        var score = intent.getIntExtra("score", 0)

        totalquizText.text = "총 문제 수: " + totalquiz.toString()
        scoreText.text = "맞힌 문제 수: " + score.toString()



        goMain.setOnClickListener {
            finish()
        }

        goQuiz.setOnClickListener {
            val i = Intent(this, OptionActivity::class.java)
            startActivity(i)
            finish()
        }

    }


}
