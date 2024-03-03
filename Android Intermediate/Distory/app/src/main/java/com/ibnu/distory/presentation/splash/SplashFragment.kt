package com.ibnu.distory.presentation.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ibnu.distory.R
import com.ibnu.distory.base.BaseFragment
import com.ibnu.distory.databinding.FragmentSplashBinding
import com.ibnu.distory.utils.PreferenceManager
import com.ibnu.distory.utils.constant.AnimationConstants.SPLASH_ANIMATION
import org.koin.android.ext.android.inject

class SplashFragment : BaseFragment<FragmentSplashBinding>() {

    private val pref: PreferenceManager by inject()

    private fun initLoading() {
        Handler(Looper.getMainLooper()).postDelayed({
            if (pref.isLogin) {
                findNavController().navigate(R.id.action_splashFragment_to_homeFragment2)
            } else {
                findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
            }
        }, SPLASH_ANIMATION)
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