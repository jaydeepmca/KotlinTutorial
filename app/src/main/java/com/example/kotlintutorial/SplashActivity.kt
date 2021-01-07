package com.example.kotlintutorial

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView

class SplashActivity : AppCompatActivity() {

    lateinit var appIvLogo: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        appIvLogo = findViewById<AppCompatImageView>(R.id.appIvLogo);
        val hyperspaceJump = AnimationUtils.loadAnimation(this, R.anim.zoom_in)
        appIvLogo.startAnimation(hyperspaceJump)


    /*    window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );*/

        Handler().postDelayed({

            if (AppPreferences.isLogin) {

                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 3000);
    }

    override fun onPause() {
        super.onPause()
        appIvLogo.clearAnimation()
        appIvLogo.animate().cancel()
    }
}