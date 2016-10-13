package com.silverkeytech.easyfunder.Activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.silverkeytech.easyfunder.Models.RequestPayload.SignupRequest
import com.silverkeytech.easyfunder.Models.ResponsePayload.*
import com.silverkeytech.easyfunder.Models.SpendingCategory
import com.silverkeytech.easyfunder.R
import com.silverkeytech.easyfunder.Utility.ApiCalls
import com.silverkeytech.easyfunder.Utility.UserData
import java.util.*

class SignUp : AppCompatActivity() {
    var emailIsExist :Boolean = true
    var firstNameTextView: TextView?= null
    var lastNameTextView: TextView?= null
    var emailTextView: TextView?= null
    var phoneNumberTextView: TextView?= null
    var passwordTextView: TextView?= null
    var signUpButton: Button?= null
    var somethingMissing: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        title = "Sign up"
        firstNameTextView = findViewById(R.id.sign_up_first_name) as TextView
        lastNameTextView = findViewById(R.id.sign_up_last_name) as TextView
        emailTextView = findViewById(R.id.sign_up_email) as TextView
        phoneNumberTextView = findViewById(R.id.sign_up_phone_number) as TextView
        passwordTextView = findViewById(R.id.sign_up_password) as TextView
        signUpButton = findViewById(R.id.sign_up_sign_up) as Button

        var _loginUser : UserInfo = UserInfo()
        var _userBank: BankInfo = BankInfo()
        var _userSpendingCategories : SpendingCategoriesResponse = SpendingCategoriesResponse()
        var _userDonationCharities: DonationCharitiesResponse = DonationCharitiesResponse ()
        var _AllspendingCategories: ArrayList<SpendingCategory> = ArrayList()
        var _AllCharity : ArrayList<Charities> = ArrayList()
        UserData.loginUser =_loginUser
        UserData.userBank =_userBank
        UserData.userSpendingCategories =_userSpendingCategories
        UserData.userDonationCharities =_userDonationCharities
        UserData.AllspendingCategories =_AllspendingCategories
        UserData.AllCharity =_AllCharity
        signUpButton?.setOnClickListener {
            emailIsExist = true

            somethingMissing = (
                    firstNameTextView?.text.toString().equals("") ||
                            lastNameTextView?.text.toString().equals("") ||
                            emailTextView?.text.toString().equals("") ||
                            phoneNumberTextView?.text.toString().equals("") ||
                            passwordTextView?.text.toString().equals("")
                    )
            if (somethingMissing){
                showError("Please fill all required fields.")
            }
            else {

                var signupUser: SignupRequest = SignupRequest()
                signupUser.first_name = firstNameTextView?.text.toString()
                signupUser.second_name = lastNameTextView?.text.toString()
                signupUser.phone_number = phoneNumberTextView?.text.toString()
                signupUser.email = emailTextView?.text.toString()
                signupUser.Password = passwordTextView?.text.toString()
                var response: SignupResponse = ApiCalls.Signup(signupUser)
                if(response.uniqueEmail==false)
                {
                    showError("Email Exist, Enter another Email!!")
                    emailIsExist=false

                }
                if (emailIsExist)
                {
                    UserData.loginUser = response.user

                    var now:TimeNow = TimeNow()
                    now.user_id = UserData.loginUser.id
                    var timenow:Calendar = Calendar.getInstance()
                    now.day = timenow.get(Calendar.DAY_OF_MONTH).toString()
                    now.month = timenow.get(Calendar.MONTH).toString()
                    now.year = timenow.get(Calendar.YEAR).toString()

                    now.hours = ""
                    now.minutes = ""
                    now.seconds = ""

                    ApiCalls.SetTime(now)






                    startActivity(Intent(this@SignUp, Home::class.java).putExtra("Show charities", true))

                    finish()
                }
                //

            }
        }
    }
    private fun showError(message: String){
        Snackbar.make(signUpButton as View, message, Snackbar.LENGTH_SHORT).show()
    }
}
