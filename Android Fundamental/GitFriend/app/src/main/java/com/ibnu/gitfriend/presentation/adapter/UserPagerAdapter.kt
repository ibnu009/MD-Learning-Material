package com.ibnu.gitfriend.presentation.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ibnu.gitfriend.presentation.follower.FollowerFragment
import com.ibnu.gitfriend.presentation.following.FollowingFragment

class UserPagerAdapter(private val username: String, activity: FragmentActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        val mBundle = Bundle()

        val mFollowingFragment = FollowingFragment()
        val mFollowerFragment = FollowerFragment()

        mBundle.putString(EXTRA_USER_NAME, username)

        mFollowingFragment.arguments = mBundle
        mFollowerFragment.arguments = mBundle

        when (position) {
            0 -> fragment = mFollowingFragment
            1 -> fragment = mFollowerFragment
        }
        return fragment as Fragment
    }

    companion object {
        var EXTRA_USER_NAME = "extra_user_name"
    }
}