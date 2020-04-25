package com.example.myvocaquiz_201714286

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// RecyclerView에 표시될 View 생성
class QuizAdapter(val data:ArrayList<Data>, val correct:Data): RecyclerView.Adapter<QuizAdapter.MyViewHolder>() {

    interface onItemClickListener{
       fun onItemClick( holder:MyViewHolder, view: View, data:String, position: Int)
    }

    var itemClickListener:onItemClickListener?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // row에 해당하는 객체에 inflation되서 View로 전달. View에서는 textView에 해당하는 것을 참고
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row,parent, false)
        return MyViewHolder(v)

    }
    // 아이템의 데이터 갯수
    override fun getItemCount(): Int {
        return 4
    }

    // 뷰홀더에 해당하는 것이 전달됨.(내용만 교체할때 호출됨)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textView.text=data[position].meaning
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var textView:TextView = itemView.findViewById(R.id.textView)
        init{
            itemView.setOnClickListener {
                if(data[adapterPosition].word == correct.word){
                    itemClickListener?.onItemClick(this, it, "true", adapterPosition)
                }
                else{
                    itemClickListener?.onItemClick(this, it, "false",adapterPosition)
                }


            }
        }
    }

}