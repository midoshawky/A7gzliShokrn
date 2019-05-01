package com.shawky.a7gzlyshokrn

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.Snackbar
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView

class adminlogin : AppCompatActivity() {
    lateinit var MyPref:SharedPreferences
    lateinit var UserName:TextView
    lateinit var Pass:TextView
    val myShPref="mypref"
    val nameKey="NameKey"
    val passKey="PassKey"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adminlogin)
        UserName=findViewById(R.id.UserName)
        Pass=findViewById(R.id.pass)

        val LoginBtn=findViewById<View>(R.id.loginbtn) as Button
        val Relative=findViewById<View>(R.id.RelativeID) as ConstraintLayout



        MyPref=getSharedPreferences(myShPref,Context.MODE_PRIVATE)
        if (MyPref.contains(nameKey))
        {
            UserName.text=MyPref.getString(nameKey,"")

        }
        if (MyPref.contains(passKey))
        {
            Pass.text=MyPref.getString(passKey,"")
        }
        val Name=UserName.text.toString()
        val Password=Pass.text.toString()
        val Edit=MyPref.edit()

        Edit.putString(nameKey,Name)
        Edit.putString(passKey,Password)
        Edit.apply()

        LoginBtn.setOnClickListener {
            if (!TextUtils.isEmpty(UserName.text.toString())||!TextUtils.isEmpty(Pass.text.toString()))
            {
                if (UserName.text.toString().equals("Admin")&&Pass.text.toString().equals("Admin"))
                {
                    startActivity(Intent(this,pending::class.java))
                }else
                {
                    Snackbar.make(Relative,"اتأكد من صحة بياناتك الاول",Snackbar.LENGTH_LONG).show()
                }

            }else
            {
                UserName.error="دخل اسم المستخدم الخاص بيك"
                Pass.error="متنساش كلمة السر"
            }
            }
        }

    }
