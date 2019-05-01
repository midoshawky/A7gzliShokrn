package com.shawky.a7gzlyshokrn

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView

class splash : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val Logo=findViewById<ImageView>(R.id.logo)
        val animate:Animation=AnimationUtils.loadAnimation(this,R.anim.slider)
        Logo.startAnimation(animate)
        try {
           val Splasher=Thread(object :Thread(){
               override fun run() {
                   Thread.sleep(3500)
                   startActivity(Intent(this@splash,MainActivity::class.java))
               }
           })
            Splasher.start()
       }catch (e:Exception)
       {
           e.printStackTrace()
       }
    }
}
