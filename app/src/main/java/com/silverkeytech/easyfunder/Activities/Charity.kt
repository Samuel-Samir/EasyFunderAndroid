package com.silverkeytech.easyfunder.Activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.silverkeytech.easyfunder.Models.Charity
import com.silverkeytech.easyfunder.Models.ResponsePayload.DonationCharitiesResponse
import com.silverkeytech.easyfunder.Models.ResponsePayload.GetCharitiesResponse
import com.silverkeytech.easyfunder.Models.ResponsePayload.Status
import com.silverkeytech.easyfunder.R
import com.silverkeytech.easyfunder.Utility.ApiCalls
import com.silverkeytech.easyfunder.Utility.UserData
import java.util.*

class Charity : AppCompatActivity() {

    var categoryImageView: ImageView?= null
    var nameTextView: TextView?= null
    var categoryTextView: TextView?= null
    var countryTextView: TextView?= null
    var rateTextView: TextView?= null
    var descriptionTextView: TextView?= null
    var fundraisingExpensesTextView: TextView?= null
    var functionalExpensesTextView: TextView?= null
    var programsTextView: TextView?= null
    var totalRevenueTextView: TextView?= null
    var support: Button?= null
    var supported: Boolean = false
    val map = HashMap<String, Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_charity)
        initialize()
    }
    private fun initialize(){
        map.put("Animals", R.drawable.animals)
        map.put("Arts, and Culture", R.drawable.arts)
        map.put("Community", R.drawable.community_development)
        map.put("Education", R.drawable.education)
        map.put("Environment", R.drawable.environment)
        map.put("Health", R.drawable.health)
        map.put("Human Services", R.drawable.human)
        map.put("International", R.drawable.international)
        map.put("Human Rights", R.drawable._public)
        map.put("Religion", R.drawable.religion)
        map.put("Research", R.drawable.research)
        categoryImageView = findViewById(R.id.charity_details_category_image) as ImageView

        nameTextView = findViewById(R.id.charity_details_name) as TextView
        categoryTextView = findViewById(R.id.charity_details_category) as TextView
        countryTextView = findViewById(R.id.charity_details_country) as TextView
        rateTextView = findViewById(R.id.charity_details_rate) as TextView
        descriptionTextView = findViewById(R.id.charity_details_description) as TextView
        fundraisingExpensesTextView = findViewById(R.id.charity_details_fundraising_expenses) as TextView
        functionalExpensesTextView = findViewById(R.id.charity_details_functional_expenses) as TextView
        programsTextView = findViewById(R.id.charity_details_program_expenses) as TextView
        totalRevenueTextView = findViewById(R.id.charity_details_total_revenue) as TextView
        support = findViewById(R.id.charity_details_support) as Button

        var selecteccharity = intent.getSerializableExtra("charity") as Charity
        nameTextView?.text = selecteccharity.name
        categoryTextView?.text = selecteccharity.categoryName
        countryTextView?.text =selecteccharity.country
        rateTextView?.tag = selecteccharity.rate
        descriptionTextView?.text =selecteccharity.description
        fundraisingExpensesTextView?.text =selecteccharity.fundraisingExpenses
        functionalExpensesTextView?.text =selecteccharity.totalFunctionalExpences
        programsTextView?.text =selecteccharity.programExpenses
        totalRevenueTextView?.text= selecteccharity.totalRevenue
        categoryImageView?.setImageDrawable(resources.getDrawable(map[selecteccharity.categoryName]!!))




        supported = selecteccharity.supported

        support?.text = if (supported) "Supported" else "Support"
        support?.setOnClickListener {
            if (support?.text!!.equals("Support")){
                // add to  my  supported charitys

               // fun AddCharity( user_id:String,charity_id:String ): Status
              var response:Status =  ApiCalls.AddCharity(UserData.loginUser.id ,selecteccharity.id)
                if (response.done==true)
                    Toast.makeText(baseContext, "Charity Added" , Toast.LENGTH_SHORT).show()

                support?.text = "Supported"
            }
            else
            {
                var response:Status =  ApiCalls.RemoveCharity(UserData.loginUser.id ,selecteccharity.id)
                if (response.done==true)
                    Toast.makeText(baseContext, "Charity Removed" , Toast.LENGTH_SHORT).show()
                support?.text = "Support"
            }

            var userDonationCharities: DonationCharitiesResponse = ApiCalls.GetDonationCharities(UserData?.loginUser.id!!)
            if (userDonationCharities.hasCharities==true) {
                Collections.sort(userDonationCharities.donationCharities)
                UserData.userDonationCharities = userDonationCharities
            }
            else
            {
                UserData.userDonationCharities.hasCharities =false
                UserData.userDonationCharities = DonationCharitiesResponse()

            }


            var allCharities : GetCharitiesResponse = ApiCalls.GetCharities()
            UserData.AllCharity =allCharities.charitiesList
            Collections.sort(allCharities.charitiesList)
            UserData.AllCharity =allCharities.charitiesList
            UserData.setMyCharityFlag()
            for (catigory in UserData.AllCharity.iterator() )
            {
                var charitiesTemp = ArrayList<Charity>()
                Collections.sort(catigory.charityDetails)
            }

        }
    }
}
