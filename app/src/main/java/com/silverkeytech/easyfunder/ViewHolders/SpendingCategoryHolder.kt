package com.silverkeytech.easyfunder.ViewHolders

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.silverkeytech.easyfunder.R

class SpendingCategoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    var name: TextView
    var amount: TextView
    var percentage: TextView
    var details: LinearLayout
    var use: Button

    init {
        name = itemView.findViewById(R.id.spending_category_name) as TextView
        amount = itemView.findViewById(R.id.spending_category_amount) as TextView
        percentage = itemView.findViewById(R.id.spending_category_percentage) as TextView
        details = itemView.findViewById(R.id.spending_category_my_details) as LinearLayout
        use = itemView.findViewById(R.id.spending_category_use) as Button
    }
}