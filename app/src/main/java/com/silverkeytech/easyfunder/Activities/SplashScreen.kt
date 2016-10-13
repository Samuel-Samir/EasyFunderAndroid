package com.silverkeytech.easyfunder.Activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.silverkeytech.easyfunder.R

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        Handler().postDelayed(
                {
                    val intent = Intent(this@SplashScreen, IntroSlider::class.java)
                    intent.putExtra("About", true)
                    startActivity(intent)
                    finish()
                }
                , 2000
        )
    }
}
