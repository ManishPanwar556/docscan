package com.example.docscan.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.docscan.clickFunctionality.OnImageClickInterface
import com.example.docscan.R
import com.example.docscan.database.DocsEntity
import kotlinx.android.synthetic.main.item_row.view.*

class MyAdapter (val data:List<DocsEntity>,val onImageClickInterface: OnImageClickInterface):RecyclerView.Adapter<MyAdapter.MyViewHolder>(){


    inner class MyViewHolder(val view: View):RecyclerView.ViewHolder(view){
        init {
            view.delete.setOnClickListener {
                if(adapterPosition!=RecyclerView.NO_POSITION) {
                    onImageClickInterface.onImageClick(adapterPosition,data.get(adapterPosition))
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_row,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.view.count.text=(position+1).toString()
       Glide.with(holder.view).load(data.get(position).uri).into(holder.view.image)
    }

    override fun getItemCount()=data.size

}