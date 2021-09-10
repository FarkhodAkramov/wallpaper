package com.example.wallpaperappflow.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.wallpaperappflow.PagerFragment

class PagerAdapter(fragmentActivity: FragmentActivity,val list: ArrayList<String>) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = list.size

    override fun createFragment(position: Int): Fragment {
        return PagerFragment.newInstance(list[position])
    }


}