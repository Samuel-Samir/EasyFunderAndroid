package com.silverkeytech.easyfunder.Models.ResponsePayload

import java.util.*

/**
 * Created by samuel on 9/11/2016.
 */
class SpendingCategoriesResponse {

    var hasSpendingCategories:Boolean= false
    var spendingCategories:ArrayList<UserSpendingCategories> = ArrayList()
    init {
        this.spendingCategories =spendingCategories
        Collections.sort(spendingCategories)
    }
}

