package com.dicoding.asclepius.view.adapter

import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.asclepius.data.model.History
import com.dicoding.asclepius.databinding.ItemHistoryBinding
import com.dicoding.asclepius.utils.setImage

class HistoryAdapter(private val onClick: (History) -> Unit) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    private var listHistory = ArrayList<History>()

    fun setData(listHistory: List<History>) {
        this.listHistory.clear()
        this.listHistory.addAll(listHistory)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val History = listHistory[position]
        holder.bind(History)

    }

    override fun getItemCount(): Int = listHistory.size

    inner class HistoryViewHolder(private val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(history: History) {
            binding.root.setOnClickListener {
                onClick(history)
            }
            val imageBytes = Base64.decode(history.imageBase64, Base64.DEFAULT)
            binding.ivHistory.setImage(imageBytes)
            binding.tvHistoryLabel.text = history.label
            binding.tvConfidenceScore.text = history.confidence
        }
    }
}
