package com.silverkeytech.easyfunder.Activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.silverkeytech.easyfunder.Adapters.ViewPagerAdapter
import com.silverkeytech.easyfunder.R
import com.silverkeytech.easyfunder.Utilities.SharedPrefrencesManager
import java.util.*

class IntroSlider : AppCompatActivity() {

    private var viewPager: ViewPager? = null
    private var myViewPagerAdapter: ViewPagerAdapter? = null
    private var dotsLayout: LinearLayout? = null
    private var dots: ArrayList<TextView> = ArrayList<TextView>()
    private var layouts: IntArray? = null
    private var btnSkip: Button? = null
    private var btnNext: Button? = null
    private var prefManager: SharedPrefrencesManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro_slider)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        prefManager = SharedPrefrencesManager(this)
        val firstTime = prefManager!!.isFirstTimeLaunch
        if (!firstTime) {
            launchHomeScreen()
            finish()
        }
        if (Build.VERSION.SDK_INT >= 21) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }

        setContentView(R.layout.activity_intro_slider)

        viewPager = findViewById(R.id.view_pager) as ViewPager?
        dotsLayout = findViewById(R.id.layoutDots) as LinearLayout?
        btnSkip = findViewById(R.id.btn_skip) as Button?
        btnNext = findViewById(R.id.btn_next) as Button?


        // layouts of all welcome sliders
        // add few more layouts if you want
        layouts = intArrayOf(
                R.layout.slider_screen1,
                R.layout.slider_screen2,
                R.layout.slider_screen3)

        addBottomDots(0)

        myViewPagerAdapter = ViewPagerAdapter(this@IntroSlider, layouts!!)
        viewPager!!.adapter = myViewPagerAdapter
        viewPager!!.addOnPageChangeListener(viewPagerPageChangeListener)

        btnSkip!!.setOnClickListener { launchHomeScreen() }

        btnNext!!.setOnClickListener {
            // checking for last page
            // if last page home screen will be launched
            val current = getItem(+1)
            if (current < layouts!!.size) {
                viewPager!!.currentItem = current
            } else {
                launchHomeScreen()
            }
        }
    }

    private fun launchHomeScreen() {
        prefManager!!.isFirstTimeLaunch = false
        startActivity(Intent(this@IntroSlider, SignIn::class.java))
        finish()
    }

    private fun addBottomDots(currentPage: Int) {
        dots = ArrayList<TextView>(layouts!!.size)

        dotsLayout!!.removeAllViews()
        for (i in dots.indices) {
            dots[i] = TextView(this)
            dots[i].text = Html.fromHtml("&#8226;")
            dots[i].textSize = 35f
            dots[i].setTextColor(resources.getColor(R.color.concrete))
            dotsLayout!!.addView(dots[i])
        }

        if (dots.size > 0)
            dots[currentPage].setTextColor(resources.getColor(R.color.clouds_white))
    }

    private fun getItem(i: Int): Int {
        return viewPager!!.currentItem + i
    }

    internal var viewPagerPageChangeListener: ViewPager.OnPageChangeListener = object : ViewPager.OnPageChangeListener {

        override fun onPageSelected(position: Int) {
            addBottomDots(position)
            if (position == layouts!!.size - 1) {
                btnNext!!.text = getString(R.string.got_it)
                btnSkip!!.visibility = View.GONE
            } else {
                btnNext!!.text = getString(R.string.next)
                btnSkip!!.visibility = View.VISIBLE
            }
        }

        override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {

        }

        override fun onPageScrollStateChanged(arg0: Int) {

        }
    }

}