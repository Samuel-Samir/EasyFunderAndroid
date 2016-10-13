package com.silverkeytech.easyfunder.Activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import android.widget.Toast
import com.silverkeytech.easyfunder.Adapters.SpendingCategoryAdapter
import com.silverkeytech.easyfunder.Models.ResponsePayload.GetTransactionTodayDetailsResponse
import com.silverkeytech.easyfunder.Models.ResponsePayload.HistoryDetailsResponse
import com.silverkeytech.easyfunder.Models.SpendingCategory
import com.silverkeytech.easyfunder.Models.Transaction
import com.silverkeytech.easyfunder.R
import com.silverkeytech.easyfunder.Utilities.Global
import com.silverkeytech.easyfunder.Utility.ApiCalls
import com.silverkeytech.easyfunder.Utility.UserData
import java.util.*

class Report : AppCompatActivity() {

    private var recyclerView : RecyclerView? = null
    private var adapter: SpendingCategoryAdapter?= null
    private var spendingCategories: ArrayList<SpendingCategory> = ArrayList<SpendingCategory>()
    private var transaction: Transaction? = null
    private var totalDonations: TextView? = null
    private var flag:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)
        initialize()
    }

    private fun initialize() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        title = "Donation report"
        flag = intent.extras.getString("flag")
        transaction = intent.extras.getSerializable("transaction") as Transaction?
        Global.transaction_id = transaction?.id!!
        recyclerView = findViewById(R.id.report_recycler_view) as RecyclerView
        totalDonations = findViewById(R.id.report_total_donations) as TextView
        setCategories()
        adapter = SpendingCategoryAdapter(this, spendingCategories, true,true)
        val layoutManager = LinearLayoutManager(this)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.itemAnimator = DefaultItemAnimator()
        recyclerView?.adapter = adapter
    }

    private fun setCategories() {
        // TODO API call Done

        var total: Double = 0.0

        if(flag.equals("1"))
        {
            var responsReport:HistoryDetailsResponse  = ApiCalls.GetReport(Global.transaction_id)

            for (i in 0..responsReport.spending_categories.size -1)
            {
                var spendingObject : SpendingCategory = SpendingCategory()

                spendingObject.name = responsReport.spending_categories.get(i)
                spendingObject.percentage = responsReport.spending_categories_percentage.get(i).toDouble()
                spendingObject.amountDonated = responsReport.amount_of_money_from_each_category.get(i)
                spendingObject.totalPaid = responsReport.amount_of_money_from_each_category.get(i)
                spendingObject.usedForDonation = true
                spendingCategories.add(spendingObject)


            }

            total = responsReport.total_donation?.toDouble()!!

        }
        else if(flag.equals("2"))
        {

            var responseTodayDetails: GetTransactionTodayDetailsResponse = ApiCalls.GetTransactionTodayDetails(UserData.loginUser.id,Global.transaction_id)

            for (i in 0..responseTodayDetails.spending_categories.size - 1)
            {
                var spendingObject : SpendingCategory = SpendingCategory()

                spendingObject.name = responseTodayDetails.spending_categories.get(i)
                spendingObject.percentage = responseTodayDetails.spending_categories_percentage.get(i)
                spendingObject.amountDonated = responseTodayDetails.amount_of_money_from_each_category.get(i)
                spendingObject.totalPaid = responseTodayDetails.amount_of_money_from_each_category.get(i)
                spendingObject.usedForDonation = true
                spendingCategories.add(spendingObject)
            }

            total = responseTodayDetails.total_donation?.toDouble()!!
        }


        ////////
//        val c1 : SpendingCategory = SpendingCategory()
//        val c2 : SpendingCategory = SpendingCategory()
//        val c3 : SpendingCategory = SpendingCategory()
//        val c4 : SpendingCategory = SpendingCategory()
//
//        c1.name = "Fast food"
//        c1.totalPaid = 313.4
//        c1.amountDonated = 19.4
//        c1.percentage = 3.1
//
//        c2.name = "Entertainment"
//        c2.totalPaid = 421.4
//        c2.amountDonated = 9.2
//        c2.percentage = 2.0
//
//        c3.name = "Clothes"
//        c3.totalPaid = 313.4
//        c3.amountDonated = 3.4
//        c3.percentage = 0.9
//
//        c4.name = "Shopping"
//        c4.totalPaid = 320.4
//        c4.amountDonated = 1.1
//        c4.percentage = 3.1
//
//
//
//
//        spendingCategories = arrayListOf(c1, c2, c3, c4)
//        val total: Double = 33.1
        totalDonations!!.text = String.format("$%.2f", total)
        if (total == 0.0){
            totalDonations!!.setTextColor(resources.getColor(R.color.red))
        }
        else {
            totalDonations!!.setTextColor(resources.getColor(R.color.green))
        }



        var str = ""

        if(spendingCategories.size != 0)
        {
            sortCategories()

            for (i in 0..spendingCategories.size - 1)
            {
                str += String.format("%s, %s, %s, %s\n",
                        spendingCategories.get(i).name,
                        spendingCategories.get(i).totalPaid.toString(),
                        spendingCategories.get(i).percentage.toString() + "%",
                        spendingCategories.get(i).amountDonated.toString()
                )
            }

        }


        Toast.makeText(this, str, Toast.LENGTH_LONG).show()
    }

    private fun sortCategories(){
        for (i in 0..spendingCategories.size - 1){
            for (j in 0..spendingCategories.size - 1){
                if (spendingCategories.get(i).amountDonated > spendingCategories.get(j).amountDonated){
                    Collections.swap(spendingCategories, i, j)
                }
            }
        }
    }
}


