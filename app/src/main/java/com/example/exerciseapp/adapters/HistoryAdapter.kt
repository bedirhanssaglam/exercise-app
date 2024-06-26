package com.example.exerciseapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.exerciseapp.R
import com.example.exerciseapp.databinding.ItemHistoryRowBinding

class HistoryAdapter(private val items: ArrayList<String>) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    inner class ViewHolder(binding: ItemHistoryRowBinding) : RecyclerView.ViewHolder(binding.root) {
        val main = binding.historyItemMain
        val itemNumber = binding.itemNumberText
        val itemDate = binding.dateText
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemHistoryRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
    )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val date: String = items[position]
        holder.apply {
            itemNumber.text = (position + 1).toString()
            itemDate.text = date
            main.setBackgroundColor(
                ContextCompat.getColor(
                    itemView.context,
                    if (position % 2 == 0) R.color.grey else R.color.white
                )
            )
        }
    }
}