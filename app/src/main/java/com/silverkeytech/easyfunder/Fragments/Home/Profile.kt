package com.silverkeytech.easyfunder.Fragments.Home
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.silverkeytech.easyfunder.Activities.BankAccountRegistration
import com.silverkeytech.easyfunder.Activities.SignIn
import com.silverkeytech.easyfunder.Models.ResponsePayload.BankInfo
import com.silverkeytech.easyfunder.Models.ResponsePayload.Charities
import com.silverkeytech.easyfunder.Models.ResponsePayload.DonationCharitiesResponse
import com.silverkeytech.easyfunder.Models.ResponsePayload.SpendingCategoriesResponse
import com.silverkeytech.easyfunder.Models.SpendingCategory
import com.silverkeytech.easyfunder.R
import com.silverkeytech.easyfunder.Utility.ApiCalls
import com.silverkeytech.easyfunder.Utility.UserData
import java.util.*

class Profile : Fragment(){

    var personalNameTextView: TextView?= null
    var personalEmailTextView: TextView?= null
    var personalPhoneTextView: TextView?= null
    var bankNameTextView: TextView?= null
    var bankAccountTextView: TextView?= null
    var bankBranchTextView: TextView?= null
    var bankSwiftCodeTextView: TextView?= null
    var bankAddressTextView: TextView?= null
    var bankDetailsContainer: LinearLayout?= null
    var addOrRemoveBankAccount: Button?= null
    var signOut: Button?= null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?) : View? {
        val view =  inflater!!.inflate(R.layout.fragment_home_profile, container, false)
        personalNameTextView = view.findViewById(R.id.profile_person_name) as TextView
        personalEmailTextView = view.findViewById(R.id.profile_email) as TextView
        personalPhoneTextView = view.findViewById(R.id.profile_phone) as TextView

        bankNameTextView = view.findViewById(R.id.profile_bank_name) as TextView
        bankAccountTextView = view.findViewById(R.id.profile_account_number) as TextView
        bankBranchTextView = view.findViewById(R.id.profile_branch) as TextView
        bankSwiftCodeTextView = view.findViewById(R.id.profile_swift_code) as TextView
        bankAddressTextView = view.findViewById(R.id.profile_address) as TextView

        bankDetailsContainer = view.findViewById(R.id.profile_bank_account_details) as LinearLayout

        addOrRemoveBankAccount = view.findViewById(R.id.add_or_remove_bank_account) as Button
        signOut = view.findViewById(R.id.sign_out) as Button

        personalNameTextView!!.text = UserData.loginUser.first_name+ " " +UserData.loginUser.second_name
        personalEmailTextView!!.text = UserData.loginUser.email
        personalPhoneTextView !!.text = UserData.loginUser.phone_number


        inisializeBank()

        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        var haveBankAccount: Boolean = UserData.loginUser.has_bank_account!!

        if (haveBankAccount){
            bankDetailsContainer?.visibility = View.VISIBLE
            addOrRemoveBankAccount?.text = "Remove account"
            addOrRemoveBankAccount?.setBackgroundColor(resources.getColor(R.color.red))
        }
        else {
            bankDetailsContainer?.visibility = View.GONE
            addOrRemoveBankAccount?.text = "Add account"
            addOrRemoveBankAccount?.setBackgroundColor(resources.getColor(R.color.green))
        }


        addOrRemoveBankAccount?.setOnClickListener {
            if (addOrRemoveBankAccount?.text!!.equals("Add account")){

                context.startActivity(Intent(context, BankAccountRegistration::class.java))
                addOrRemoveBankAccount?.setBackgroundColor(context.resources.getColor(R.color.red))
                addOrRemoveBankAccount?.text = "Remove account"
                (context as Activity).finish()
            }
            else {
                addOrRemoveBankAccount?.setBackgroundColor(context.resources.getColor(R.color.green))
                ApiCalls.RemoveBankAccount(UserData.loginUser.id)
                addOrRemoveBankAccount?.text = "Add account"
                bankDetailsContainer?.visibility = View.GONE
            }
        }
        signOut?.setOnClickListener {

            var AllspendingCategories2: ArrayList<SpendingCategory> = ArrayList()
            var AllCharity2 :ArrayList<Charities> = ArrayList()

            startActivity(Intent(context, SignIn::class.java))
            (context as Activity).finish()
        }
    }

    fun inisializeBank()
    {
        bankNameTextView!!.text =UserData.userBank.bank_name
        bankAccountTextView !!.text =UserData.userBank.account_number
        bankBranchTextView !!.text =UserData.userBank.branch
        bankSwiftCodeTextView !!.text =UserData.userBank.swift_code
        bankAddressTextView !!.text =UserData.userBank.address
    }

    override fun onResume() {
        super.onResume()

        var response = ApiCalls.GetUserData(UserData.loginUser.email!!,true,"",false)
        UserData.loginUser= response
        if(UserData.loginUser.has_bank_account == true)
        {
            var userBank: BankInfo = ApiCalls.GetBankDetails(UserData?.loginUser.id!!)
            UserData.userBank =userBank
        }
        else
        {
            UserData.userBank = BankInfo()
        }

        if(UserData.loginUser.has_spending_categories == true)
        {
            var userSpendingCategories: SpendingCategoriesResponse = ApiCalls.GetSpendingCategories(UserData?.loginUser.id!!)
            if (userSpendingCategories.hasSpendingCategories==true)
                UserData.userSpendingCategories =userSpendingCategories
            else
            {
                UserData.userSpendingCategories.hasSpendingCategories =false
                UserData.userSpendingCategories = SpendingCategoriesResponse()
            }

        }

        if(UserData.loginUser.has_charities_to_donation == true)
        {
            var userDonationCharities: DonationCharitiesResponse = ApiCalls.GetDonationCharities(UserData?.loginUser.id!!)
            if (userDonationCharities.hasCharities==true)
                UserData.userDonationCharities =userDonationCharities
            else
            {
                UserData.userSpendingCategories.hasSpendingCategories =false
                UserData.userSpendingCategories = SpendingCategoriesResponse()
            }
        }


        inisializeBank()


    }
}