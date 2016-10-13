package com.silverkeytech.easyfunder.Fragments.Home
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.silverkeytech.easyfunder.Activities.CharityList
import com.silverkeytech.easyfunder.Activities.Report
import com.silverkeytech.easyfunder.Activities.SpendingCategories
import com.silverkeytech.easyfunder.Adapters.CharityAdapter
import com.silverkeytech.easyfunder.Adapters.MiniSpendingCategoryAdapter
import com.silverkeytech.easyfunder.Models.Charity
import com.silverkeytech.easyfunder.Models.ResponsePayload.*
import com.silverkeytech.easyfunder.Models.SpendingCategory
import com.silverkeytech.easyfunder.Models.Transaction
import com.silverkeytech.easyfunder.R
import com.silverkeytech.easyfunder.Utilities.Global
import com.silverkeytech.easyfunder.Utility.ApiCalls
import com.silverkeytech.easyfunder.Utility.UserData
import java.util.*


class Dashboard : Fragment(){
    override fun onResume() {
        super.onResume()

        var response = ApiCalls.GetUserData(UserData.loginUser.email!!,true,"",false)
        UserData.loginUser= response
        if(UserData.loginUser.has_bank_account == true)
        {
            var userBank: BankInfo = ApiCalls.GetBankDetails(UserData?.loginUser.id!!)
            UserData.userBank =userBank
        }

        if(UserData.loginUser.has_spending_categories == true)
        {
            var userSpendingCategories: SpendingCategoriesResponse = ApiCalls.GetSpendingCategories(UserData?.loginUser.id!!)
            if (userSpendingCategories.hasSpendingCategories==true)
                UserData.userSpendingCategories =userSpendingCategories
            else
            {
                UserData.userSpendingCategories.hasSpendingCategories =false
                UserData.userSpendingCategories = SpendingCategoriesResponse()
            }

        }

        if(UserData.loginUser.has_charities_to_donation == true)
        {
            var userDonationCharities: DonationCharitiesResponse = ApiCalls.GetDonationCharities(UserData?.loginUser.id!!)
            if (userDonationCharities.hasCharities==true)
                UserData.userDonationCharities =userDonationCharities
            else
            {
                UserData.userSpendingCategories.hasSpendingCategories =false
                UserData.userSpendingCategories = SpendingCategoriesResponse()
            }
        }


        supportedCharities = ArrayList()

        var userDonationCharities: DonationCharitiesResponse = ApiCalls.GetDonationCharities(UserData.loginUser.id)
        if (userDonationCharities.hasCharities==true) {
            Collections.sort(userDonationCharities.donationCharities)
            UserData.userDonationCharities = userDonationCharities

            Collections.sort(UserData.userDonationCharities.donationCharities  )

            for (i in UserData.userDonationCharities.donationCharities) {
                val c: Charity = Charity()
                c.id =i.id!!
                c.name =i.name!!
                c.description =i.description!!
                c.rate =i.rate!!
                c.totalRevenue =i.total_revenue!!
                c.programExpenses =i.programs_expenses!!
                c.fundraisingExpenses =i.fundraising_expenses!!
                c.totalFunctionalExpences =i.total_functional_expenses!!
                c.categoryName =i.category_name!!
                c.country =i.country!!
                c.supported =true
                i.supported=true
                supportedCharities.add(c)
            }

        }
        else {
            UserData.userDonationCharities.hasCharities = false
            UserData.userDonationCharities = DonationCharitiesResponse()
        }
        UserData.setMyCharityFlag()
        var charityAdapter = CharityAdapter(context, supportedCharities, "shuffled", true, true ,false)
        recyclerViews[0].adapter = charityAdapter
        Global.dashMySupportedCharitiesAdapter = charityAdapter



        ///Spending categories

        var  b :Double =35.25
        spendingCategories = ArrayList()

        val mySpendingCatigories = ApiCalls.GetSpendingCategories(UserData.loginUser.id)
        if(mySpendingCatigories.hasSpendingCategories)
        {
            UserData.userSpendingCategories = mySpendingCatigories
            for (categorie in UserData.userSpendingCategories.spendingCategories) {
                val s: SpendingCategory = SpendingCategory()
                s.name = categorie.spending_category_name!!
                s.percentage = categorie.donation_Percentage!!.toDouble()
                s.totalPaid = b
                s.usedForDonation = true
                spendingCategories.add(s)
                b*=1.012

            }
        }
        else
        {
            UserData.userSpendingCategories.hasSpendingCategories = false
            UserData.userSpendingCategories = SpendingCategoriesResponse()
        }

        var mySpendingCategoriesAdapter: MiniSpendingCategoryAdapter = MiniSpendingCategoryAdapter(context, spendingCategories)
        Global.dashMySpendingCategoryAdapter = mySpendingCategoriesAdapter
        recyclerViews[1].adapter = mySpendingCategoriesAdapter


        var next:NextDonation = ApiCalls.GetNextDonationTime(UserData.loginUser.id)
        var now: TimeNow = ApiCalls.GetTime(UserData.loginUser.id)

        if(next.user_id  == null || next.user_id?.length == 0)
        {

        }
        else
        {
            var nextMonth:Int = next.month!!.toInt()
            var nextYear:Int = next.year!!.toInt()
            var nextDay :Int = next.day!!.toInt()

            var nowMonth:Int = now.month!!.toInt()
            var nowYear:Int = now.year!!.toInt()
            var nowtDay :Int = now.day!!.toInt()


            if(UserData.loginUser.has_bank_account == true &&
                    UserData.loginUser.has_charities_to_donation == true &&
                    UserData.loginUser.has_spending_categories == true)
            {
                if(nextYear == nowYear)
                {
                    if(nowMonth > nextMonth )
                    {
//                        if (nowMonth == nextMonth&&nowtDay>=25  ) {
//                            ApiCalls.MakeTransaction(UserData.loginUser.id)
//                            ApiCalls.SetNextDonationTime(UserData.loginUser.id)
//                        }

                         if(nowMonth > nextMonth )
                        {
                            ApiCalls.MakeTransaction(UserData.loginUser.id)
                            ApiCalls.SetNextDonationTime(UserData.loginUser.id)
                        }
                    }

                }
                else if(nowYear > nextYear)
                {
                    ApiCalls.MakeTransaction(UserData.loginUser.id)
                    ApiCalls.SetNextDonationTime(UserData.loginUser.id)
                }


            }


        }

    }

