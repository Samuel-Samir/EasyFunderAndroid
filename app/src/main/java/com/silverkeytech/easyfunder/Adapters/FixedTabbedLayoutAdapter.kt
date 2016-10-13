package com.silverkeytech.easyfunder.Adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import java.util.*

class FixedTabbedLayoutAdapter(supportFragmentManager: FragmentManager,
                    tabs: ArrayList<String>,
                    fragments: ArrayList<Fragment>
                    ) : FragmentPagerAdapter(supportFragmentManager) {
    private val tabs = tabs
    private val fragments = fragments

    override fun getItem(position: Int): Fragment? {
        return fragments[position]
    }

    override fun getCount(): Int {
        return tabs.size
    }

    override fun getPageTitle(position: Int): CharSequence {

        return tabs[position]
    }
}