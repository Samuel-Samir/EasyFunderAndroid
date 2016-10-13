package com.silverkeytech.easyfunder.Adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.silverkeytech.easyfunder.Activities.Report
import com.silverkeytech.easyfunder.Models.Transaction
import com.silverkeytech.easyfunder.R
import com.silverkeytech.easyfunder.ViewHolders.HistoryHolder
import java.util.*

class HistoryAdapter(_context: Context,_transactions :ArrayList<Transaction>) : RecyclerView.Adapter<HistoryHolder>() {
    val context = _context
    var transactions: ArrayList<Transaction> = _transactions
    val months: ArrayList<String> = arrayListOf(
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_transaction_history, parent, false)



        return HistoryHolder(itemView)
    }

    override fun onBindViewHolder(holder: HistoryHolder, position: Int)
    {





        val transaction = transactions[position]
        holder.month.text = months[transaction.month - 1]
        holder.amount.text = "$" + transaction.amount.toString()
        if (transaction.amount == 0.0){
            holder.amount.setTextColor(context.resources.getColor(R.color.red))
        }
        else {
            holder.amount.setTextColor(context.resources.getColor(R.color.green))
        }
        holder.layout.setOnClickListener{
            val intent: Intent = Intent(context, Report::class.java)
            intent.putExtra("flag","1").putExtra("transaction", transaction)

            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return transactions.size
    }
}
