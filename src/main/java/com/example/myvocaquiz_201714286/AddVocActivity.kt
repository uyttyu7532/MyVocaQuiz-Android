package com.example.myvocaquiz_201714286

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_voc.*
import kotlinx.android.synthetic.main.activity_intro.addBtn
import java.io.PrintStream


class AddVocActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_voc)
        init()
    }

    private fun init(){
        addBtn.setOnClickListener {
            val word = editWord.text.toString()
            val meaning = editMeaning.text.toString()
            writeFile(word, meaning)
        }

        cancelBtn.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }

    private fun writeFile(word: String, meaning: String) {
        val output = PrintStream(openFileOutput("out.txt", Context.MODE_APPEND))
        output.println(word)
        output.println(meaning)
        output.close()
        val i = Intent()
        i.putExtra("voc", Data(word, meaning))
        setResult(Activity.RESULT_OK, i)
        finish()
    }
}


