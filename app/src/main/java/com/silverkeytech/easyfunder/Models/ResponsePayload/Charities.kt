package com.silverkeytech.easyfunder.Models.ResponsePayload

import java.util.*

/**
 * Created by samuel on 9/11/2016.
 */
class Charities: Comparable<Charities> {

    var categoryName  :String?=null
    var numberOfCharities:String?=null
    var numberOfPages:String?=null
    var charityDetails:ArrayList<CharityInfo> = ArrayList()
    init {
         this.charityDetails =charityDetails
    }

    override fun compareTo(other: Charities): Int {
        //throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
       if (this?.categoryName.equals(other?.categoryName!!))
       {
          Collections.sort(this.charityDetails)

       }
        return this?.categoryName!!.compareTo(other?.categoryName!!)

    }
}

