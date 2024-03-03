package com.ibnu.gemria

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ibnu.gemria.adapter.CategoryAdapter
import com.ibnu.gemria.adapter.GameAdapter
import com.ibnu.gemria.data.model.Game
import com.ibnu.gemria.data.source.CategoryDataSource
import com.ibnu.gemria.data.source.GameDataSource
import com.ibnu.gemria.databinding.ActivityMainBinding
import com.ibnu.gemria.presentation.AboutActivity
import com.ibnu.gemria.presentation.DetailActivity
import com.ibnu.gemria.utils.AnimationConstant
import com.ibnu.gemria.utils.CategoryConstant
import com.ibnu.gemria.utils.popTap

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val gameAdapter: GameAdapter by lazy {
        GameAdapter{
            navigateToDetail(it)
        }
    }

    private val categoriesAdapter: CategoryAdapter by lazy {
        CategoryAdapter{
            onCategoryItemClicked(it)
        }
    }

    private var games: ArrayList<Game> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initiateAdapters()
        games.addAll(GameDataSource.listGame)

        gameAdapter.setData(games)
        categoriesAdapter.setData(CategoryDataSource.listCategory)

        binding.actionAboutMe.setOnClickListener {
            it.popTap()
            Handler(Looper.getMainLooper()).postDelayed({
                navigateToAboutMe()
            }, AnimationConstant.POP_ANIMATION)
        }
    }

    private fun initiateAdapters() {
        binding.rvQuote.apply {
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            adapter = gameAdapter
        }

        binding.rvCategories.apply {
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = categoriesAdapter
        }
    }

    private fun onCategoryItemClicked(name: String) {
        if (name == CategoryConstant.ALL) {
            gameAdapter.setData(games)
            return
        }
        val filteredGames = games.filter { it.categories.contains(name) }
        gameAdapter.setData(filteredGames)
    }

    private fun navigateToAboutMe(){
        val intent = Intent(this, AboutActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToDetail(game: Game) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_NAME, game.name)
        intent.putExtra(DetailActivity.EXTRA_DESCRIPTION, game.description)
        intent.putExtra(DetailActivity.EXTRA_DEVELOPER, game.developerName)
        intent.putExtra(DetailActivity.EXTRA_RELEASE_DATE, game.releaseDate)
        intent.putExtra(DetailActivity.EXTRA_POSTER, game.posterImage)
        intent.putExtra(DetailActivity.EXTRA_BACKGROUND, game.backgroundImage)
        intent.putExtra(DetailActivity.EXTRA_CATEGORY, game.categories)
        intent.putExtra(DetailActivity.EXTRA_RATING, game.rating)
        intent.putExtra(DetailActivity.EXTRA_REVIEWS, game.totalReviews)
        intent.putExtra(DetailActivity.EXTRA_PRICE, game.price)
        intent.putExtra(DetailActivity.EXTRA_LINK, game.link)

        startActivity(intent)
    }

}