package com.silverkeytech.easyfunder.Utility

import android.net.Uri
import com.google.gson.Gson
import com.silverkeytech.easyfunder.Models.RequestPayload.*
import com.silverkeytech.easyfunder.Models.ResponsePayload.*
import com.silverkeytech.easyfunder.Utilities.Global

/**
 * Created by samuel on 9/11/2016.
 */
object ApiCalls {
   // var staticURL: String = "http://192.168.1.21/api/"
    var staticURL: String = "http://10.0.2.2:5000/api/"
    var finalResuilt: String? = null


    // user api functions
    fun Signin(signinUser: SigninRequest): SigninResponse {
        val builtUri = Uri.parse(staticURL + "user/signin").buildUpon().build()
        val url = builtUri.toString()
        val jsonObj: Gson = Gson()
        val str: String = jsonObj.toJson(signinUser)
        finalResuilt = Connection().execute(url, "POST", str).get()
        val gson: Gson = Gson()
        var response: SigninResponse = gson.fromJson(finalResuilt, SigninResponse::class.java)
        return response
    }

    fun Signup(SignupUser: SignupRequest): SignupResponse {
        val builtUri = Uri.parse(staticURL + "user/signup").buildUpon().build()
        val url = builtUri.toString()
        val jsonObj: Gson = Gson()
        val str: String = jsonObj.toJson(SignupUser)
        finalResuilt = Connection().execute(url, "POST", str).get()
        val gson: Gson = Gson()
        var response: SignupResponse = gson.fromJson(finalResuilt, SignupResponse::class.java)
        return response
    }

    fun CreateVerifyCode(email: String, firstName: String): VerifyCodeResponse {
        val builtUri = Uri.parse(staticURL + "user/create/code?").buildUpon().appendQueryParameter("email" ,email ).appendQueryParameter("firstName" ,firstName ).build()
        val url = builtUri.toString()
        finalResuilt = Connection().execute(url, "GET").get()
        val gson: Gson = Gson()
        var response: VerifyCodeResponse = gson.fromJson(finalResuilt, VerifyCodeResponse::class.java)
        return response
    }

    fun GetVerifyCode(email: String): VerifyCodeResponse {
        val builtUri = Uri.parse(staticURL + "user/get/verification/code?").buildUpon().appendQueryParameter("email" ,email ).build()
        val url = builtUri.toString()
        finalResuilt = Connection().execute(url, "GET").get()
        val gson: Gson = Gson()
        var response: VerifyCodeResponse = gson.fromJson(finalResuilt, VerifyCodeResponse::class.java)
        return response
    }

    fun GetUserData(email: String ,byEmail:Boolean ,id:String ,byId:Boolean): UserInfo {
        val builtUri = Uri.parse(staticURL + "user/get/user/data?").buildUpon().appendQueryParameter("email" ,email ).appendQueryParameter("byEmail" ,byEmail.toString())
                .appendQueryParameter("id",id) .appendQueryParameter("byId",byId.toString()).build()
        val url = builtUri.toString()
        finalResuilt = Connection().execute(url, "GET").get()
        val gson: Gson = Gson()
        var response: UserInfo = gson.fromJson(finalResuilt, UserInfo::class.java)
        return response
    }

    fun GetBankDetails(id: String ): BankInfo {
        val builtUri = Uri.parse(staticURL + "user/bank/account/details?").buildUpon().appendQueryParameter("id",id).build()
        val url = builtUri.toString()
        finalResuilt = Connection().execute(url, "GET").get()
        val gson: Gson = Gson()
        var response: BankInfo = gson.fromJson(finalResuilt, BankInfo::class.java)
        return response
    }

    fun GetSpendingCategories(id: String ): SpendingCategoriesResponse {
        val builtUri = Uri.parse(staticURL + "user/spend/categories?").buildUpon().appendQueryParameter("id",id).build()
        val url = builtUri.toString()
        finalResuilt = Connection().execute(url, "GET").get()
        val gson: Gson = Gson()
        var response: SpendingCategoriesResponse = gson.fromJson(finalResuilt, SpendingCategoriesResponse::class.java)
        return response
    }

