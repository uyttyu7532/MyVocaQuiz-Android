package com.example.myvocaquiz_201714286

import VocListAdapter
import VocListAdapter.MyViewHolder
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kotlinx.android.synthetic.main.activity_voc_list.*
import java.io.PrintStream
import java.util.*
import kotlin.collections.ArrayList

class VocListActivity : AppCompatActivity() {


    var words = mutableMapOf<String, String>()
    var array = ArrayList<String>()
    lateinit var adapter: VocListAdapter
    lateinit var tts: TextToSpeech
    var isTtsReady = false
    var switchOn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voc_list)
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

    private fun makeList() {
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = VocListAdapter(array, words, switchOn)
        adapter.itemClickListener = object : VocListAdapter.onItemClickListener {
            override fun onItemClick(
                holder: MyViewHolder,
                view: View,
                data: String,
                position: Int
            ) {
                if (isTtsReady) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        tts.speak(data, TextToSpeech.QUEUE_ADD, null, null)
                    }
                }
                if (holder.meaningView.visibility == GONE) {
                    holder.meaningView.visibility = VISIBLE
                } else {
                    holder.meaningView.visibility = GONE
                }
            }
        }
        recyclerView.adapter = adapter
    }

    @SuppressLint("WrongConstant")
    private fun init() {
        tts = TextToSpeech(this, TextToSpeech.OnInitListener {
            isTtsReady = true
            tts.language = Locale.US
        })
        readFile()
        makeList()

        meaningSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                switchOn = true
                makeList()
            } else {
                switchOn = false
                makeList()
            }
        }

        addFab.setOnClickListener {
            addDialog()
        }


        val simpleCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.DOWN or ItemTouchHelper.UP,
            ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: ViewHolder,
                target: ViewHolder
            ): Boolean {
                adapter.moveItem(viewHolder.adapterPosition, target.adapterPosition)
                return true
            }

            override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
                adapter.removeItem(viewHolder.adapterPosition)
            }

        }

        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)


    }

    fun readFileScan(scan: Scanner) {
        while (scan.hasNextLine()) {
            val word = scan.nextLine()
            val meaning = scan.nextLine()
            words[word] = meaning
            array.add(word)
        }
        scan.close()
    }

    fun readFile() {
        val scan2 = Scanner(openFileInput("out.txt"))
        readFileScan(scan2)
        val scan = Scanner(resources.openRawResource(R.raw.words2))
        readFileScan(scan)
    }

    fun addDialog() {
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.fragment_add, null)
        val builder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("단어 추가하기")
        builder.setPositiveButton("추가") { _, _ ->
            var newWord = mDialogView.findViewById<EditText>(R.id.editWord).text.toString()
            var newMeaning = mDialogView.findViewById<EditText>(R.id.editMeaning).text.toString()
            writeFile(newWord, newMeaning)
        }
//        builder.setNegativeButton("취소") { _,_->
//        }
//            mDialogView.AddBtn.setOnClickListener {
//                mAlertDialog.dismiss()
//            }
//            mDialogView.CancelAddBtn.setOnClickListener {
//                mAlertDialog.dismiss()
//            }
        val dig = builder.create()
        dig.show()

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
