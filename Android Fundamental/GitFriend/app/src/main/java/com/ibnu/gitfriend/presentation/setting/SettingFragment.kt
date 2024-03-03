package com.ibnu.gitfriend.presentation.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import com.ibnu.gitfriend.R
import com.ibnu.gitfriend.base.BaseFragment
import com.ibnu.gitfriend.databinding.FragmentSettingBinding
import org.koin.android.ext.android.inject

class SettingFragment : BaseFragment<FragmentSettingBinding>() {
    private val viewModel: SettingViewModel by inject()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentSettingBinding {
        return FragmentSettingBinding.inflate(inflater, container, false)
    }

    override fun initUI() {
        binding.toolBar.apply {
            title = context.getString(R.string.title_setting_page)
            setNavigationOnClickListener {
                it.findNavController().popBackStack()
            }
        }
    }

    override fun initProcess() {
    }

    override fun initObservers() {
        viewModel.getThemeSettings().observe(this
        ) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.switchTheme.isChecked = false
            }
        }

        binding.switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            viewModel.saveThemeSetting(isChecked)
        }
    }
}