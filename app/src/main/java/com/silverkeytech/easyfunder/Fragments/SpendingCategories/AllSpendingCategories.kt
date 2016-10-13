package com.silverkeytech.easyfunder.Fragments.History

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.silverkeytech.easyfunder.Adapters.SpendingCategoryAdapter
import com.silverkeytech.easyfunder.Models.SpendingCategory
import com.silverkeytech.easyfunder.R
import com.silverkeytech.easyfunder.Utility.UserData
import java.util.*

class AllSpendingCategories : Fragment(){

    private var recyclerView : RecyclerView? = null
    private var adapter: SpendingCategoryAdapter?= null
    private var spendingCategories: ArrayList<SpendingCategory> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?) : View? {
        val view = inflater!!.inflate(R.layout.fragment_all_spending_categories, container, false)
        recyclerView = view.findViewById(R.id.all_spending_categories_recycler_view) as RecyclerView
        setSpendingCategories()
        adapter = SpendingCategoryAdapter(context,  UserData.AllspendingCategories, false,false )
        val layoutManager = LinearLayoutManager(view.context)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.itemAnimator = DefaultItemAnimator()
        recyclerView?.adapter = adapter
        return view
    }

    private fun setSpendingCategories(){
       UserData.initializeAllMySpendingCatigory ()


    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()

        setSpendingCategories()
        adapter = SpendingCategoryAdapter(context,  UserData.AllspendingCategories, false,false )
        recyclerView?.adapter = adapter


    }
}






