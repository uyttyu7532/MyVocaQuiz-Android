package com.example.myvocaquiz_201714286

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_web.*

class MainActivity : AppCompatActivity() {

    val tabArray = arrayListOf<String>("단어장","단어 검색")


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

    }


    override fun onBackPressed(){
        if(viewPager.currentItem == 1){
            if (webView.canGoBack()) { // 웹뷰가 이전 페이지로 갈 수 있다면
                webView.goBack()
            } else {
                super.onBackPressed()
            }
        }else{
            super.onBackPressed()
        }

    }





}
