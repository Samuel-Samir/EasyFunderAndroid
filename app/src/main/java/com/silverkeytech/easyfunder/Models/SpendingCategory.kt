package com.silverkeytech.easyfunder.Models

class SpendingCategory: Comparable<SpendingCategory> {

    var name: String = ""
    var percentage: Double = 0.0
    var totalPaid: Double = 0.0
    var amountDonated: Double = 0.0
    var usedForDonation: Boolean = false
    override fun compareTo(other: SpendingCategory): Int {
        return this.name.compareTo(other.name)
    }


}