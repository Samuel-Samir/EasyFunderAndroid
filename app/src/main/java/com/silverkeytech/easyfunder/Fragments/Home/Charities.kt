package com.silverkeytech.easyfunder.Fragments.Home
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.silverkeytech.easyfunder.Activities.CharityList
import com.silverkeytech.easyfunder.Adapters.CharityAdapter
import com.silverkeytech.easyfunder.Models.Charity
import com.silverkeytech.easyfunder.Models.ResponsePayload.GetCharitiesResponse
import com.silverkeytech.easyfunder.R
import com.silverkeytech.easyfunder.Utility.ApiCalls
import com.silverkeytech.easyfunder.Utility.UserData
import java.util.*

class Charities : Fragment(), View.OnClickListener {

    private var charities = ArrayList<Charity>()
    private var recyclerViews: ArrayList<RecyclerView> = ArrayList()
    private var adapters: ArrayList<CharityAdapter> = ArrayList()
    private var headings: ArrayList<LinearLayout> = ArrayList()
    private var charitiesNames = arrayListOf(
            "Animals",
            "Arts, and Culture",
            "Community",
            "Education",
            "Environment",
            "Health",
            "Human Rights",
            "Human Services",
            "International",
            "Religion",
            "Research"
    )

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?) : View? {
        val view: View = inflater!!.inflate(R.layout.fragment_home_charities, container, false)
        initialize(view)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initialize(view: View){
        var allCharities : GetCharitiesResponse = ApiCalls.GetCharities()
        UserData.AllCharity =allCharities.charitiesList
        Collections.sort(allCharities.charitiesList)
        UserData.AllCharity =allCharities.charitiesList
        UserData.setMyCharityFlag()

        headings = arrayListOf(
                view.findViewById(R.id.home_charities_heading_animals) as LinearLayout,
                view.findViewById(R.id.home_charities_heading_art) as LinearLayout,
                view.findViewById(R.id.home_charities_heading_community) as LinearLayout,
                view.findViewById(R.id.home_charities_heading_education) as LinearLayout,
                view.findViewById(R.id.home_charities_heading_environment) as LinearLayout,
                view.findViewById(R.id.home_charities_heading_health) as LinearLayout,
                view.findViewById(R.id.home_charities_heading_human) as LinearLayout,
                view.findViewById(R.id.home_charities_heading_international) as LinearLayout,
                view.findViewById(R.id.home_charities_heading_human_rights) as LinearLayout,
                view.findViewById(R.id.home_charities_heading_religion) as LinearLayout,
                view.findViewById(R.id.home_charities_heading_research) as LinearLayout
        )

        recyclerViews = arrayListOf(
                view.findViewById(R.id.animals_charities_recycler_view) as RecyclerView,
                view.findViewById(R.id.art_charities_recycler_view) as RecyclerView,
                view.findViewById(R.id.community_charities_recycler_view) as RecyclerView,
                view.findViewById(R.id.education_charities_recycler_view) as RecyclerView,
                view.findViewById(R.id.environment_charities_recycler_view) as RecyclerView,
                view.findViewById(R.id.health_charities_recycler_view) as RecyclerView,
                view.findViewById(R.id.human_charities_recycler_view) as RecyclerView,
                view.findViewById(R.id.international_charities_recycler_view) as RecyclerView,
                view.findViewById(R.id.human_rights_charities_recycler_view) as RecyclerView,
                view.findViewById(R.id.religion_charities_recycler_view) as RecyclerView,
                view.findViewById(R.id.research_charities_recycler_view) as RecyclerView
        )
        var i :Int = 0
        for (catigory in UserData.AllCharity.iterator() )
        {
            var charitiesTemp = ArrayList<Charity>()
            Collections.sort(catigory.charityDetails)
            for (charityObject in catigory.charityDetails)
            {
                var c :Charity = Charity()
                c.id = charityObject.id!!
                c.name = charityObject.name!!
                c.description = charityObject.description!!
                c.rate =charityObject.rate!!
                c.country = charityObject.country!!
                c.totalRevenue = charityObject.total_revenue!!
                c.programExpenses =charityObject.programs_expenses!!
                c.fundraisingExpenses = charityObject.fundraising_expenses!!
                c.totalRevenue = charityObject.total_functional_expenses!!
                c.categoryName = charityObject.category_name!!
                c.supported =charityObject.supported
                charitiesTemp.add(c)
            }
            charities = charitiesTemp


//            for (catigory in UserData.AllCharity.iterator() )
//            {
//                var charitiesTemp = ArrayList<Charity>()
//                var charityByPaging: GetCategoryCharitiesResponse = ApiCalls.GetCategoryCharities(Global.pageNumber.toString(),catigory.categoryName!!)
//                var charitiesList:ArrayList<CharityInfo> = ArrayList()
//
//                for (j in 0..charityByPaging.charitiesAll.size -1)
//                {
//                    var oneCharityObject: CharityInfo = ApiCalls.GetCharityDetails(charityByPaging.charitiesAll.get(j).charityId!!)
//                    charitiesList.add(oneCharityObject)
//
//                }
//                Collections.sort(charitiesList)
//                for (charityObject in charitiesList)
//                {
//                    var c :Charity = Charity()
//                    c.id = charityObject.id!!
//                    c.name = charityObject.name!!
//                    c.description = charityObject.description!!
//                    c.rate =charityObject.rate!!
//                    c.country = charityObject.country!!
//                    c.totalRevenue = charityObject.total_revenue!!
//                    c.programExpenses =charityObject.programs_expenses!!
//                    c.fundraisingExpenses = charityObject.fundraising_expenses!!
//                    c.totalRevenue = charityObject.total_functional_expenses!!
//                    c.categoryName = charityObject.category_name!!
//                    c.supported =charityObject.supported
//                    charitiesTemp.add(c)
//                }
//                charities = charitiesTemp


                headings[i].setOnClickListener(this)

            adapters.add(CharityAdapter(context, charities, catigory.categoryName, false, true,true))
            recyclerViews[i].layoutManager = LinearLayoutManager(context)
            recyclerViews[i].itemAnimator = DefaultItemAnimator()
            recyclerViews[i].adapter = adapters.get(i)
            i++
        }



    }


    override fun onResume() {
        super.onResume()
        var allCharities : GetCharitiesResponse = ApiCalls.GetCharities()
        UserData.AllCharity =allCharities.charitiesList
        Collections.sort(allCharities.charitiesList)
        UserData.AllCharity =allCharities.charitiesList
        UserData.setMyCharityFlag()

        var i :Int = 0
        for (catigory in UserData.AllCharity.iterator() )
        {
            var charitiesTemp = ArrayList<Charity>()
            Collections.sort(catigory.charityDetails)
            for (charityObject in catigory.charityDetails)
            {
                var c :Charity = Charity()
                c.id = charityObject.id!!
                c.name = charityObject.name!!
                c.description = charityObject.description!!
                c.rate =charityObject.rate!!
                c.country = charityObject.country!!
                c.totalRevenue = charityObject.total_revenue!!
                c.programExpenses =charityObject.programs_expenses!!
                c.fundraisingExpenses = charityObject.fundraising_expenses!!
                c.totalRevenue = charityObject.total_functional_expenses!!
                c.categoryName = charityObject.category_name!!
                c.supported =charityObject.supported
                charitiesTemp.add(c)
            }
            charities = charitiesTemp



            headings[i].setOnClickListener(this)

            adapters.add(CharityAdapter(context, charities, catigory.categoryName, false, true,true))
            recyclerViews[i].layoutManager = LinearLayoutManager(context)
            recyclerViews[i].itemAnimator = DefaultItemAnimator()
            recyclerViews[i].adapter = adapters.get(i)
            i++
        }

    }

    override fun onClick(v: View?) {
        UserData.ISAllCharity=true
        for (i in 0..10){
            if (v?.id == headings[i].id){
                UserData.index=i
                startActivity(
                        Intent(context, CharityList::class.java).putExtra("category", charitiesNames[i]))
                break
            }
        }
    }
}
