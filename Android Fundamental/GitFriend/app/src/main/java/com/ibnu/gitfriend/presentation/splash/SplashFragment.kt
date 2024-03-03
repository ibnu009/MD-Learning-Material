package com.ibnu.gitfriend.presentation.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ibnu.gitfriend.R
import com.ibnu.gitfriend.base.BaseFragment
import com.ibnu.gitfriend.databinding.FragmentSplashBinding
import com.ibnu.gitfriend.utils.AnimationConstant

class SplashFragment : BaseFragment<FragmentSplashBinding>() {

    private fun initLoading() {
        Handler(Looper.getMainLooper()).postDelayed({
            lifecycleScope.launchWhenResumed {
                findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
            }
        }, AnimationConstant.SPLASH_ANIMATION)
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentSplashBinding {
        return FragmentSplashBinding.inflate(inflater, container, false)
    }

    override fun initUI() {}

    override fun initProcess() {
        initLoading()
    }

    override fun initObservers() {}
}