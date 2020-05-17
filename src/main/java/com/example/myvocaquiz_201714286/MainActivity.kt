package com.example.myvocaquiz_201714286

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val tabArray = arrayListOf<String>("기본단어장","내 단어장")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init(){
        viewPager.adapter = MyFragStateAdapter(this)
        TabLayoutMediator(tabLayout, viewPager){
                tab, position ->
            tab.text = tabArray[position]
            // tab.icon
        }.attach()

//        addFab.setOnClickListener {
//            addDialog()
//        }
    }



}
