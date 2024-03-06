package com.dicoding.asclepius.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.asclepius.data.model.Article
import com.dicoding.asclepius.databinding.ItemArticleBinding
import com.dicoding.asclepius.utils.setImage

class ArticleAdapter(private val onClick: (String) -> Unit) :
    RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    private var listArticle = ArrayList<Article>()

    fun setData(listArticle: List<Article>) {
        this.listArticle.clear()
        this.listArticle.addAll(listArticle)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val Article = listArticle[position]
        holder.bind(Article)

    }

    override fun getItemCount(): Int = listArticle.size

    inner class ArticleViewHolder(private val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            binding.root.setOnClickListener {
                onClick(article.url)
            }

            binding.ivArticle.setImage(article.urlToImage)
            binding.tvArticleTitle.text = article.title
            binding.tvAuthor.text = article.source.name
        }
    }
}
