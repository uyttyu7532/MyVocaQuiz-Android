package com.example.myvocaquiz_201714286

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_add.*


class AddFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val clearEditWord: ImageView = view?.findViewById(R.id.clearEditWord) as ImageView
        val clearEditMeaning: ImageView = view?.findViewById(R.id.clearEditMeaning) as ImageView

        Log.d("성공","onActivityCreated실행")

        clearEditWord.setOnClickListener {
            Log.d("성공","클릭성공1")
            editWord.text=null
            editWord.setText("")
            Toast.makeText(context, "성공", Toast.LENGTH_SHORT).show()
        }


        clearEditMeaning.setOnClickListener {
            Log.d("성공","클릭성공2")
            editMeaning.text=null
            editMeaning.setText("")
        }
    }
}
