package com.ibnu.gemria.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ibnu.gemria.R
import com.ibnu.gemria.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAboutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Glide.with(this)
            .load(R.drawable.my_photo)
            .apply(RequestOptions())
            .into(binding.imgProfile)

        binding.toolBar.apply {
            title = context.getString(R.string.appbar_about_me)
            setNavigationOnClickListener {
                finish()
            }
        }
    }
}