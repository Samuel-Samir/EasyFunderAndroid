
package com.silverkeytech.easyfunder.Adapters
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.silverkeytech.easyfunder.Activities.Dialog
import com.silverkeytech.easyfunder.Activities.Percentage
import com.silverkeytech.easyfunder.Models.RequestPayload.AddSpendingCategoriesInf
import com.silverkeytech.easyfunder.Models.RequestPayload.AddSpendingCategoriesRequest
import com.silverkeytech.easyfunder.Models.ResponsePayload.Status
import com.silverkeytech.easyfunder.Models.SpendingCategory
import com.silverkeytech.easyfunder.R
import com.silverkeytech.easyfunder.Utilities.Global
import com.silverkeytech.easyfunder.Utility.ApiCalls
import com.silverkeytech.easyfunder.Utility.UserData
import com.silverkeytech.easyfunder.ViewHolders.SpendingCategoryHolder
import java.util.*
class SpendingCategoryAdapter(_context: Context, _spendingCategories: ArrayList<SpendingCategory>?, _myCategories: Boolean,history:Boolean ) : RecyclerView.Adapter<SpendingCategoryHolder>() {
    val context = _context
    val spendingCategories : ArrayList<SpendingCategory>? = _spendingCategories
    val mYCategories = _myCategories
    var HistoryBool = history

    companion object{
        var PERCENTAGE = -0.1
        var SPENDING_CATEGORY: SpendingCategory = SpendingCategory()
        var ADAPTER: SpendingCategoryAdapter ?= null
        fun update(){
            SPENDING_CATEGORY.percentage = -0.1
            SPENDING_CATEGORY.percentage = SpendingCategoryAdapter.PERCENTAGE
            SPENDING_CATEGORY.usedForDonation = true
            SPENDING_CATEGORY.amountDonated = (SPENDING_CATEGORY.totalPaid * SPENDING_CATEGORY.percentage) / 100.00
            ADAPTER?.notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpendingCategoryHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_spending_category, parent, false)
        return SpendingCategoryHolder(itemView)
    }

    override fun onBindViewHolder(holder: SpendingCategoryHolder, position: Int)
    {


        val spendingCategory = spendingCategories!![position]

        if(HistoryBool)
        {
            holder.use.visibility = View.GONE
        }
        else
        {
            holder.use.visibility = View.VISIBLE
        }

        holder.name.text = String.format("%s ($%.2f)", spendingCategory.name, spendingCategory.totalPaid)
        holder.amount.text = "$" + spendingCategory.amountDonated.toString()
        holder.percentage.text = spendingCategory.percentage.toString() + "%"
        //holder.amount.text = String.format("%.2f", spendingCategory.amountDonated * spendingCategory.percentage / 100.00)
        holder.amount.text = String.format("$%.2f", spendingCategory.amountDonated)
        if (spendingCategory.amountDonated == 0.0){
            holder.amount.setTextColor(context.resources.getColor(R.color.red))
        }
        else {
            holder.amount.setTextColor(context.resources.getColor(R.color.green))
        }
        if (spendingCategory.usedForDonation){

            holder.details.visibility = View.VISIBLE
            holder.use.text = "remove"
            holder.use.setBackgroundColor(context.resources.getColor(R.color.red))
        }
        else {
            holder.details.visibility = View.GONE
            holder.use.text = "Add"
            holder.use.setBackgroundColor(context.resources.getColor(R.color.green))
        }
        holder.use.setOnClickListener {
            if (holder.use.text.equals("Add")){
                var newSpendingCategorie: AddSpendingCategoriesRequest = AddSpendingCategoriesRequest()
                var temp: AddSpendingCategoriesInf = AddSpendingCategoriesInf()
                if (mYCategories)
                {
                    var wanted=UserData.userSpendingCategories.spendingCategories.get(position)
                    temp.user_id =wanted.user_id
                    temp.spending_category_name =wanted.spending_category_name
                    temp.donation_Percentage =6.0
                    wanted.donation_Percentage="6.0"

                }
                else if  (mYCategories==false)
                {
                    var wanted  = UserData.AllspendingCategories.get(position)
                    UserData.AllspendingCategories.get(position).percentage =6.0
                    temp.user_id =UserData.loginUser.id
                    temp.spending_category_name =wanted.name
                    temp.donation_Percentage =6.0
                    wanted.percentage =6.0
                }
                newSpendingCategorie.SpendingCategories.add(temp)
                var response : Status = ApiCalls.AddSpendingCategories(newSpendingCategorie)
                if (response.done ==true && mYCategories == false)
                {
                    UserData.AllspendingCategories.get(position).usedForDonation=true

                }


                    SpendingCategoryAdapter.SPENDING_CATEGORY = spendingCategory
                SpendingCategoryAdapter.ADAPTER = this
                context.startActivity(Intent(context, Percentage::class.java))
            }
            else {
                var cateoryName :String =String()


                if (mYCategories)
                {
                    cateoryName = UserData.userSpendingCategories.spendingCategories.get(position).spending_category_name!!
//                    (context as Activity).startActivity(Intent(context,SpendingCategories::class.java))
//                    (context as Activity).finish()
                }
                else if (mYCategories==false)
                {
                    cateoryName = UserData.AllspendingCategories.get(position).name

                }

                Global.cateoryName = cateoryName

                context.startActivity(Intent(context, Dialog::class.java))

                //ApiCalls.RemoveSpendingCategory(UserData.loginUser.id , cateoryName)
//                holder.details.visibility = View.GONE
//                holder.use.text = "Add"
//                holder.use.setBackgroundColor(context.resources.getColor(R.color.green))
            }
        }
    }
    override fun getItemCount(): Int {
        return spendingCategories!!.size
    }
}
