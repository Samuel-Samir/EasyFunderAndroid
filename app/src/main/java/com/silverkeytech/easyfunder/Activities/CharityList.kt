package com.silverkeytech.easyfunder.Activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.silverkeytech.easyfunder.Adapters.CharityAdapter
import com.silverkeytech.easyfunder.Models.Charity
import com.silverkeytech.easyfunder.Models.ResponsePayload.CharityInfo
import com.silverkeytech.easyfunder.Models.ResponsePayload.DonationCharitiesResponse
import com.silverkeytech.easyfunder.Models.ResponsePayload.GetCategoryCharitiesResponse
import com.silverkeytech.easyfunder.R
import com.silverkeytech.easyfunder.Utilities.Global
import com.silverkeytech.easyfunder.Utility.ApiCalls
import com.silverkeytech.easyfunder.Utility.UserData
import java.util.*

class CharityList : AppCompatActivity() {

    private var recyclerView : RecyclerView? = null
    private var adapter: CharityAdapter?= null
    private var charities: ArrayList<Charity> = ArrayList()
    private var charityCategory: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_charity_category)
        initialize()
    }

    override fun onResume() {
        super.onResume()

        var userDonationCharities: DonationCharitiesResponse = ApiCalls.GetDonationCharities(UserData.loginUser.id)
        if (userDonationCharities.hasCharities==true) {
            Collections.sort(userDonationCharities.donationCharities)
            UserData.userDonationCharities = userDonationCharities
        }
        else {
            UserData.userDonationCharities.hasCharities = false
            UserData.userDonationCharities = DonationCharitiesResponse()

        }
        initialize()
    }

    private fun initialize() {
        charityCategory = intent.getStringExtra("category")
        title = charityCategory
        charities = ArrayList()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        recyclerView = findViewById(R.id.category_recycler_view) as RecyclerView
        UserData.setMyCharityFlag()
      if (charityCategory.equals("Supported categories")) {
          for (i in UserData.userDonationCharities.donationCharities) {
              val c: Charity = Charity()
              c.id = i.id!!
              c.name = i.name!!
              c.description = i.description!!
              c.rate = i.rate!!
              c.totalRevenue = i.total_revenue!!
              c.programExpenses = i.programs_expenses!!
              c.fundraisingExpenses = i.fundraising_expenses!!
              c.totalFunctionalExpences = i.total_functional_expenses!!
              c.categoryName = i.category_name!!
              c.country = i.country!!
              c.supported =  i.supported
              charities.add(c)
          }
      }
        else if  ( charityCategory.equals("Supported categories") == false )
      {

          var charityByPaging: GetCategoryCharitiesResponse = ApiCalls.GetCategoryCharities(Global.pageNumber.toString(),charityCategory)
          var charitiesList:ArrayList<CharityInfo> = ArrayList()

         for (j in 0..charityByPaging.charitiesAll.size - 1) {
             var i: CharityInfo = ApiCalls.GetCharityDetails(charityByPaging.charitiesAll.get(j).charityId!!)

              val c: Charity = Charity()
              c.id = i.id!!
              c.name = i.name!!
              c.description = i.description!!
              c.rate = i.rate!!
              c.totalRevenue = i.total_revenue!!
              c.programExpenses = i.programs_expenses!!
              c.fundraisingExpenses = i.fundraising_expenses!!
              c.totalFunctionalExpences = i.total_functional_expenses!!
              c.categoryName = i.category_name!!
              c.country = i.country!!
             for (flag in UserData.userDonationCharities.donationCharities)
             {
                 if (i.name.equals(flag.name))
                 {
                     c.supported=true
                     i.supported=true
                     break
                 }
                 else
                 {
                     c.supported=false
                     i.supported=false
                 }
             }
              charities.add(c)
          }
      }
        adapter = CharityAdapter(this, charities, "shuffled", true, false , UserData.ISAllCharity)
        val layoutManager = LinearLayoutManager(this)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.itemAnimator = DefaultItemAnimator()
        recyclerView?.adapter = adapter
    }
}
