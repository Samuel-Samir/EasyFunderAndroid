package com.silverkeytech.easyfunder.Models.ResponsePayload

import java.util.*

/**
 * Created by samuel on 9/11/2016.
 */
class HistoryDetailsResponse {
    var id:String ?=null
    var month_of_donation:String ?=null
    var year_of_donation:String ?=null
    var total_donation:String ?=null
    var spending_categories :ArrayList<String> = ArrayList()
    var spending_categories_percentage :ArrayList<String> =ArrayList()
    var list_donation_charities :ArrayList<String> =ArrayList()
    var amount_of_money_from_each_category :ArrayList<Double> =ArrayList()
    init {
        this.amount_of_money_from_each_category =amount_of_money_from_each_category
        this.list_donation_charities = list_donation_charities
        this.spending_categories_percentage =spending_categories_percentage
        this.spending_categories=spending_categories
    }


}

