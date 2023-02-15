package com.hds.labs.ids.hdslabs.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hds.labs.ids.hdslabs.R

class MainItemAdapter(
    private val modelArrayList: ArrayList<MainItem>,
    private val context: Context,
    private val callBack: ((model:MainItem)->Unit)
) : RecyclerView.Adapter<MainItemAdapter.GridviewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridviewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.sample_main_item, parent, false)
        return GridviewHolder(view)
    }

    override fun onBindViewHolder(holder: GridviewHolder, position: Int) {
        val model = modelArrayList[position]
        try {
            holder.itemTitle.text = model.title
            Glide.with(context).load(model.pic).into(holder.itemIcon)
            holder.itemView.setOnClickListener {
                callBack(model)
            }

        } catch (e: Exception) {
        }
    }

    override fun getItemCount(): Int {
        return modelArrayList.size
    }


    inner class GridviewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemIcon = itemView.findViewById<ImageView>(R.id.mainItemImage)
        var itemTitle = itemView.findViewById<TextView>(R.id.mainItemText)
    }

}