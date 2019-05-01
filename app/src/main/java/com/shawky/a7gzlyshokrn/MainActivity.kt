package com.shawky.a7gzlyshokrn

import android.app.Dialog
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.support.annotation.IntegerRes
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBar
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.regdialog.*
import kotlinx.android.synthetic.main.regdialog.view.*
import org.w3c.dom.Text
import java.util.*
import java.util.zip.Inflater

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var mDataBase: DatabaseReference
    lateinit var AcDatabase: DatabaseReference
    lateinit var Listitms: ArrayList<List<Any?>>
    var adapter: ListAdapter? = null
    val Sendintent:Intent=Intent(Intent.ACTION_SEND)
    var DublicateTime:Boolean?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar!!.setTitle("")
        Listitms = arrayListOf()
        val HeadList = arrayListOf<Int>(R.drawable.three, R.drawable.one, R.drawable.two)
        val RegFab = findViewById<View>(R.id.fab)
        val listView = include2.findViewById<View>(R.id.Listv) as ListView
        val hr = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val min = Calendar.getInstance().get(Calendar.MINUTE)
        val Head = findViewById<ImageView>(R.id.header)

        var imgCount=0

        Head.setOnClickListener {
            while (imgCount<HeadList.size-1)
            {
                Head.setImageResource(HeadList[imgCount])
                imgCount++
                if(imgCount==2)
                {
                    imgCount=0
                    Head.setImageResource(R.drawable.a7gz)
                }
            }
        }

        mDataBase = FirebaseDatabase.getInstance().getReference("Pending")
        AcDatabase = FirebaseDatabase.getInstance().getReference("Accepted")
        mDataBase.orderByPriority()
        AcDatabase.orderByPriority()
        AcDatabase.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    Listitms.clear()
                    for (init in snapshot.children) {
                        if (hr >= 2 && hr <= 5) {

                            mDataBase.child("TeamName").parent.removeValue()
                            AcDatabase.child("TeamName").parent.removeValue()
                        } else
                            Listitms.add(List(init.child("TeamName").getValue(true).toString()
                                    , init.child("TeamLeader").getValue(true).toString()
                                    , init.child("Timing").getValue(true).toString()))
                    }
                    adapter = ListAdapter(this@MainActivity, Listitms)
                    listView.adapter = adapter

                }
            }
        })

        RegFab.setOnClickListener {
            if (hr >= 2 && hr <= 5) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    RegFab.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.DeActive))
                }
                Toast.makeText(this, "ألملعب قافل حاليا بيفتح من 5 الصبح لحد الساعة 2 بليل", Toast.LENGTH_LONG).show()
            } else
                RegDialog(listView)
        }
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)


    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.admin -> {
                startActivity(Intent(this, adminlogin::class.java))
                return true
            }
            R.id.dev -> {
                startActivity(Intent(this, myprofile::class.java))
            }
            R.id.nav_send -> {
                Sender()
            }
        }
        return true
    }

    fun RegDialog(lv: ListView) {
        val regDialog = Dialog(this)
        regDialog.setContentView(R.layout.regdialog)
        val inflater = regDialog.layoutInflater.inflate(R.layout.regdialog, null)
        regDialog.setContentView(inflater)
        regDialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        regDialog.show()

        val TeamN = inflater.findViewById<View>(R.id.tname) as EditText
        val Teaml = inflater.findViewById<View>(R.id.tleader) as EditText
        val phoneNum = inflater.findViewById<View>(R.id.phone) as EditText
        val Timing = inflater.findViewById<View>(R.id.timeing) as TextView
        val TimePick = inflater.findViewById<View>(R.id.timepick) as TextView
        val RegBtn = inflater.findViewById<View>(R.id.regbtn) as Button
        val TimePicker = inflater.findViewById<View>(R.id.TimePick) as TimePicker
        val CodeTV = inflater.findViewById<View>(R.id.code) as TextView
        var strt: Int? = null
        var end: Int? = null
        var Pricing: Int? = null
        TimePick.setOnClickListener {
            TimePicker.visibility = View.VISIBLE
        }
        TimePicker.setOnTimeChangedListener(object : TimePicker.OnTimeChangedListener {
            override fun onTimeChanged(p0: TimePicker?, p1: Int, p2: Int) {
                TimePick.text = "$p1 " + " : " + " $p2"
                strt = p1
                mDataBase.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onDataChange(snapshot: DataSnapshot) {

                    for (init in snapshot.children) {
                        if (init.child("StartTime").getValue(true).toString().equals(TimePick.text.toString())) {
                            Toast.makeText(this@MainActivity, "اختار وقت تاني لان الوقت دا في حد مسجل قبلك", Toast.LENGTH_LONG).show()
                            DublicateTime=true
                            break
                        } else {
                            DublicateTime=false
                            break
                        }
                    }
                }
            })
            }
        })

        TimePicker.setOnClickListener {
            TimePicker.visibility = View.INVISIBLE
        }
        Timing.setOnClickListener {
            TimePicker.visibility = View.VISIBLE
            TimePicker.setOnTimeChangedListener(object : TimePicker.OnTimeChangedListener {
                override fun onTimeChanged(p0: TimePicker?, p1: Int, p2: Int) {
                    Timing.text = "$p1 " + " : " + " $p2"
                    end = p1
                    Pricing = Math.abs((end!! - strt!!) * 70)
                }
            })
        }

        RegBtn.setOnClickListener {

            if (TextUtils.isEmpty(TeamN.text.toString()) || TextUtils.isEmpty(Teaml.text.toString())) {
                TeamN.error = "متسيبش اسم الفريق فاضي"
                Teaml.error = "متسيبش اسمك فاضي"
            } else if (DublicateTime!!){
                Toast.makeText(this@MainActivity, "اختار وقت تاني لان الوقت دا في حد مسجل قبلك", Toast.LENGTH_LONG).show()
            }else if(!DublicateTime!!) {
                val UID = UUID.randomUUID().mostSignificantBits.toString()
                val RegCode = 100000 + (Math.random() * 100000).toInt()
                CodeTV.text = RegCode.toString()
                val DataSend = mDataBase.child(UID)
                DataSend.child("TeamName").setValue(TeamN.text.toString())
                DataSend.child("TeamLeader").setValue(Teaml.text.toString())
                DataSend.child("StartTime").setValue(TimePick.text.toString())
                DataSend.child("EndTime").setValue(Timing.text.toString())
                DataSend.child("Phone").setValue(phoneNum.text.toString())
                DataSend.child("RegCode").setValue(RegCode)
                Toast.makeText(this@MainActivity, "تم التسجيل بنجاح", Toast.LENGTH_LONG).show()
            }

        }
    }

    fun Sender()
    {
        Sendintent.setType("text/plain")
        startActivity(Intent.createChooser(Sendintent,"Send Using"))
    }
}
