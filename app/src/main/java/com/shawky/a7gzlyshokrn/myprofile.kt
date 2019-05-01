package com.shawky.a7gzlyshokrn

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.telecom.Call
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_myprofile.*

class myprofile : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_myprofile)
        val FB=findViewById<ImageView>(R.id.fp)
        val callme =findViewById<ImageView>(R.id.call)
        val gmail=findViewById<ImageView>(R.id.gmail)

        FB.setOnClickListener {
            startActivity(openFacebook(this))
        }
        callme.setOnClickListener {
            val intent=Intent(Intent.ACTION_CALL)
            intent.data= Uri.parse("tel:"+"01228684181")
            if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CALL_PHONE),2)
            }else
            startActivity(intent)
        }
        textView8.setOnClickListener {
            textView8.text="انا محمد شوقي عندي 20 سنة من الاسكندرية انا مطور البرنامج دا و بدرس ف كلية الحاسبات و المعلومات تقدر تتابعني عالفيسبوك او تكلمني لو احتجت اي حاجة ف خصوص برامج الاندرويد"
        }
    }
    fun openFacebook(context: Context): Intent {
        try {
            context.getPackageManager().getPackageInfo("com.facebook.katana", 0)
            return Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/100000489100011"))
        } catch (e: Exception) {
            return Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.facebook.com/m.shawky150"))
        }
    }
}
