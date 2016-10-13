package com.silverkeytech.easyfunder.Adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import com.silverkeytech.easyfunder.Models.Charity
import com.silverkeytech.easyfunder.Models.ResponsePayload.Status
import com.silverkeytech.easyfunder.R
import com.silverkeytech.easyfunder.Utility.ApiCalls
import com.silverkeytech.easyfunder.Utility.UserData
import com.silverkeytech.easyfunder.ViewHolders.CharityHolder
import java.util.*

class CharityAdapter(_context: Context, _charities: ArrayList<Charity>?, _charitiesCategory: String?, _dashboard: Boolean, _mini: Boolean ,_IsAllCharity:Boolean ) :
        RecyclerView.Adapter<CharityHolder>() {
    val context = _context
    var charities: ArrayList<Charity>? = _charities
    val map = HashMap<String, Int>()
    val charitiesCategory: String? = _charitiesCategory
    val dashboard: Boolean = _dashboard
    val mini: Boolean = _mini
    val IsAllCharity:Boolean =_IsAllCharity

    init {
        map.put("Animals", R.drawable.animals)
        map.put("Arts, and Culture", R.drawable.arts)
        map.put("Community", R.drawable.community_development)
        map.put("Education", R.drawable.education)
        map.put("Environment", R.drawable.environment)
        map.put("Health", R.drawable.health)
        map.put("Human Rights", R.drawable._public)
        map.put("Human Services", R.drawable.human)
        map.put("International", R.drawable.international)
        map.put("Religion", R.drawable.religion)
        map.put("Research", R.drawable.research)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharityHolder
    {
        val resource: Int = if (mini) R.layout.list_mini_item_charity else R.layout.list_item_charity
        val itemView = LayoutInflater.from(parent.context)
                .inflate(resource, parent, false)
        return CharityHolder(itemView)
    }

    override fun onBindViewHolder(holder: CharityHolder, position: Int) {

        var charity = charities!![position]
        holder.name.text = charity.name
        holder.rate.text = charity.rate
//        if (charitiesCategory.equals("shuffled")){
//            holder.imageType.setImageDrawable(context.resources.getDrawable(map[charity.categoryName]!!))
//        }
//        else {
//            holder.imageType.setImageDrawable(context.resources.getDrawable(map[charitiesCategory]!!))
//        }
        holder.imageType.setImageDrawable(context.resources.getDrawable(R.drawable.ic_business_white_18dp))
        if (mini){
            holder.support = holder.support as ImageButton
            if (charity.supported){
                (holder.support as ImageButton).setImageDrawable(
                        context.resources.getDrawable(R.drawable.ic_favorite_white_18dp))
            }
            else {
                (holder.support as ImageButton).setImageDrawable(
                        context.resources.getDrawable(R.drawable.ic_favorite_border_white_18dp))
            }

//            holder.support.setOnClickListener {
//                if (charity.supported && !dashboard){
//                 /*  */
//                    charity.supported = false
//                    (holder.support as ImageButton).setImageDrawable(
//                            context.resources.getDrawable(R.drawable.ic_favorite_border_white_18dp))
//                }
//                else if (!charity.supported){
//                   /* */
//                    charity.supported = true
//                    (holder.support as ImageButton).setImageDrawable(
//                            context.resources.getDrawable(R.drawable.ic_favorite_white_18dp))
//                }
//            }
        }
        else {
            holder.support = holder.support as Button
            if (charity.supported==true){
                (holder.support as Button).text = "Supported"
            }
            else if (charity.supported==false) {
                (holder.support as Button).text = "Support"
            }
//////////////////////////////////////////////////////////////////
            holder.support.setOnClickListener {
                // remove
                if (charity.supported==true)
                {
                    var ID: String? = String()
                    if ( IsAllCharity == false)
                    {
                        ID = UserData.userDonationCharities.donationCharities.get(position).id
                    }
                    else if (IsAllCharity)
                    {
                      //  Toast.makeText(context ," remove all " , Toast.LENGTH_LONG).show()
                        ID = UserData.AllCharity.get(UserData.index).charityDetails.get(position).id
                    }

                  var response:Status =  ApiCalls.RemoveCharity(UserData.loginUser.id ,ID!!)
                    if (response.done==true)
                        Toast.makeText(context, "Charity Removed" , Toast.LENGTH_SHORT).show()

                    charity.supported = false
                    (holder.support as Button).text = "Support"
                }
                    // add charity
                else if (charity.supported==false){

                    var response: Status =Status()
                    if (IsAllCharity == false)
                    {
                            //Toast.makeText(context ," add dashboard " , Toast.LENGTH_LONG).show()
                         var ID :String? = UserData.userDonationCharities.donationCharities.get(position).id
                       response=  ApiCalls.AddCharity(UserData.loginUser.id ,ID!!)

                    }

                    else if (IsAllCharity )
                    {
                        //Toast.makeText(context ," add All " , Toast.LENGTH_LONG).show()
                         var ID = UserData.AllCharity.get(UserData.index).charityDetails.get(position).id
                         response=  ApiCalls.AddCharity(UserData.loginUser.id ,ID!!)
                    }
                    if (response.done==true)
                       Toast.makeText(context, "Charity Added" , Toast.LENGTH_SHORT).show()
                    charity.supported = true
                    (holder.support as Button).text = "Supported"
                }

//                var userDonationCharities: DonationCharitiesResponse = ApiCalls.GetDonationCharities(UserData?.loginUser.id!!)
//                if (userDonationCharities.hasCharities==true) {
//                    Collections.sort(userDonationCharities.donationCharities)
//                    UserData.userDonationCharities = userDonationCharities
//                }
//                else
//                    UserData.userDonationCharities.hasCharities =false
//                var allCharities : GetCharitiesResponse = ApiCalls.GetCharities()
//                UserData.AllCharity =allCharities.charitiesList
//                Collections.sort(allCharities.charitiesList)
//                UserData.AllCharity =allCharities.charitiesList
//                UserData.setMyCharityFlag()
//                for (catigory in UserData.AllCharity.iterator() )
//                {
//                    var charitiesTemp = ArrayList<Charity>()
//                    Collections.sort(catigory.charityDetails)
//                }



            }

//            (context as Activity).startActivity(Intent(context, Home::class.java))
//            (context as Activity).finish()
        }


        holder.layout.setOnClickListener {
            // TODO API call
            Toast.makeText(context, charity.categoryName, Toast.LENGTH_SHORT).show();
            context.startActivity(
                    Intent(context,
                            com.silverkeytech.easyfunder.Activities.Charity::class.java)
                    .putExtra("charity", charity)
            )
        }
    }

    override fun getItemCount(): Int {
        return charities!!.size
    }
}