    private var recyclerViews: ArrayList<RecyclerView> = ArrayList()
    private var adapters: ArrayList<CharityAdapter> = ArrayList()
    private var headings: ArrayList<LinearLayout> = ArrayList()
    private var supportedCharities: ArrayList<Charity> = ArrayList()
    private var spendingCategories: ArrayList<SpendingCategory> = ArrayList()
    private var notification: LinearLayout? = null
    private var notifiactionDetails: Button? = null
    private var notificationDismiss: Button? = null
    private var statusDetails: Button? = null
    private var myView: View?= null
    private var notificationImage: ImageView?= null
    private var notificationTitle: TextView?= null
    private var notificationDescription : TextView?= null
    private var statusDescription: TextView?= null
    var haveUnreadNotification : Boolean = true
    var lastTransactionAmount: Double = 0.00
    var lastTransactionNumberOfSupportedCharities: Int = 0
    var currentlySupportedMoneyAmount: Double = 0.00
    var currentlyNumberOfSupportedCharities: Int = 0

    var TransactionTodayObject: GetTransactionToday = ApiCalls.GetTransactionToday(UserData.loginUser.id)
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?) : View? {
        val view: View = inflater!!.inflate(R.layout.fragment_home_dashboard, container, false)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        initialize(view!!)
    }

    private fun initialize(view: View){
        myView = view.findViewById(R.id.dashboard)
        notification = view.findViewById(R.id.home_dashboard_notification) as LinearLayout
        notifiactionDetails = view.findViewById(R.id.notifications_details) as Button
        notificationDismiss = view.findViewById(R.id.notifications_dismiss) as Button
        statusDetails = view.findViewById(R.id.status_details) as Button
        notificationImage = view.findViewById(R.id.notification_status_image) as ImageView
        notificationTitle = view.findViewById(R.id.notification_title) as TextView
        notificationDescription = view.findViewById(R.id.notification_description) as TextView
        statusDescription = view.findViewById(R.id.dashboard_status_description) as TextView

        // TODO API call notification call Done
        var notificationObject:CheckNewTransaction  = ApiCalls.GetNotifications(UserData.loginUser.id)

        if(notificationObject.ifnew == true)
        {
            haveUnreadNotification = true
            lastTransactionAmount = notificationObject.total_donation!!.toDouble()

            lastTransactionNumberOfSupportedCharities = UserData.userDonationCharities.donationCharities.size




        }
        else
        {
            haveUnreadNotification = false
            lastTransactionAmount = 0.00
            lastTransactionNumberOfSupportedCharities = 0
        }
        ////


        //TODO Api call for today transaction Done
        if(TransactionTodayObject.total_donation.length > 0 )
        {
            currentlySupportedMoneyAmount = TransactionTodayObject.total_donation.toDouble()
            currentlyNumberOfSupportedCharities = UserData.userDonationCharities.donationCharities.size
            statusDetails?.visibility = View.VISIBLE
        }
        else
        {
            statusDetails?.visibility = View.GONE
            currentlySupportedMoneyAmount = 0.00
            currentlyNumberOfSupportedCharities = 0
        }



        setNotificationAndStatus()

        notifiactionDetails?.setOnClickListener {
            var transaction:Transaction = Transaction()
            transaction.id = notificationObject.donation_id
            transaction.amount = notificationObject.total_donation!!.toDouble()
            transaction.month = notificationObject.month_of_donation!!.toInt()
            transaction.year = notificationObject.year_of_donation!!.toInt()

            startActivity(Intent(context, Report::class.java).putExtra("flag","1").putExtra("transaction", transaction))
        }

        notificationDismiss?.setOnClickListener {
            val state:Status = ApiCalls.UpdateNotification(notificationObject.donation_id)
            if(state.done == true)
            {
                notification?.visibility = View.GONE
            }

        }

        statusDetails?.setOnClickListener {
            var transaction:Transaction = Transaction()
            transaction.id = TransactionTodayObject.transaction_id

            startActivity(Intent(context, Report::class.java).putExtra("flag","2").putExtra("transaction", Transaction()))
        }

        if (UserData.loginUser.has_charities_to_donation == false && UserData.loginUser.has_spending_categories==false)
            showError (view, "You don't support any charities or have any spending categories.")

        else if (UserData.loginUser.has_charities_to_donation == true && UserData.loginUser.has_spending_categories==false)
        {
          Collections.sort(UserData.userDonationCharities.donationCharities  )
            supportedCharities = ArrayList()
            for (i in UserData.userDonationCharities.donationCharities) {
                val c: Charity = Charity()
                c.id =i.id!!
                c.name =i.name!!
                c.description =i.description!!
                c.rate =i.rate!!
                c.totalRevenue =i.total_revenue!!
                c.programExpenses =i.programs_expenses!!
                c.fundraisingExpenses =i.fundraising_expenses!!
                c.totalFunctionalExpences =i.total_functional_expenses!!
                c.categoryName =i.category_name!!
                c.country =i.country!!
                c.supported =true
                i.supported=true

                supportedCharities.add(c)
            }
            showError (view, "No spending categories used for donation.")

        }

        else if (UserData.loginUser.has_charities_to_donation == false && UserData.loginUser.has_spending_categories==true)
        {
            var  b :Double =35.25
            spendingCategories = ArrayList()
            for (categorie in UserData.userSpendingCategories.spendingCategories) {
                val s: SpendingCategory = SpendingCategory()
                s.name = categorie.spending_category_name!!
                s.percentage = categorie.donation_Percentage!!.toDouble()
                s.totalPaid = b
                s.usedForDonation = true
                spendingCategories.add(s)
                b*=1.012

            }
            showError (view, "You don't support any charities.")

        }
        else if (UserData.loginUser.has_charities_to_donation == true && UserData.loginUser.has_spending_categories==true) {

            Collections.sort(UserData.userDonationCharities.donationCharities  )
            supportedCharities = ArrayList()
            for (i in UserData.userDonationCharities.donationCharities) {
                val c: Charity = Charity()
                c.id =i.id!!
                c.name =i.name!!
                c.description =i.description!!
                c.rate =i.rate!!
                c.totalRevenue =i.total_revenue!!
                c.programExpenses =i.programs_expenses!!
                c.fundraisingExpenses =i.fundraising_expenses!!
                c.totalFunctionalExpences =i.total_functional_expenses!!
                c.categoryName =i.category_name!!
                c.country =i.country!!
                c.supported =true
                i.supported=true
                supportedCharities.add(c)
            }
            var  b :Double =35.25
            spendingCategories = ArrayList()
            for (categorie in UserData.userSpendingCategories.spendingCategories) {
                val s: SpendingCategory = SpendingCategory()
                s.name = categorie.spending_category_name!!
                s.percentage = categorie.donation_Percentage!!.toDouble()
                s.totalPaid = b
                s.usedForDonation = true
                spendingCategories.add(s)
                b*=1.012

            }

        }

        /*
       val c1 = Charity()
       c1.name = "Red Cross"
       c1.supported = true
       c1.rate = "4.6"
       c1.categoryName = "Health"

       val c2 = Charity()
       c2.name = "GiveDirectly"
       c2.supported = true
       c2.rate = "4.3"
       c2.categoryName = "Community"

       val c3 = Charity()
       c3.name = "Evidence Action"
       c3.supported = true
       c3.rate = "2.5"
       c3.categoryName = "Arts, and Culture"

       val c4 = Charity()
       c4.name = "Oxfam International"
       c4.supported = true
       c4.rate = "2.1"
       c4.categoryName = "Research"

       supportedCharities = arrayListOf(c1, c2, c3, c4)

       val s1 = SpendingCategory()
       s1.name = "Fast food"
       s1.totalPaid = 399.0
       s1.percentage = 3.1
       s1.usedForDonation = true

       val s2 = SpendingCategory()
       s2.name = "Entertainment"
       s2.totalPaid = 92.2
       s2.percentage = 0.9
       s2.usedForDonation = true

       val s3 = SpendingCategory()
       s3.name = "Shopping"
       s3.totalPaid = 1043.6
       s3.percentage = 4.1
       s3.usedForDonation = true

       val s4 = SpendingCategory()
       s4.name = "Clothes"
       s4.totalPaid = 494.6
       s4.percentage = 4.1
       s4.usedForDonation = false

       spendingCategories = arrayListOf(s1, s2, s3, s4)
*/
        headings = arrayListOf(
                view.findViewById(R.id.home_dashboard_supported_charities) as LinearLayout,
                view.findViewById(R.id.home_dashboard_spending_categories) as LinearLayout
        )

        recyclerViews = arrayListOf(
                view.findViewById(R.id.supported_charities_recycler_view) as RecyclerView,
                view.findViewById(R.id.current_spending_categories_recycler_view) as RecyclerView
        )

        headings[0].setOnClickListener {
            UserData.ISAllCharity = false
            startActivity(Intent(context, CharityList::class.java).putExtra("category", "Supported categories"))
        }
        recyclerViews[0].layoutManager = LinearLayoutManager(context)
        recyclerViews[0].itemAnimator = DefaultItemAnimator()
        var charityAdapter: CharityAdapter = CharityAdapter(context, supportedCharities, "shuffled", true, true ,false)
        Global.dashMySupportedCharitiesAdapter = charityAdapter
        recyclerViews[0].adapter = charityAdapter

        headings[1].setOnClickListener {
            startActivity(Intent(context, SpendingCategories::class.java))
        }
        recyclerViews[1].layoutManager = LinearLayoutManager(context)
        recyclerViews[1].itemAnimator = DefaultItemAnimator()
        var mySpendingCategoriesAdapter: MiniSpendingCategoryAdapter = MiniSpendingCategoryAdapter(context, spendingCategories)
       // Global.dashMySpendingCategoryAdapter = mySpendingCategoriesAdapter
        recyclerViews[1].adapter = mySpendingCategoriesAdapter
    }

    private fun setNotificationAndStatus(){
        if (haveUnreadNotification){
            notification?.visibility = View.VISIBLE
            if (lastTransactionAmount > 0.0 && lastTransactionNumberOfSupportedCharities > 0){
                notificationImage?.setImageDrawable(context.resources.getDrawable(R.drawable.ic_sentiment_very_satisfied_white_48dp))
                notificationTitle?.text = "Thank you for your support!."
                notificationDescription?.text = String.format("You have supported %d charities with amount of $%.2f the last month.", lastTransactionNumberOfSupportedCharities, lastTransactionAmount)
            }
            else {
                notificationImage?.setImageDrawable(context.resources.getDrawable(R.drawable.ic_sentiment_dissatisfied_white_48dp))
                notificationTitle?.text = "No supported charities."
                notificationDescription?.text = "You didn't support any charities the last month."
            }
        }
        else {
            notification?.visibility = View.GONE
        }
        var s  :String = UserData.userDonationCharities.donationCharities.size.toString()
       var r :Double= ( UserData.userSpendingCategories.spendingCategories.size + 0.00)*5.21
        statusDescription?.text = String.format("For this month, you are supporting %s charities with amount of $%.2f",
                s,r

        )
    }

    private fun showError(view: View, message: String){
       // Snackbar.make(notifiactionDetails as View, message, Snackbar.LENGTH_SHORT).show()
       Snackbar.make(myView!!, message, Snackbar.LENGTH_LONG).show()
    }
}
