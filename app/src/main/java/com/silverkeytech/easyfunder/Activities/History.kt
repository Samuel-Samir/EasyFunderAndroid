package com.silverkeytech.easyfunder.Activities

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.silverkeytech.easyfunder.Adapters.HistoryTabbedLayoutAdapter
import com.silverkeytech.easyfunder.Models.ResponsePayload.HistoryList
import com.silverkeytech.easyfunder.Models.Transaction
import com.silverkeytech.easyfunder.R
import com.silverkeytech.easyfunder.Utilities.Global
import com.silverkeytech.easyfunder.Utility.ApiCalls
import com.silverkeytech.easyfunder.Utility.UserData
import java.util.*

class History : AppCompatActivity() {

    private var toolbar: Toolbar? = null
    private var tabLayout: TabLayout? = null
    private var viewPager: ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        Global.yearsList = ArrayList()
        Global.allTransactions = ArrayList()
        Global.contextList = ArrayList()




        initialize()


        //TODO history api call Done
        for (i in 0..Global.yearsList.size - 1 )
        {
            var transactions: ArrayList<Transaction> = ArrayList()
            var historyYearList: HistoryList = ApiCalls.GetHistory(UserData.loginUser.id,Global.yearsList.get(i))

            for (i in 0..historyYearList.history.size - 1)
            {
                var historyObject: Transaction = Transaction()

                historyObject.month = historyYearList.history.get(i).month_of_donation!!.toInt()

                historyObject.amount = historyYearList.history.get(i).total_donation!!.toDouble()
                historyObject.id = historyYearList.history.get(i).id
                historyObject.is_read = historyYearList.history.get(i).is_read

                transactions.add(historyObject)

            }

            Global.allTransactions.add(transactions)
        }
    }

    private fun initialize() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        title = "Transactions History"
        toolbar = findViewById(R.id.toolbar_history) as Toolbar
        setSupportActionBar(toolbar)
        window.statusBarColor = resources.getColor(R.color.green_dark)
        viewPager = findViewById(R.id.viewpager_history) as ViewPager
        val adapter = HistoryTabbedLayoutAdapter(supportFragmentManager)
        viewPager!!.adapter = adapter
        tabLayout = findViewById(R.id.tabs_history) as TabLayout
        tabLayout!!.setupWithViewPager(viewPager!!)
    }
}
