package com.ibrahimdemir.moviesapp.view

import android.content.Intent
import android.os.Handler
import com.ibrahimdemir.moviesapp.R
import com.ibrahimdemir.moviesapp.base.BaseActivity

class SplashScreenActivity : BaseActivity() {

    override fun getLayoutId(): Int = R.layout.activity_splash_screen

    override fun initView() {
        Handler().postDelayed({
            val mainIntent = Intent(this, SearchActivity::class.java)
            this.startActivity(mainIntent)
            finish()
        }, SPLASH_DISPLAY_TIME)
    }

    companion object {
        const val SPLASH_DISPLAY_TIME = 2000L
    }
}