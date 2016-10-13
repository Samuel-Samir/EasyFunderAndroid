package com.silverkeytech.easyfunder.Activities
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import com.silverkeytech.easyfunder.Adapters.SpendingCategoryAdapter
import com.silverkeytech.easyfunder.R
class Percentage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_percentage)
        val seekBar: SeekBar = findViewById(R.id.dialog_seek_bar) as SeekBar
        val percentageTextView: TextView = findViewById(R.id.dialog_percentage) as TextView
        val setPercentage = findViewById(R.id.dialog_support) as Button
        var percentage: Double = 0.00
        setPercentage.setOnClickListener {
            SpendingCategoryAdapter.PERCENTAGE = percentage
            SpendingCategoryAdapter.update()
            finish()
        }
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            internal var seekBarProgress = 0
            override
            fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                seekBarProgress = progress
            }
            override
            fun onStartTrackingTouch(seekBar: SeekBar) {
            }
            override
            fun onStopTrackingTouch(seekBar: SeekBar) {
                percentage = 0.0 + (seekBarProgress  / 5.0)
                percentageTextView.text = String.format("%.1f", percentage) + '%'
            }
        })
    }
}