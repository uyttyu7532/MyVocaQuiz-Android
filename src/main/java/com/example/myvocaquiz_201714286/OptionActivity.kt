package com.example.myvocaquiz_201714286

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_option.*

class OptionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_option)


        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, ArrayList<String>())
        adapter.add("4지선다")
        adapter.add("5지선다")
        adapter.add("6지선다")
        choice_count_spinner.adapter = adapter

        val adapter2 = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, ArrayList<String>())
        adapter2.add("한글 뜻 고르기")
        adapter2.add("영어 단어 고르기")
        choice_quiz_spinner.adapter = adapter2



        startBtn.setOnClickListener {
            val i = Intent(this, QuizActivity::class.java)

            if(choice_count_spinner.selectedItem.toString().equals("4지선다")){
                i.putExtra("choice_count",4)
            }
            if(choice_count_spinner.selectedItem.toString().equals("5지선다")){
                i.putExtra("choice_count",5)
            }
            if(choice_count_spinner.selectedItem.toString().equals("6지선다")){
                i.putExtra("choice_count",6)
            }

            if(choice_quiz_spinner.selectedItem.toString().equals("한글 뜻 고르기")){
                i.putExtra("choice_quiz",0)
            }
            if(choice_quiz_spinner.selectedItem.toString().equals("영어 단어 고르기")){
                i.putExtra("choice_quiz",1)
            }

            startActivity(i)
            finish()

        }


    }




}
