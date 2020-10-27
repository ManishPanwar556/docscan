package com.example.docscan.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.docscan.ClickInterface
import com.example.docscan.R
import com.example.docscan.database.PdfEntity
import kotlinx.android.synthetic.main.pdf_item_row.view.*


class PdfAdapter(val data:List<PdfEntity>,val clickInterface: ClickInterface):RecyclerView.Adapter<PdfAdapter.PdfViewHolder>() {
    inner class PdfViewHolder(val view: View):RecyclerView.ViewHolder(view){
        init {
            view.openbtn.setOnClickListener {
                if(adapterPosition!=RecyclerView.NO_POSITION){
                    clickInterface.onOpenClick(adapterPosition)
                }
            }
            view.Sharebtn.setOnClickListener {
                if(adapterPosition!=RecyclerView.NO_POSITION){
                    clickInterface.onShareClick(adapterPosition)
                }
            }
            view.deletebtn.setOnClickListener {
                if(adapterPosition!=RecyclerView.NO_POSITION){
                    clickInterface.onDeleteClick(adapterPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PdfViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.pdf_item_row,parent,false)
        return PdfViewHolder(view)
    }

    override fun onBindViewHolder(holder: PdfViewHolder, position: Int) {
        holder.view.filename.text=data.get(position).name
    }

    override fun getItemCount()=data.size
}