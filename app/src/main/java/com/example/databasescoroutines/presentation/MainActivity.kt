package com.example.databasescoroutines.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.databasescoroutines.R
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.pager)
        val pagerAdapter = ViewPagerAdapter(this)
        viewPager.adapter = pagerAdapter

        TabLayoutMediator(tab, viewPager) { tab, position ->
            tab.text = "TAB ${(position + 1)}"
        }.attach()
    }

    private class ViewPagerAdapter(activity: FragmentActivity)
        : FragmentStateAdapter(activity) {

        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            return if (position == 0) {
                RegistrationFragment()
            } else {
                RoomFragment()
            }
        }
    }
}
