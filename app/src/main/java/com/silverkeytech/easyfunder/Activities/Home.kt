package com.silverkeytech.easyfunder.Activities


import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.silverkeytech.easyfunder.Adapters.FixedTabbedLayoutAdapter
import com.silverkeytech.easyfunder.Fragments.Home.Charities
import com.silverkeytech.easyfunder.Fragments.Home.Dashboard
import com.silverkeytech.easyfunder.Fragments.Home.Profile
import com.silverkeytech.easyfunder.Fragments.Time.DatePickerFragment
import com.silverkeytech.easyfunder.Models.ResponsePayload.NextDonation
import com.silverkeytech.easyfunder.Models.ResponsePayload.TimeNow
import com.silverkeytech.easyfunder.R
import com.silverkeytech.easyfunder.Utility.ApiCalls
import com.silverkeytech.easyfunder.Utility.UserData

class Home : AppCompatActivity() {

    private var toolbar: Toolbar? = null
    private var tabLayout: TabLayout? = null
    private var viewPager: ViewPager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        if(UserData.loginUser.has_bank_account == true &&
            UserData.loginUser.has_charities_to_donation == true &&
             UserData.loginUser.has_spending_categories == true)
        {
            ApiCalls.SetNextDonationFirstTime(UserData.loginUser.id)
        }

        var next:NextDonation = ApiCalls.GetNextDonationTime(UserData.loginUser.id)
        var now: TimeNow = ApiCalls.GetTime(UserData.loginUser.id)

        if(next.user_id  == null || next.user_id?.length == 0)
        {

        }
        else
        {
            var nextMonth:Int = next.month!!.toInt()
            var nextYear:Int = next.year!!.toInt()
            var nextDay :Int = next.day!!.toInt()

            var nowMonth:Int = now.month!!.toInt()
            var nowYear:Int = now.year!!.toInt()
            var nowtDay :Int = now.day!!.toInt()


//            if(nextYear == nowYear)
//            {
//                if(nowMonth >= nextMonth )
//                {
//                    if (nowMonth == nextMonth&&nowtDay>=25  ) {
//                        ApiCalls.MakeTransaction(UserData.loginUser.id)
//                        ApiCalls.SetNextDonationTime(UserData.loginUser.id)
//                    }
//
//                    else if(nowMonth > nextMonth )
//                    {
//                        ApiCalls.MakeTransaction(UserData.loginUser.id)
//                        ApiCalls.SetNextDonationTime(UserData.loginUser.id)
//                    }
//                }
//
//            }
//            else if(nowYear > nextYear)
//            {
//                ApiCalls.MakeTransaction(UserData.loginUser.id)
//                ApiCalls.SetNextDonationTime(UserData.loginUser.id)
//            }
//

        }





        initialize()
    }

    private fun initialize() {
        toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        window.statusBarColor = resources.getColor(R.color.green_dark)
        viewPager = findViewById(R.id.viewpager) as ViewPager
        val adapter = FixedTabbedLayoutAdapter(
                supportFragmentManager,
                arrayListOf("Dashboard", "Charities", "Profile"),
                arrayListOf(Dashboard(), Charities(), Profile()))
        viewPager!!.adapter = adapter
        tabLayout = findViewById(R.id.tabs) as TabLayout
        tabLayout!!.setupWithViewPager(viewPager!!)
        tabLayout?.getTabAt(0)?.icon = resources.getDrawable(R.drawable.ic_dashboard_white_18dp)
        tabLayout?.getTabAt(1)?.icon = resources.getDrawable(R.drawable.ic_business_white_18dp)
        tabLayout?.getTabAt(2)?.icon = resources.getDrawable(R.drawable.ic_face_white_18dp)
        if (UserData.loginUser.has_charities_to_donation == false){
            tabLayout?.getTabAt(1)?.select()
        }
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
         menuInflater.inflate(R.menu.menu_home, menu)



        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        var id:Int = item!!.itemId
        if(id == R.id.notifications_badge)
        {
            this@Home.startActivity(Intent(this@Home, History::class.java))
        }
        else if(id == R.id.time_menu)
        {
            showDatePickerDialog(findViewById(R.id.time_menu)!!)
        }



        return super.onOptionsItemSelected(item)
    }

    fun showDatePickerDialog(v: View) {
        val newFragment = DatePickerFragment()
        newFragment.show(supportFragmentManager, "datePicker")
    }

}



