package com.ibnu.gemria.adapter

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ibnu.gemria.data.model.Category
import com.ibnu.gemria.R
import com.ibnu.gemria.databinding.ItemCategoryBinding
import com.ibnu.gemria.utils.AnimationConstant
import com.ibnu.gemria.utils.popTap

class CategoryAdapter(private val onClick: (String) -> Unit) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private val listCategory = ArrayList<Category>()
    private var selectedPos = 0

    fun setData(listCategory: List<Category>) {
        this.listCategory.addAll(listCategory)
        notifyItemRangeInserted(0, listCategory.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = listCategory[position]
        holder.bind(category, position)
        holder.itemView.setOnClickListener {
            it.popTap()
            notifyItemChanged(selectedPos)
            selectedPos = holder.layoutPosition
            notifyItemChanged(selectedPos)
            Handler(Looper.getMainLooper()).postDelayed({
                onClick(category.name)
            }, AnimationConstant.POP_ANIMATION)
        }
    }

    override fun getItemCount(): Int = listCategory.size

    inner class CategoryViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category, position: Int) {
            if (selectedPos == position){
                binding.tvCategoryName.setTextColor(ContextCompat.getColor(binding.root.context, R.color.white))
            } else {
                binding.tvCategoryName.setTextColor(ContextCompat.getColor(binding.root.context, R.color.blue))
            }
            binding.tvCategoryName.text = category.name
        }
    }
}