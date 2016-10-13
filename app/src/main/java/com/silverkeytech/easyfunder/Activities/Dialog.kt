package com.silverkeytech.easyfunder.Activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.silverkeytech.easyfunder.R
import com.silverkeytech.easyfunder.Utilities.Global
import com.silverkeytech.easyfunder.Utility.ApiCalls
import com.silverkeytech.easyfunder.Utility.UserData

class Dialog : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog)

        var ok :Button = findViewById(R.id.ok_button) as Button
        var cancel:Button = findViewById(R.id.cancel_button) as Button


        ok.setOnClickListener {

            ApiCalls.RemoveSpendingCategory(UserData.loginUser.id , Global.cateoryName)
            finish()

        }

        cancel.setOnClickListener {
            finish()
        }


    }
}