    fun GetDonationCharities(id: String ): DonationCharitiesResponse {
        val builtUri = Uri.parse(staticURL + "user/donation/charities?").buildUpon().appendQueryParameter("id",id).build()
        val url = builtUri.toString()
        finalResuilt = Connection().execute(url, "GET").get()
        val gson: Gson = Gson()
        var response: DonationCharitiesResponse = gson.fromJson(finalResuilt, DonationCharitiesResponse::class.java)
        return response
    }
    fun SetVerificationBoolean(email: String ): Status {
        val builtUri = Uri.parse(staticURL + "user/set/verify?").buildUpon().appendQueryParameter("email",email).build()
        val url = builtUri.toString()
        finalResuilt = Connection().execute(url, "GET").get()
        val gson: Gson = Gson()
        var response: Status = gson.fromJson(finalResuilt, Status::class.java)
        return response
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////
                                 //         Timing api functions
    fun SetTime (timeNow : TimeNow) : TimeNow
    {
        val builtUri = Uri.parse(staticURL +"timing/set/now").buildUpon().build()
        val url =builtUri.toString()
        val jsonObj : Gson = Gson()
        val str:String = jsonObj.toJson(timeNow)
        finalResuilt = Connection().execute(url ,"POST",str).get()
        val gson: Gson = Gson()
        var response: TimeNow = gson.fromJson(finalResuilt, TimeNow::class.java)
        return response

    }

    fun GetTime (user_id :String) : TimeNow
    {
        val builtUri = Uri.parse(staticURL +"timing/get/now?").buildUpon()
                .appendQueryParameter("user_id",user_id).build()
        val url =builtUri.toString()
        finalResuilt = Connection().execute(url ,"GET").get()
        val gson: Gson = Gson()
        var response: TimeNow = gson.fromJson(finalResuilt, TimeNow::class.java)
        return response

    }


    fun SetNextDonationTime (user_id :String) : NextDonation
    {
        val builtUri = Uri.parse(staticURL +"timing/set/next/donation?").buildUpon()
                .appendQueryParameter("user_id",user_id).build()
        val url =builtUri.toString()
        finalResuilt = Connection().execute(url ,"GET").get()
        val gson: Gson = Gson()
        var response: NextDonation = gson.fromJson(finalResuilt, NextDonation::class.java)
        return response

    }

    fun GetNextDonationTime (user_id :String) : NextDonation
    {
        val builtUri = Uri.parse(staticURL +"timing/get/next/donation?").buildUpon()
                .appendQueryParameter("user_id",user_id).build()
        val url =builtUri.toString()
        finalResuilt = Connection().execute(url ,"GET").get()
        val gson: Gson = Gson()
        var response: NextDonation = gson.fromJson(finalResuilt, NextDonation::class.java)
        return response

    }

    fun SetNextDonationFirstTime (user_id :String) : Status
    {
        val builtUri = Uri.parse(staticURL +"timing/set/next/donation/first?").buildUpon()
                .appendQueryParameter("user_id",user_id).build()
        val url =builtUri.toString()
        finalResuilt = Connection().execute(url ,"GET").get()
        val gson: Gson = Gson()
        var response: Status = gson.fromJson(finalResuilt, Status::class.java)
        return response

    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////
            //  Remove api functions

    fun RemoveCharity(user_id: String , charity_id:String): Status {
        val builtUri = Uri.parse(staticURL + "remove/charity?").buildUpon().appendQueryParameter("user_id",user_id).appendQueryParameter("charity_id",charity_id).build()
        val url = builtUri.toString()
        finalResuilt = Connection().execute(url, "GET").get()
        val gson: Gson = Gson()
        var response: Status = gson.fromJson(finalResuilt, Status::class.java)
        return response
    }

    fun RemoveSpendingCategory(user_id: String , spending_category_name:String): Status {
        val builtUri = Uri.parse(staticURL + "remove/spending/category?")
        .buildUpon().appendQueryParameter("user_id",user_id).appendQueryParameter("spending_category_name",spending_category_name).build()
        val url = builtUri.toString()
        finalResuilt = Connection().execute(url, "GET").get()
        val gson: Gson = Gson()
        var response: Status = gson.fromJson(finalResuilt, Status::class.java)
        return response
    }

    fun RemoveBankAccount(user_id: String): Status {
        val builtUri = Uri.parse(staticURL + "remove/account?")
        .buildUpon().appendQueryParameter("user_id",user_id).build()
        val url = builtUri.toString()
        finalResuilt = Connection().execute(url, "GET").get()
        val gson: Gson = Gson()
        var response: Status = gson.fromJson(finalResuilt, Status::class.java)
        return response
    }

    fun RemoveAll(user_id: String): Status {
        val builtUri = Uri.parse(staticURL + "remove/all?")
        .buildUpon().appendQueryParameter("user_id",user_id).build()
        val url = builtUri.toString()
        finalResuilt = Connection().execute(url, "GET").get()
        val gson: Gson = Gson()
        var response: Status = gson.fromJson(finalResuilt, Status::class.java)
        return response
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //  Charity api functions

    fun AddCharity(newCharity: AddCharityRequist): Status {
        val builtUri = Uri.parse(staticURL + "Charity/add/charity").buildUpon().build()
        val url = builtUri.toString()
        val jsonObj: Gson = Gson()
        val str: String = jsonObj.toJson(newCharity)
        finalResuilt = Connection().execute(url, "POST", str).get()
        val gson: Gson = Gson()
        var response: Status = gson.fromJson(finalResuilt, Status::class.java)
        Global.dashMySupportedCharitiesAdapter?.notifyDataSetChanged()
        return response

    }

    fun GetCharities(): GetCharitiesResponse
    {
        val builtUri = Uri.parse(staticURL + "Charity/get/charities").buildUpon().build()
        val url = builtUri.toString()
        finalResuilt = Connection().execute(url, "GET").get()
        val gson: Gson = Gson()
        var response: GetCharitiesResponse = gson.fromJson(finalResuilt, GetCharitiesResponse::class.java)
        return response
    }

    fun GetCategoryCharities(pageNumber:String ,category_name  :String): GetCategoryCharitiesResponse
    {
        val builtUri = Uri.parse(staticURL + "Charity/category/charities?").buildUpon()
          .appendQueryParameter("pageNumber",pageNumber).appendQueryParameter("categoryName",category_name  ).build()
        val url = builtUri.toString()
        finalResuilt = Connection().execute(url, "GET").get()
        val gson: Gson = Gson()
        var response: GetCategoryCharitiesResponse = gson.fromJson(finalResuilt, GetCategoryCharitiesResponse::class.java)
        return response
    }

    fun GetCharityDetails(charityId:String ): CharityInfo
    {
        val builtUri = Uri.parse(staticURL + "Charity/details?").buildUpon()
                .appendQueryParameter("charityId",charityId).build()
        val url = builtUri.toString()
        finalResuilt = Connection().execute(url, "GET").get()
        val gson: Gson = Gson()
        var response: CharityInfo = gson.fromJson(finalResuilt, CharityInfo::class.java)
        return response
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //  Add api functions
    fun AddCharity( user_id:String,charity_id:String ): Status
    {
        val builtUri = Uri.parse(staticURL + "add/charity?").buildUpon()
          .appendQueryParameter("user_id",user_id).appendQueryParameter("charity_id",charity_id).build()
        val url = builtUri.toString()
        finalResuilt = Connection().execute(url, "GET").get()
        val gson: Gson = Gson()
        var response: Status = gson.fromJson(finalResuilt, Status::class.java)
        return response
    }

    fun AddBankAccount (newBank: AddBankAccountRequest): Status {
        val builtUri = Uri.parse(staticURL + "add/account").buildUpon().build()
        val url = builtUri.toString()
        val jsonObj: Gson = Gson()
        val str: String = jsonObj.toJson(newBank)
        finalResuilt = Connection().execute(url, "POST", str).get()
        val gson: Gson = Gson()
        var response: Status = gson.fromJson(finalResuilt, Status::class.java)
        return response
    }

    fun AddSpendingCategories (newSpendingCategorie: AddSpendingCategoriesRequest): Status {
        val builtUri = Uri.parse(staticURL + "add/spending/categories").buildUpon().build()
        val url = builtUri.toString()
        val jsonObj: Gson = Gson()
        val str: String = jsonObj.toJson(newSpendingCategorie)
        finalResuilt = Connection().execute(url, "POST", str).get()
        val gson: Gson = Gson()
        var response: Status = gson.fromJson(finalResuilt, Status::class.java)
      //  Global.dashMySpendingCategoryAdapter?.notifyDataSetChanged()
        return response
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //  transaction api functions


    fun GetReport( id:String ): HistoryDetailsResponse
    {
        val builtUri = Uri.parse(staticURL + "transaction/report?").buildUpon()
                .appendQueryParameter("id",id).build()
        val url = builtUri.toString()
        finalResuilt = Connection().execute(url, "GET").get()
        val gson: Gson = Gson()
        var response: HistoryDetailsResponse = gson.fromJson(finalResuilt, HistoryDetailsResponse::class.java)
        return response
    }

    fun GetDonationsYears( user_id :String ): DonationYearsResponse
    {
        val builtUri = Uri.parse(staticURL + "transaction/years?").buildUpon()
                .appendQueryParameter("user_id",user_id).build()
        val url = builtUri.toString()
        finalResuilt = Connection().execute(url, "GET").get()
        val gson: Gson = Gson()
        var response: DonationYearsResponse = gson.fromJson(finalResuilt, DonationYearsResponse::class.java)
        return response
    }


    fun GetHistory( user_id :String , year :String): HistoryList
    {
        val builtUri = Uri.parse(staticURL + "transaction/history?").buildUpon()
                .appendQueryParameter("user_id",user_id).appendQueryParameter("year",year).build()
        val url = builtUri.toString()
        finalResuilt = Connection().execute(url, "GET").get()
        val gson: Gson = Gson()
        var response: HistoryList = gson.fromJson(finalResuilt, HistoryList::class.java)
        return response
    }

    fun GetNotifications( user_id :String ): CheckNewTransaction
    {
        val builtUri = Uri.parse(staticURL + "transaction/new?").buildUpon()
                .appendQueryParameter("user_id",user_id).build()
        val url = builtUri.toString()
        finalResuilt = Connection().execute(url, "GET").get()
        val gson: Gson = Gson()
        var response: CheckNewTransaction = gson.fromJson(finalResuilt, CheckNewTransaction::class.java)
        return response
    }


    fun UpdateNotification( id :String ): Status
    {
        val builtUri = Uri.parse(staticURL + "transaction/update/notification?").buildUpon()
                .appendQueryParameter("id",id).build()
        val url = builtUri.toString()
        finalResuilt = Connection().execute(url, "GET").get()
        val gson: Gson = Gson()
        var response: Status = gson.fromJson(finalResuilt, Status::class.java)
        return response
    }


    fun GetTransactionToday( user_id :String ): GetTransactionToday
    {
        val builtUri = Uri.parse(staticURL + "transaction/today?").buildUpon()
                .appendQueryParameter("user_id",user_id).build()
        val url = builtUri.toString()
        finalResuilt = Connection().execute(url, "GET").get()
        val gson: Gson = Gson()
        var response: GetTransactionToday = gson.fromJson(finalResuilt, GetTransactionToday::class.java)
        return response
    }


    fun GetTransactionTodayDetails( user_id :String , id: String ): GetTransactionTodayDetailsResponse
    {
        val builtUri = Uri.parse(staticURL + "transaction/today/details?").buildUpon()
                .appendQueryParameter("user_id",user_id).appendQueryParameter("id",id).build()
        val url = builtUri.toString()
        finalResuilt = Connection().execute(url, "GET").get()
        val gson: Gson = Gson()
        var response: GetTransactionTodayDetailsResponse = gson.fromJson(finalResuilt, GetTransactionTodayDetailsResponse::class.java)
        return response
    }


    fun MakeTransaction( user_id :String ): Status
    {
        val builtUri = Uri.parse(staticURL + "transaction/donation?").buildUpon()
                .appendQueryParameter("user_id",user_id).build()
        val url = builtUri.toString()
        finalResuilt = Connection().execute(url, "GET").get()
        val gson: Gson = Gson()
        var response: Status = gson.fromJson(finalResuilt, Status::class.java)
        return response
    }

}

