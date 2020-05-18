package com.example.myvocaquiz_201714286

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import es.dmoral.toasty.Toasty
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
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }

        quizBtn.setOnClickListener {
            val i = Intent(this, OptionActivity::class.java)
            startActivity(i)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.infoDeveloper->{
                Toasty.normal(this, "스마트ict융합공학과 201714286 조예린").show();
            }

        }
        return super.onOptionsItemSelected(item)
    }
}
