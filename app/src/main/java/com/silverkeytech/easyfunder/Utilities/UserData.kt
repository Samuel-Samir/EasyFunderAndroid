package com.silverkeytech.easyfunder.Utility

import com.silverkeytech.easyfunder.Models.ResponsePayload.*
import com.silverkeytech.easyfunder.Models.SpendingCategory
import java.util.*

/**
 * Created by samuel on 9/14/2016.
 */
object UserData {
 //   UserInfo loginUser
    var loginUser : UserInfo =UserInfo()
    var userBank: BankInfo = BankInfo()
    var userSpendingCategories : SpendingCategoriesResponse = SpendingCategoriesResponse()
    var userDonationCharities: DonationCharitiesResponse =DonationCharitiesResponse ()
    var AllspendingCategories: ArrayList<SpendingCategory> = ArrayList()
    var AllCharity :ArrayList<Charities> = ArrayList()
    var  index :Int =0
    var ISAllCharity :Boolean =false

    fun setMyCharityFlag ()
    {
        val userDonationCharities: DonationCharitiesResponse = ApiCalls.GetDonationCharities(UserData.loginUser.id)
        if (userDonationCharities.hasCharities==true) {
            Collections.sort(userDonationCharities.donationCharities)
            UserData.userDonationCharities = userDonationCharities
        }
        else
        {
            UserData.userDonationCharities.hasCharities =false
            UserData.userDonationCharities = DonationCharitiesResponse()
        }


//       for (catigory in AllCharity )
//       {
//           for (listCharity in catigory.charityDetails)
//           {
//               for (myCharity in userDonationCharities.donationCharities )
//               {
//                   if (myCharity.name .equals(listCharity.name))
//                   {
//                       myCharity.supported=true
//                       listCharity.supported=true
//                   }
//                   else
//                   {
//                       listCharity.supported=false
//
//                   }
//               }
//           }
//       }
//

        for (i in 0..AllCharity.size-1)
        {
            for (j in 0..AllCharity.get(i).charityDetails.size-1)
            {
                for (myCharity in userDonationCharities.donationCharities )
                {
                    if (myCharity.name .equals(AllCharity.get(i).charityDetails.get(j).name))
                    {
                        myCharity.supported=true
                        AllCharity.get(i).charityDetails.get(j).supported=true
                    }
                    else
                    {
                        AllCharity.get(i).charityDetails.get(j).supported=false

                    }
                }

            }
        }
    }

    fun initializeAllMySpendingCatigory ()
   {
       val s2 = SpendingCategory()
       s2.name = "Entertainment"
       s2.totalPaid = 234.0
       s2.percentage =5.0
       s2.usedForDonation = false


       val s4 = SpendingCategory()
       s4.name = "Restaurants"
       s4.totalPaid = 120.0
       s2.percentage =5.2

       s4.usedForDonation = false

       val s5 = SpendingCategory()
       s5.name = "Soft drinks"
       s5.totalPaid = 50.0
       s2.percentage =6.0

       s5.usedForDonation = false

       val s1 = SpendingCategory()
       s1.name = "Automotive"
       s1.totalPaid = 301.0
       s2.percentage =7.0

       s1.usedForDonation = false

       val s3 = SpendingCategory()
       s3.name = "Food"
       s3.totalPaid = 125.0
       s2.percentage =2.0

       s3.usedForDonation = false

       val s6 = SpendingCategory()
       s6.name = "Telephone"
       s6.totalPaid = 140.0
       s2.percentage =5.0

       s6.usedForDonation = false

       val s7 = SpendingCategory()
       s7.name = "Toys"
       s2.percentage =3.0

       s7.totalPaid = 40.0
       s7.usedForDonation = false
       AllspendingCategories = arrayListOf(s1, s2, s3, s4, s5, s6, s7)
       Collections.sort(AllspendingCategories)

       val mySpendingCatigories = ApiCalls.GetSpendingCategories(UserData.loginUser.id)
       if(mySpendingCatigories.hasSpendingCategories)
       {
           userSpendingCategories.hasSpendingCategories = true
           userSpendingCategories = mySpendingCatigories
       }
       else
       {
           userSpendingCategories.hasSpendingCategories = false
           userSpendingCategories = SpendingCategoriesResponse()
       }



       for (i  in userSpendingCategories.spendingCategories)
       {
           for(k in 0..AllspendingCategories.size - 1)
           {
               if (i.spending_category_name.equals(AllspendingCategories.get(k).name))
               {
                   AllspendingCategories.get(k).usedForDonation = true
               }

           }
       }

   }


}