package com.silverkeytech.easyfunder.Models.ResponsePayload

/**
 * Created by samuel on 9/11/2016.
 */
class UserSpendingCategories : Comparable<UserSpendingCategories> {

    var id:String ?= null
    var user_id:String ?= null
    var spending_category_name:String ?= null
    var donation_Percentage:String ?= null

    override fun compareTo(other: UserSpendingCategories): Int {
        return  this.spending_category_name!!.compareTo(other.spending_category_name!!)
    }



}

