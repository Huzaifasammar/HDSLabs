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
import com.hds.labs.ids.hdslabs.databinding.SampleMainItemBinding

class BottomItemAdapter(
    private val modelArrayList: ArrayList<MainItem>,
    private val context: Context,
    private val callBack: ((model:MainItem)->Unit)
) : RecyclerView.Adapter<BottomItemAdapter.GridviewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridviewHolder {
        val binding = SampleMainItemBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return GridviewHolder(binding)
    }

    override fun onBindViewHolder(holder: GridviewHolder, position: Int) {
        val model = modelArrayList[position]
        try {
            holder.binding.mainItemText.text = model.title
            Glide.with(context).load(model.pic).into(holder.binding.mainItemImage)

            holder.itemView.setOnClickListener {
                callBack(model)
            }

        } catch (e: Exception) {
        }
    }

    override fun getItemCount(): Int {
        return modelArrayList.size
    }


    inner class GridviewHolder(val binding:SampleMainItemBinding) : RecyclerView.ViewHolder(binding.root)

}