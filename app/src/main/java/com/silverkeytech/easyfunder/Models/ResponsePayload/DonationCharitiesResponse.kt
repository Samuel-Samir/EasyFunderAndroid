package com.silverkeytech.easyfunder.Models.ResponsePayload

import com.silverkeytech.easyfunder.Models.Charity
import java.util.*

/**
 * Created by samuel on 9/11/2016.
 */
class DonationCharitiesResponse {
    var hasCharities:Boolean ? =false
    var donationCharities:ArrayList<CharityInfo> =ArrayList()
    init {
         this.donationCharities=donationCharities
    }
}

