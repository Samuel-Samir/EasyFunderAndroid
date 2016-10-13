package com.silverkeytech.easyfunder.Fragments.History

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.silverkeytech.easyfunder.Adapters.SpendingCategoryAdapter
import com.silverkeytech.easyfunder.Models.ResponsePayload.SpendingCategoriesResponse
import com.silverkeytech.easyfunder.Models.SpendingCategory
import com.silverkeytech.easyfunder.R
import com.silverkeytech.easyfunder.Utility.ApiCalls
import com.silverkeytech.easyfunder.Utility.UserData
import java.util.*

class MySpendingCategories : Fragment(){

    private var recyclerView : RecyclerView? = null
    private var adapter: SpendingCategoryAdapter?= null
    private var spendingCategories: ArrayList<SpendingCategory> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?) : View? {
        val view = inflater!!.inflate(R.layout.fragment_my_spending_categories, container, false)
        recyclerView = view.findViewById(R.id.my_spending_categories_recycler_view) as RecyclerView
        setSpendingCategories()
        adapter = SpendingCategoryAdapter(context, spendingCategories, true,false)
        val layoutManager = LinearLayoutManager(view.context)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.itemAnimator = DefaultItemAnimator()
        recyclerView?.adapter = adapter
        if (spendingCategories.size == 0){
            showError("You have no spending categories.")
        }
        return view
    }

    private fun setSpendingCategories(){


        var  b :Double =35.25
        for (categorie in UserData.userSpendingCategories.spendingCategories) {
            val s: SpendingCategory = SpendingCategory()
            s.name = categorie.spending_category_name!!
            s.percentage = categorie.donation_Percentage!!.toDouble()
            s.totalPaid = b
            s.usedForDonation = true
            s.amountDonated = (s.percentage* s.totalPaid)/100.00
            spendingCategories.add(s)
            b*=1.012
        }
        Collections.sort(spendingCategories)
    }

    override fun onResume() {
        super.onResume()

        spendingCategories = ArrayList()
        val mySpendingCatigories = ApiCalls.GetSpendingCategories(UserData.loginUser.id)
        if(mySpendingCatigories.hasSpendingCategories)
        {
            UserData.userSpendingCategories = mySpendingCatigories
        }
        else
        {
            UserData.userSpendingCategories.hasSpendingCategories = false
            UserData.userSpendingCategories = SpendingCategoriesResponse()
        }

        setSpendingCategories()

        adapter = SpendingCategoryAdapter(context, spendingCategories, true,false)
        recyclerView?.adapter = adapter
        if (spendingCategories.size == 0){
            showError("You have no spending categories.")
        }
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
    }

    private fun showError(message: String){
        Toast.makeText(context , message , Toast.LENGTH_SHORT).show()
    }
}
