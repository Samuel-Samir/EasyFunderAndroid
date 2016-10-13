package com.silverkeytech.easyfunder.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.silverkeytech.easyfunder.Models.SpendingCategory
import com.silverkeytech.easyfunder.R
import com.silverkeytech.easyfunder.ViewHolders.MiniSpendingCategoryHolder
import com.silverkeytech.easyfunder.ViewHolders.SpendingCategoryHolder
import java.util.*

class MiniSpendingCategoryAdapter(_context: Context,
                                  _spendingCategories: ArrayList<SpendingCategory>?) :
        RecyclerView.Adapter<MiniSpendingCategoryHolder>() {
    val context = _context
    val spendingCategories : ArrayList<SpendingCategory>? = _spendingCategories

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MiniSpendingCategoryHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_mini_item_spending_category, parent, false)
        return MiniSpendingCategoryHolder(itemView)
    }

    override fun onBindViewHolder(holder: MiniSpendingCategoryHolder, position: Int)
    {
        val spendingCategory = spendingCategories!![position]
        holder.name.text = spendingCategory.name
        if (spendingCategory.usedForDonation){
            holder.name.text = holder.name.text.toString() +
                            String.format(" (%s)", spendingCategory.percentage.toString() + '%')
        }
        holder.amount.text = spendingCategory.totalPaid.toString()
    }

    override fun getItemCount(): Int {
        return spendingCategories!!.size
    }
}
