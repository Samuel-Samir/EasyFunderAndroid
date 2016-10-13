package com.silverkeytech.easyfunder.Models.ResponsePayload

import java.util.*

/**
 * Created by samuel on 9/11/2016.
 */
class GetCategoryCharitiesResponse {
    var status :Boolean?=false
    var categoryName  :String?=null
    var charitiesAll:ArrayList<CharitiesPaging> = ArrayList()
    init {
        this.charitiesAll = charitiesAll
    }
}

