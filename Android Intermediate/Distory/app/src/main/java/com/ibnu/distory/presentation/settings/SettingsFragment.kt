package com.ibnu.distory.presentation.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.ibnu.distory.R
import com.ibnu.distory.base.BaseFragment
import com.ibnu.distory.databinding.FragmentSettingsBinding
import com.ibnu.distory.utils.helper.popClick
import android.provider.Settings
import androidx.navigation.fragment.findNavController
import com.ibnu.distory.utils.PreferenceManager
import com.ibnu.distory.utils.helper.showYesNoDialog
import org.koin.android.ext.android.inject

class SettingsFragment : BaseFragment<FragmentSettingsBinding>() {

    private val pref: PreferenceManager by inject()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentSettingsBinding {
        return FragmentSettingsBinding.inflate(inflater, container, false)
    }

    override fun initUI() {
        binding.toolBar.apply {
            title = getString(R.string.title_settings)
            setNavigationOnClickListener {
                it.findNavController().popBackStack()
            }
        }
    }

    override fun initActions() {
        binding.apply {
            btnChangeLanguange.popClick {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            }

            btnLogout.popClick {
                showYesNoDialog(
                    title = getString(R.string.title_logout),
                    message = getString(R.string.message_logout),
                    onYes = {
                        pref.clearAllPreferences()
                        findNavController().navigate(R.id.action_settingsFragment_to_loginFragment)
                    }
                )
            }
        }
    }

    override fun initProcess() {
    }

    override fun initObservers() {
    }


}