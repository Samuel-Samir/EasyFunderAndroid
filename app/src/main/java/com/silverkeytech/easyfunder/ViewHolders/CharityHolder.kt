package com.silverkeytech.easyfunder.ViewHolders

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.*
import com.silverkeytech.easyfunder.R
import java.io.Serializable

class CharityHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    var layout: LinearLayout
    var name: TextView
    var imageType: ImageView
    var rate: TextView
    var support: View

    init {
        layout = itemView.findViewById(R.id.charity) as LinearLayout
        name = itemView.findViewById(R.id.charity_name) as TextView
        imageType = itemView.findViewById(R.id.charity_image_type) as ImageView
        rate = itemView.findViewById(R.id.charity_rate) as TextView
        support = itemView.findViewById(R.id.charity_support)
    }
}