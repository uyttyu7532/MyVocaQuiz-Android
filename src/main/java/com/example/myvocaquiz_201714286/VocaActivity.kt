package com.example.myvocaquiz_201714286

import VocaAdapter
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_voca.*
import java.util.*
import kotlin.collections.ArrayList

class VocaActivity : AppCompatActivity() {


    var words = mutableMapOf<String, String>()
    var array = ArrayList<String>()
    lateinit var adapter: VocaAdapter
    lateinit var tts:TextToSpeech
    var isTtsReady = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voca)
        init()
    }

    override fun onStop() {
        super.onStop()
        tts.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        tts.shutdown()
    }

    @SuppressLint("WrongConstant")
    private fun init() {
        tts = TextToSpeech(this, TextToSpeech.OnInitListener {
            isTtsReady = true
            tts.language = Locale.KOREA
        })
        readFile()
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        adapter = VocaAdapter(array,words)
        adapter.itemClickListener = object:VocaAdapter.onItemClickListener{
            override fun onItemClick(holder: VocaAdapter.MyViewHolder, view: View, data: String, position: Int) {
                if(isTtsReady){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        tts.speak(data, TextToSpeech.QUEUE_ADD, null,null)
                    }
                }


                if(holder.meaningView.visibility==GONE){
                    holder.meaningView.visibility = VISIBLE
                }else{
                    holder.meaningView.visibility = GONE
                }
                Toast.makeText(applicationContext,words[data],Toast.LENGTH_SHORT).show()
            }

        }
        recyclerView.adapter = adapter
        val simpleCallback = object:ItemTouchHelper.SimpleCallback(ItemTouchHelper.DOWN or ItemTouchHelper.UP, ItemTouchHelper.RIGHT){
            override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
            ): Boolean {
                adapter.moveItem(viewHolder.adapterPosition, target.adapterPosition)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                adapter.removeItem(viewHolder.adapterPosition)
            }

        }

        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

    }

    fun readFileScan(scan: Scanner){
        while(scan.hasNextLine()){
            val word = scan.nextLine()
            val meaning = scan.nextLine()
            words[word] = meaning
            array.add(word)
        }
        scan.close()
    }

    fun readFile(){
        val scan2 = Scanner(openFileInput("out.txt"))
        readFileScan(scan2)
        val scan = Scanner(resources.openRawResource(R.raw.words))
        readFileScan(scan)
    }
}
