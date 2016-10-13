package com.silverkeytech.easyfunder.Models

import java.io.Serializable

class Charity() : Serializable{
    var id: String = ""
    var name: String = ""
    var description: String = ""
    var rate: String = ""
    var country: String = ""
    var totalRevenue: String = ""
    var programExpenses: String = ""
    var fundraisingExpenses: String = ""
    var totalFunctionalExpences: String = ""
    var categoryName: String = ""
    var supported: Boolean =false

}