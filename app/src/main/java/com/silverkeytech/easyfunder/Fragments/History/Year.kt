package com.silverkeytech.easyfunder.Fragments.History

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.silverkeytech.easyfunder.Adapters.HistoryAdapter
import com.silverkeytech.easyfunder.Models.Transaction
import com.silverkeytech.easyfunder.R
import com.silverkeytech.easyfunder.Utilities.Global
import java.util.*

class Year : Fragment(){

    var year: Int = Calendar.getInstance().get(Calendar.YEAR)
    private var recyclerView : RecyclerView? = null
    private var adapter: HistoryAdapter?= null
    private var transactions: ArrayList<Transaction> = ArrayList()
    var position:Int = 0






    private fun setTransactions() {
        // TODO API call in oncreate activity history Done






        ////////
//        val t1 :Transaction = Transaction()
//        val t2 :Transaction = Transaction()
//        val t3 :Transaction = Transaction()
//        val t4 :Transaction = Transaction()
//        val t5 :Transaction = Transaction()
//        val t6 :Transaction = Transaction()
//        val t7 :Transaction = Transaction()
//        val t8 :Transaction = Transaction()
//        val t9 :Transaction = Transaction()
//        val t10 :Transaction = Transaction()
//        val t11 :Transaction = Transaction()
//        val t12 :Transaction = Transaction()
//
//
//        t1.amount = 20.0
//        t1.month = 1
//        t2.amount = 30.0
//        t2.month = 2
//        t3.amount = 0.0
//        t3.month = 3
//
//        t4.amount = 0.0
//        t4.month = 4
//        t5.amount = 30.0
//        t5.month = 5
//        t6.amount = 60.0
//        t6.month = 6
//
//        t7.amount = 20.0
//        t7.month = 7
//        t8.amount = 0.0
//        t8.month = 8
//        t9.amount = 60.0
//        t9.month = 9
//
//        t10.amount = 20.0
//        t10.month = 10
//        t11.amount = 30.0
//        t11.month = 11
//        t12.amount = 60.0
//        t12.month = 12
//        transactions = arrayListOf(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?) : View? {
        val view = inflater!!.inflate(R.layout.fragment_history_year, container, false)
        recyclerView = view.findViewById(R.id.history_recycler_view) as RecyclerView
        //setTransactions()

//        if(Global.contextList.size == 3)
//        {
//            Global.contextList = ArrayList()
//        }
//        Global.contextList.add(view.context)
//        var index:Int = Global.contextList.indexOf(view.context)
        transactions =  Global.allTransactions.get(position)

        adapter = HistoryAdapter(view.context,transactions)
        val layoutManager = LinearLayoutManager(view.context)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.itemAnimator = DefaultItemAnimator()
        recyclerView?.adapter = adapter
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
    }
}
