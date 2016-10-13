package com.silverkeytech.easyfunder.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.silverkeytech.easyfunder.R

import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import com.silverkeytech.easyfunder.Adapters.FixedTabbedLayoutAdapter
import com.silverkeytech.easyfunder.Fragments.History.AllSpendingCategories
import com.silverkeytech.easyfunder.Fragments.History.MySpendingCategories
import com.silverkeytech.easyfunder.Models.Charity

class SpendingCategories : AppCompatActivity() {

    private var toolbar: Toolbar? = null
    private var tabLayout: TabLayout? = null
    private var viewPager: ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spending_categories)
        initialize()
    }

    private fun initialize() {
        title = "Spending categories"
        toolbar = findViewById(R.id.toolbar_spending_categories) as Toolbar
        setSupportActionBar(toolbar)
        window.statusBarColor = resources.getColor(R.color.green_dark)
        viewPager = findViewById(R.id.viewpager_spending_categories) as ViewPager
        val adapter = FixedTabbedLayoutAdapter(
                supportFragmentManager,
                arrayListOf("All categories", "My categories"),
                arrayListOf(AllSpendingCategories(), MySpendingCategories()))
        viewPager!!.adapter = adapter
        tabLayout = findViewById(R.id.tabs_spending_categories) as TabLayout
        tabLayout!!.setupWithViewPager(viewPager!!)
    }
}
