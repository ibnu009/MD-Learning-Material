package com.ibnu.gemria.presentation

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ibnu.gemria.R
import com.ibnu.gemria.databinding.ActivityDetailBinding
import com.ibnu.gemria.utils.*

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gameName = intent.getStringExtra(EXTRA_NAME)
        val gameDesc = intent.getStringExtra(EXTRA_DESCRIPTION)
        val gameDev = intent.getStringExtra(EXTRA_DEVELOPER)
        val gameReleaseDate = intent.getStringExtra(EXTRA_RELEASE_DATE)
        val gamePoster = intent.getIntExtra(EXTRA_POSTER, 0)
        val gameBackground = intent.getIntExtra(EXTRA_BACKGROUND, 0)
        val gameCategory = intent.getStringExtra(EXTRA_CATEGORY)
        val gameRating = intent.getDoubleExtra(EXTRA_RATING, 0.0)
        val gameReviews = intent.getIntExtra(EXTRA_REVIEWS, 0)
        val gamePrice = intent.getIntExtra(EXTRA_PRICE, 0)
        val gameLink = intent.getStringExtra(EXTRA_LINK)

        binding.tvGameName.text = gameName
        binding.tvGameCategories.text = gameCategory
        binding.tvGameDesc.text = gameDesc
        binding.tvGamePrice.text = gamePrice.formatPriceThousand()
        binding.tvGameDev.text = gameDev
        binding.tvGameRating.text = gameRating.toRating()
        binding.tvGameTotalReview.text =
            getString(R.string.total_reviews, gameReviews.formatThousand())
        binding.tvReleaseDate.text = gameReleaseDate

        Glide.with(binding.root)
            .load(gamePoster)
            .apply(RequestOptions())
            .into(binding.imgGame)

        Glide.with(binding.root)
            .load(gameBackground)
            .apply(RequestOptions())
            .into(binding.imgBackground)

        binding.actionShare.setOnClickListener {
            it.popTap()
            Handler(Looper.getMainLooper()).postDelayed({
                val intentActionShare = Intent()
                intentActionShare.action = Intent.ACTION_SEND
                intentActionShare.putExtra(
                    Intent.EXTRA_TEXT,
                    "Checkout this amazing game:\n$gameLink"
                )
                intentActionShare.type = "text/plain"
                startActivity(Intent.createChooser(intentActionShare, "Share To:"))
            }, AnimationConstant.POP_ANIMATION)
        }

        binding.actionBack.setOnClickListener {
            it.popTap()
            Handler(Looper.getMainLooper()).postDelayed({
                finish()
            }, AnimationConstant.POP_ANIMATION)
        }
    }

    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_DESCRIPTION = "extra_desc"
        const val EXTRA_DEVELOPER = "extra_dev"
        const val EXTRA_RELEASE_DATE = "extra_date"
        const val EXTRA_POSTER = "extra_poster"
        const val EXTRA_BACKGROUND = "extra_bg"
        const val EXTRA_CATEGORY = "extra_category"
        const val EXTRA_RATING = "extra_rating"
        const val EXTRA_REVIEWS = "extra_reviews"
        const val EXTRA_PRICE = "extra_price"
        const val EXTRA_LINK = "extra_link"
    }
}