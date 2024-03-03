package com.ibnu.gitfriend.presentation.adapter

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ibnu.gitfriend.R
import com.ibnu.gitfriend.data.model.User
import com.ibnu.gitfriend.databinding.ItemUserBinding
import com.ibnu.gitfriend.utils.AnimationConstant
import com.ibnu.gitfriend.utils.popTap

class UserFavoriteAdapter(
    private val onClick: (String) -> Unit, private val onFavoriteClick: (Boolean, Int) -> Unit
) : RecyclerView.Adapter<UserFavoriteAdapter.UserViewHolder>() {

    private var listUser = ArrayList<User>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(listUser: List<User>) {
        this.listUser.clear()
        this.listUser.addAll(listUser)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = listUser[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int = listUser.size

    inner class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            var isUserFavorite = user.isFavorite
            binding.root.setOnClickListener {
                it.popTap()
                Handler(Looper.getMainLooper()).postDelayed({
                    onClick(user.username ?: "")
                }, AnimationConstant.POP_ANIMATION)
            }

            binding.btnFavorite.setOnClickListener {
                it.popTap()
                isUserFavorite = !isUserFavorite
                user.isFavorite = isUserFavorite
                listUser.removeAt(absoluteAdapterPosition)
                notifyItemRemoved(absoluteAdapterPosition)

                Handler(Looper.getMainLooper()).postDelayed({
                    user.id?.let { userId -> onFavoriteClick(isUserFavorite, userId) }
                }, AnimationConstant.POP_ANIMATION)
            }

            Glide.with(binding.root).load(user.avatarUrl).apply(RequestOptions())
                .into(binding.imgUser)

            binding.btnFavorite.setImageResource(
                if (isUserFavorite) R.drawable.ic_favorite_filled
                else R.drawable.ic_favorite_border
            )

            binding.tvUserName.text = user.username
        }
    }


}