package com.silverkeytech.easyfunder.Activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.silverkeytech.easyfunder.Models.RequestPayload.SigninRequest
import com.silverkeytech.easyfunder.Models.ResponsePayload.*
import com.silverkeytech.easyfunder.Models.SpendingCategory
import com.silverkeytech.easyfunder.R
import com.silverkeytech.easyfunder.Utility.ApiCalls
import com.silverkeytech.easyfunder.Utility.UserData
import java.util.*

class SignIn : AppCompatActivity() {

    var emailTextView: TextView?= null
    var password: TextView?= null
    var signInButton: Button?= null
    var signUpButton: Button?= null
    var somethingMissing: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        emailTextView = findViewById(R.id.sign_in_email) as TextView
        password = findViewById(R.id.sign_in_password) as TextView
        signInButton = findViewById(R.id.sign_in_sign_in) as Button
        signUpButton = findViewById(R.id.sign_in_sign_up) as Button
        // signIn()

        var _loginUser : UserInfo = UserInfo()
        var _userBank: BankInfo = BankInfo()
        var _userSpendingCategories : SpendingCategoriesResponse = SpendingCategoriesResponse()
        var _userDonationCharities: DonationCharitiesResponse =DonationCharitiesResponse ()
        var _AllspendingCategories: ArrayList<SpendingCategory> = ArrayList()
        var _AllCharity : ArrayList<Charities> = ArrayList()
        UserData.loginUser =_loginUser
        UserData.userBank =_userBank
        UserData.userSpendingCategories =_userSpendingCategories
        UserData.userDonationCharities =_userDonationCharities
        UserData.AllspendingCategories =_AllspendingCategories
        UserData.AllCharity =_AllCharity
        title = "Sign in"
        signInButton?.setOnClickListener(View.OnClickListener {
           signIn()
        })
        signUpButton?.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this@SignIn, SignUp::class.java))
            finish()
        })

    }
    private fun signIn(){
        somethingMissing = (
                emailTextView?.text.toString().equals("") ||
                        password?.text.toString().equals("") ||
                        emailTextView?.text.toString().equals("") ||
                        signInButton?.text.toString().equals("") ||
                        signUpButton?.text.toString().equals("")
                )
        if (somethingMissing){
            showError("Please fill all required fields.")
        }
        else {
            var loginUser: SigninRequest = SigninRequest()
            loginUser.email = emailTextView?.text.toString()
            loginUser.Password = password?.text.toString()

            var response : SigninResponse = ApiCalls.Signin(loginUser)
            if(response.ifExist==false)
            {
                showError("Email Not Exist, Enter Correct Email.")
            }
            else if (response.ifExist==true && response.errorStatus.equals("invalid pass"))
            {
                showError("Invalid Password, Enter Valid Password")
            }
            else if (response.ifExist==true && response.errorStatus.equals("done"))
            {

                UserData.loginUser =response.user
//                var now:TimeNow = TimeNow()
//                now.user_id = UserData.loginUser.id
//                var timenow:Calendar = Calendar.getInstance()
//                now.day = timenow.get(Calendar.DAY_OF_MONTH).toString()
//                now.month = timenow.get(Calendar.MONTH).toString()
//                now.year = timenow.get(Calendar.YEAR).toString()
//
//                now.hours = ""
//                now.minutes = ""
//                now.seconds = ""
//
//                ApiCalls.SetTime(now)
                if(UserData.loginUser.has_bank_account == true)
                {
                    var userBank: BankInfo = ApiCalls.GetBankDetails(UserData?.loginUser.id!!)
                    UserData.userBank =userBank
                }

                if(UserData.loginUser.has_spending_categories == true)
                {
                    var userSpendingCategories: SpendingCategoriesResponse = ApiCalls.GetSpendingCategories(UserData?.loginUser.id!!)
                    if (userSpendingCategories.hasSpendingCategories==true)
                        UserData.userSpendingCategories =userSpendingCategories
                    else
                        UserData.userSpendingCategories.hasSpendingCategories =false
                }

                if(UserData.loginUser.has_charities_to_donation == true)
                {
                    var userDonationCharities: DonationCharitiesResponse = ApiCalls.GetDonationCharities(UserData?.loginUser.id!!)
                    if (userDonationCharities.hasCharities==true)
                        UserData.userDonationCharities =userDonationCharities
                    else
                        UserData.userDonationCharities.hasCharities =false
                }
                /*  Log.i ("bank" , UserData.userBank.bank_name)
                  Log.i ("userSpendingCategories" , UserData.userSpendingCategories.hasSpendingCategories.toString())
                  Log.i ("userDonationCharities" , UserData.userDonationCharities.hasCharities.toString())*/
                if (UserData.loginUser.has_charities_to_donation == false && UserData.loginUser.has_spending_categories==false)
                {
                    startActivity(Intent(this@SignIn, Home::class.java).putExtra("Show charities", true))
                    finish()
                }

                else
                {
                    startActivity(Intent(this@SignIn, Home::class.java).putExtra("Show charities", false))
                    finish()
                }

            }
        }
    }
    private fun showError(message: String){
        Snackbar.make(signUpButton as View, message, Snackbar.LENGTH_SHORT).show()
    }
}