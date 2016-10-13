package com.silverkeytech.easyfunder.Fragments.Time

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.widget.DatePicker
import com.silverkeytech.easyfunder.Models.ResponsePayload.TimeNow
import com.silverkeytech.easyfunder.Utility.ApiCalls
import com.silverkeytech.easyfunder.Utility.UserData
import java.util.*

/**
 * Created by Ahmed on 9/20/2016.
 */

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // Create a new instance of DatePickerDialog and return it
        return DatePickerDialog(activity, this, year, month, day)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {

        var now:TimeNow = TimeNow()

        now.user_id = UserData.loginUser.id
        now.seconds = ""
        now.minutes = ""
        now.hours = ""
        now.day = day.toString()
        now.month = month.toString()
        now.year = year.toString()

        ApiCalls.SetTime(now)



    }
}