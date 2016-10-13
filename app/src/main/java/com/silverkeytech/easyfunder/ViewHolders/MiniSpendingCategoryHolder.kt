package com.silverkeytech.easyfunder.ViewHolders

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import com.silverkeytech.easyfunder.R

class MiniSpendingCategoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    var name: TextView
    var amount: TextView

    init {
        name = itemView.findViewById(R.id.mini_spending_category_name_and_percentage) as TextView
        amount = itemView.findViewById(R.id.mini_spending_category_amount) as TextView
    }
}