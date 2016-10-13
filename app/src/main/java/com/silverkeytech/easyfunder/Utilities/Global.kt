package com.silverkeytech.easyfunder.Utilities

import android.content.Context
import com.silverkeytech.easyfunder.Adapters.CharityAdapter
import com.silverkeytech.easyfunder.Adapters.MiniSpendingCategoryAdapter
import com.silverkeytech.easyfunder.Models.Transaction
import java.util.*

/**
 * Created by Ahmed on 9/19/2016.
 */
object Global {

    var transaction_id:String = "";
    var yearSelected = "";
    var yearsList:ArrayList<String> = ArrayList()
    var allTransactions:ArrayList<ArrayList<Transaction>> = ArrayList()
    var contextList:ArrayList<Context> = ArrayList()
    var pageNumber:Int = 1
    var dashMySpendingCategoryAdapter : MiniSpendingCategoryAdapter? = null
    var dashMySupportedCharitiesAdapter : CharityAdapter? = null
    var cateoryName : String = String()


}