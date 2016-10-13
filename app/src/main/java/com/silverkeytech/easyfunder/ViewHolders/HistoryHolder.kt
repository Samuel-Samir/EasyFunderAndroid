package com.silverkeytech.easyfunder.ViewHolders

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.silverkeytech.easyfunder.R

class HistoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var layout: LinearLayout
    var month: TextView
    var amount: TextView

    init {
        layout = itemView.findViewById(R.id.transaction_history_details) as LinearLayout
        month = itemView.findViewById(R.id.transaction_history_month) as TextView
        amount = itemView.findViewById(R.id.transaction_history_amount) as TextView
    }
}