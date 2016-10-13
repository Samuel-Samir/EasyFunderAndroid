package com.silverkeytech.easyfunder.Models

import java.io.Serializable

class Transaction() : Serializable{
    var id:String=""
    var is_read:Boolean = false
    var year: Int = 0
    var month: Int = 0
    var day: Int = 0
    var amount: Double = 0.0
}