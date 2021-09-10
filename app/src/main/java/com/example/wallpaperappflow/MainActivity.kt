                          package com.example.wallpaperappflow

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import com.example.wallpaperappflow.adapters.PagerAdapter
import com.example.wallpaperappflow.databinding.ActivityMainBinding
import com.example.wallpaperappflow.databinding.ItemTabBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var adapter: PagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setTitle("Home")
        this.getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val list = ArrayList<String>()
        list.add("All")
        list.add("chelsea")
        list.add("football")
        list.add("Real madrid")
        list.add("cars")
        list.add("applications")
        list.add("foods")
        list.add("profession")
        list.add("plants")

        list.add("movies")
        adapter = PagerAdapter(this@MainActivity, list)
        binding.viewpager.adapter = adapter
        TabLayoutMediator(binding.tablayout, binding.viewpager) { tab, position ->
            tab.text = list[position]
            binding.viewpager.setCurrentItem(tab.position, true)
        }.attach()

        val count: Int = binding.tablayout.getTabCount()
        for (i in 0 until count) {
            val tabView = ItemTabBinding.inflate(layoutInflater)
            tabView.tabTitle.setText(list.get(i))
            if (i == 0) {
                tabView.tabIcon.visibility = View.VISIBLE
                tabView.tabTitle.setTextColor(Color.parseColor("#ffffff"))
            }
            binding.tablayout.getTabAt(i)?.setCustomView(tabView.root)
        }
        binding.tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val tabView = tab.customView
                val imageView = tabView!!.findViewById<ImageView>(R.id.tab_icon)
                val textView = tabView!!.findViewById<TextView>(R.id.tab_title)
                imageView.visibility = View.VISIBLE
                textView.setTextColor(Color.parseColor("#ffffff"))
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                val tabView = tab.customView
                val imageView = tabView!!.findViewById<ImageView>(R.id.tab_icon)
                val textView = tabView!!.findViewById<TextView>(R.id.tab_title)
                imageView.visibility = View.INVISIBLE
                textView.setTextColor(Color.parseColor("#858585"))
            }


            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

    }
}