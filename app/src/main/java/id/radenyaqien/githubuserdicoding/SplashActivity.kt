package id.radenyaqien.githubuserdicoding

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    private val splashTime = 3000L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            goTomain()
        }, splashTime)
    }

    private fun goTomain(){
        startActivity(Intent(applicationContext, MainActivity::class.java))
        finish()
    }
}