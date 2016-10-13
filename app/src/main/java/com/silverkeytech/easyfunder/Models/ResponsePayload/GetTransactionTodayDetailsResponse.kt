package com.silverkeytech.easyfunder.Models.ResponsePayload

import java.util.*

/**
 * Created by samuel on 9/11/2016.
 */
class GetTransactionTodayDetailsResponse {
    var total_donation :String ?=null
    var spending_categories:ArrayList<String> = ArrayList()
    var spending_categories_percentage:ArrayList<Double> =ArrayList()
    var amount_of_money_from_each_category:ArrayList<Double> =ArrayList()

    init {
        this.total_donation =total_donation
        this.spending_categories =spending_categories
        this.spending_categories_percentage =spending_categories_percentage
        this.amount_of_money_from_each_category = amount_of_money_from_each_category

    }


}
