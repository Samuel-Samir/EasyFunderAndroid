package com.silverkeytech.easyfunder.Adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.silverkeytech.easyfunder.Fragments.History.Year
import com.silverkeytech.easyfunder.Models.ResponsePayload.DonationYearsResponse
import com.silverkeytech.easyfunder.Utilities.Global
import com.silverkeytech.easyfunder.Utility.ApiCalls
import com.silverkeytech.easyfunder.Utility.UserData
import java.util.*

class HistoryTabbedLayoutAdapter(supportFragmentManager: FragmentManager) :
        FragmentPagerAdapter(supportFragmentManager) {

    private var fragments: ArrayList<Fragment> = ArrayList()
    private var tabs: ArrayList<String> = ArrayList()
    private var currentYear: Int = Calendar.getInstance().get(Calendar.YEAR)
    private var yearsPassedCount: Int = 1

    init {
        // TODO API call to get number of years Done

        var yearsObject:DonationYearsResponse  = ApiCalls.GetDonationsYears(UserData.loginUser.id)

        if(yearsObject.years.size != 0)
        {
            var max:Int = 0
            for (i in 0..yearsObject.years.size - 1)
            {
                if (yearsObject.years.get(max) < yearsObject.years.get(i))
                {
                    max = i;
                }
            }
            currentYear = yearsObject.years.get(max).toInt()
            yearsPassedCount = yearsObject.years.size
            setArrayLists()
        }

    }

    private fun setArrayLists(){
        for (i in currentYear downTo currentYear - yearsPassedCount + 1){
            tabs.add(i.toString())
            val fragment = Year()
            fragment.year = i
            fragment.position = tabs.size - 1
            Global.yearsList.add(i.toString())
            fragments.add(fragment)
        }
    }

    override fun getItem(position: Int): Fragment? {
//        var y:Year = Year()
//        y = fragments.get(position) as Year
//        Global.yearSelected = y.year.toString()
        return fragments[position]
    }

    override fun getCount(): Int {
        return tabs.size
    }

    override fun getPageTitle(position: Int): CharSequence {

        var s:String=""
        return tabs[position]
    }
}