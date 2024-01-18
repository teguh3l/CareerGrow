package com.notfoundteam.careergrowapp.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.notfoundteam.careergrowapp.databinding.ActivitySplashBinding
import com.notfoundteam.careergrowapp.ui.main.MainActivity

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    @Override
    override fun onCreate(saveInstanceState: Bundle?) {
        super.onCreate(saveInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val thread: Thread = object : Thread() {
            override fun run() {
                try {
                    sleep(2000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                } finally {
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    finish()
                }
            }
        }
        thread.start()
    }

}