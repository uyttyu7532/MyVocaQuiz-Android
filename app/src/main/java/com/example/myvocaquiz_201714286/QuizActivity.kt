package com.example.myvocaquiz_201714286

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.speech.tts.TextToSpeech
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_quiz.*
import java.util.*
import kotlin.collections.ArrayList




class QuizActivity : AppCompatActivity() {
    var words = ArrayList<Data>()
    lateinit var adapter: QuizAdapter
    var score = 0
    var totalquiz = 0
    lateinit var tts:TextToSpeech
    var isTtsReady = false

    lateinit var myPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        myPref = this.getSharedPreferences("myPref", Context.MODE_PRIVATE)
        loadAllData(myPref)

        init(0)
    }

    override fun onPause() {
        super.onPause()
        val i = Intent(this, FinishQuizActivity::class.java)
        i.putExtra("totalquiz", totalquiz)
        i.putExtra("score", score)
        startActivity(i)
        finish()
    }

    override fun onStop() {
        super.onStop()
        tts.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        tts.shutdown()
    }

    private fun init(pos:Int) {

        var choice_count = intent.getIntExtra("choice_count", 1)
        var choice_quiz = intent.getIntExtra("choice_quiz", 0)

        tts = TextToSpeech(this, TextToSpeech.OnInitListener {
            isTtsReady = true
            tts.language = Locale.US
        })


        if(choice_quiz == 1)
            quiz_english(pos, choice_count,choice_quiz)
        else
            quiz_korean(pos, choice_count,choice_quiz)





        RecyclerView.layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        RecyclerView.adapter = adapter


        val animation = AnimationUtils.loadAnimation(this, R.anim.animation)

        adapter.itemClickListener = object:QuizAdapter.onItemClickListener{
            override fun onItemClick(holder: QuizAdapter.MyViewHolder, view: View, data: String, position: Int) {

                if(data.equals("true")){ // 맞았을 경우
                    score++
                    correct.startAnimation(animation)
                }
                else{
                    wrong.startAnimation(animation)
                }
                totalquiz++
                scoreText.text="정답 수 / 푼 문제 수\n $score / $totalquiz"

                Handler().postDelayed({
                    if(pos<words.size-1) {
                        init(pos + 1)
                    }
                    else{
                        finish()
                    }
                }, 500)

            }

        }

        speakerImg.setOnClickListener {
            if(isTtsReady){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    tts.speak(quizText.text, TextToSpeech.QUEUE_ADD, null,null)
                }
            }
        }
    }



    private fun loadAllData(pref: SharedPreferences) {
        val prefKeys: MutableSet<String> = pref.all.keys
        for (pref_key in prefKeys) {
            var tmp = Data(pref_key, pref.getString(pref_key, "null"))
            words.add(tmp)
//            array.add(pref_key)
        }
        words.shuffle()
    }

    fun quiz_english(pos: Int, choice_count:Int, choice_quiz:Int){ // 영어 보기
        quizText.text = words[pos].meaning
        var tmpList= ArrayList<String>()
        tmpList.add(words[pos].word)
        while(tmpList.size < choice_count){
            var tmpNum = Random().nextInt(words.size)
            if(!tmpList.contains(words[tmpNum].word)) {
                tmpList.add(words[tmpNum].word)
            }
        }
        tmpList.shuffle()
        adapter = QuizAdapter(tmpList,words[pos],choice_count,choice_quiz)
    }

    fun quiz_korean(pos: Int, choice_count:Int, choice_quiz:Int){ // 한글 보기
        quizText.text = words[pos].word

        var tmpList= ArrayList<String>()
        tmpList.add(words[pos].meaning)
        while(tmpList.size < choice_count){
            var tmpNum = Random().nextInt(words.size)

            if(!tmpList.contains(words[tmpNum].meaning)) {
                tmpList.add(words[tmpNum].meaning)
            }
        }
        tmpList.shuffle()
        adapter = QuizAdapter(tmpList,words[pos],choice_count,choice_quiz)
    }

}