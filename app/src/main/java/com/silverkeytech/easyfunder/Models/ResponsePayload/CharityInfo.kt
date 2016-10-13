package com.silverkeytech.easyfunder.Models.ResponsePayload

import java.util.*

/**
 * Created by samuel on 9/11/2016.
 */
class CharityInfo  : Comparable<CharityInfo> {


    var id:String?=null
    var name:String?=null
    var description:String?=null
    var rate:String?=null
    var country:String?=null
    var total_revenue:String?=null
    var programs_expenses:String?=null
    var fundraising_expenses:String?=null
    var total_functional_expenses:String?=null
    var category_name:String?=null
    var supported: Boolean = false

    override fun compareTo(other: CharityInfo): Int {
        //throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
      if (this.category_name.equals(other.category_name))
      {
          return this?.name!!.compareTo(other?.name!!)

      }
        return this?.category_name!!.compareTo(other?.category_name!!)

    }


}



/*
class fth : Comparator<fth> {
    override fun compare(lhs: fth, rhs: fth): Int {
        return 0
    }
}
 */
