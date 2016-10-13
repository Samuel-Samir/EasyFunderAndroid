package com.silverkeytech.easyfunder.Activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.silverkeytech.easyfunder.Models.RequestPayload.AddBankAccountRequest
import com.silverkeytech.easyfunder.Models.ResponsePayload.BankInfo
import com.silverkeytech.easyfunder.R
import com.silverkeytech.easyfunder.Utility.ApiCalls
import com.silverkeytech.easyfunder.Utility.UserData

class BankAccountRegistration : AppCompatActivity() {

    var bankNameTextView: TextView?= null
    var accountNumberTextView: TextView?= null
    var branchTextView: TextView?= null
    var swiftCodeTextView: TextView?= null
    var addressTextView: TextView?= null
    var addAccountButton: Button?= null
    var somethingMissing: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bank_account_registration)
        title = "Add bank account"
        bankNameTextView = findViewById(R.id.bank_bank_name) as TextView
        accountNumberTextView = findViewById(R.id.bank_account_number) as TextView
        branchTextView = findViewById(R.id.bank_branch) as TextView
        swiftCodeTextView = findViewById(R.id.bank_swift_code) as TextView
        addressTextView = findViewById(R.id.bank_address) as TextView

        addAccountButton = findViewById(R.id.bank_add_account) as Button
        addAccountButton?.setOnClickListener {
            somethingMissing = (
                    bankNameTextView?.text.toString().equals("") ||
                            accountNumberTextView?.text.toString().equals("") ||
                            branchTextView?.text.toString().equals("") ||
                            swiftCodeTextView?.text.toString().equals("") ||
                            addressTextView?.text.toString().equals("")
                    )
            if (somethingMissing){
                showError("Please fill all required fields.")
            }
            else
            {
               var newBank : AddBankAccountRequest = AddBankAccountRequest()
                newBank.bank_name = bankNameTextView?.text.toString()
                newBank.account_number = accountNumberTextView?.text.toString()
                newBank.branch = branchTextView?.text.toString()
                newBank.swift_code = swiftCodeTextView?.text.toString()
                newBank.address= addressTextView?.text.toString()
                newBank.user_id = UserData.loginUser.id
                ApiCalls.AddBankAccount(newBank)
                UserData.loginUser.has_bank_account=true
                ///////////////////////////////////////
                var translate :BankInfo = BankInfo()
                translate.id ="111"
                translate.account_number =newBank.account_number
                translate.address =newBank.address
                translate.bank_name =newBank.bank_name
                translate.branch =newBank.branch
                translate.swift_code=newBank.swift_code
                translate.user_id = UserData.loginUser.id
                UserData.userBank=translate

                startActivity(Intent(this@BankAccountRegistration, Home::class.java))
                this@BankAccountRegistration.finish()
            }
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this@BankAccountRegistration, Home::class.java).putExtra("Show charities", false))
    }

    private fun showError(message: String){
        Snackbar.make(addAccountButton as View, message, Snackbar.LENGTH_SHORT).show()
    }
}
