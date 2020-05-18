package com.example.myvocaquiz_201714286

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_web.view.*

/**
 * A simple [Fragment] subclass.
 */
class WebFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_web, container, false)
        val webView = view.webView

        webView.apply{
            settings.javaScriptEnabled = true // 자바스크립트 기능 동작
            webViewClient = WebViewClient() // 이것을 지정하지 않으면 웹뷰가 아니라 자체 웹 브라우저가 동작
        }

        webView.loadUrl("https://dic.daum.net/") // 웹뷰에 페이지 로딩

    return view
}
//
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        // 인자 : 반응한 뷰, 액션id, 이벤트
//        searchWordText.setOnEditorActionListener{
//                _, actionId, _ ->
//            if (actionId == EditorInfo.IME_ACTION_SEARCH) { // 검색 버튼에 해당하는 상수를 확인해 검색버튼이 눌렀는지 확인
//                webView.loadUrl("https://dic.daum.net/search.do?q="+searchWordText.text.toString()) // 주소를 웹뷰에 전달하여 로딩
//                true // 이벤트 종료
//            } else {
//                false
//            }
//        }
//        registerForContextMenu(webView)
//    }
}



