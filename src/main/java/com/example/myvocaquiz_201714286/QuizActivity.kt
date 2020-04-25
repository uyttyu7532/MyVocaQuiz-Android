package com.example.myvocaquiz_201714286

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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        readFile()
        init(0)
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

        tts = TextToSpeech(this, TextToSpeech.OnInitListener {
            isTtsReady = true
            tts.language = Locale.US
        })

        quizText.text = words[pos].word
        var tmpList= ArrayList<Data>()
        tmpList.add(words[pos])
        while(tmpList.size < 4){
            var tmpNum = Random().nextInt(words.size)
            if(!tmpList.contains(words[tmpNum])) {
                tmpList.add(words[tmpNum])
            }
        }
        tmpList.shuffle()

        adapter = QuizAdapter(tmpList,words[pos])
        RecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
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
                    init(pos+1)
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


    fun readFile(){
        val scan = Scanner(resources.openRawResource(R.raw.words))
        while(scan.hasNextLine()){
            val word = scan.nextLine()
            val meaning = scan.nextLine()

            var tmp = Data(word,meaning)
            words.add(tmp)

        }

        words.shuffle()
    }
}
