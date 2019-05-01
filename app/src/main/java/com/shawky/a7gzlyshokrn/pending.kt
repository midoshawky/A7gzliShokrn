package com.shawky.a7gzlyshokrn

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.view.*
import android.widget.*
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_pending.*
import kotlinx.android.synthetic.main.listitem.view.*
import kotlinx.android.synthetic.main.pendinglist.view.*
import java.util.*

class pending : AppCompatActivity() {
    lateinit var ListItms: ArrayList<Plist>
    lateinit var arrlst:ArrayList<List<Any?>>
    var PendAdapter: ListAdapterTwo? = null
    lateinit var mDatabase: DatabaseReference
    lateinit var AcmDatabase:DatabaseReference

    var PListview:ListView ?= null
    var remove:Int ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pending)
        setSupportActionBar(toolbar)
         PListview = include.findViewById<View>(R.id.PendingLv) as ListView
        val LayoutBG=include.findViewById<LinearLayout>(R.id.LLay)
        val Coolay=findViewById<CoordinatorLayout>(R.id.ConLay)
        supportActionBar!!.title = ""
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mDatabase = FirebaseDatabase.getInstance().getReference("Pending")
        AcmDatabase = FirebaseDatabase.getInstance().getReference("Accepted")

        ListItms = arrayListOf()

        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    ListItms.clear()
                    for (init in snapshot.children) {
                        ListItms.add(Plist(init.child("TeamName").getValue(true).toString()
                                , init.child("TeamLeader").getValue(true).toString()
                                , init.child("StartTime").getValue(true).toString() + " -> " + init.child("EndTime").getValue(true).toString()
                                , init.child("Phone").getValue(true).toString(), init.child("RegCode").getValue(true).toString()))
                    }
                    PendAdapter = ListAdapterTwo(this@pending, ListItms)
                    PListview!!.adapter = PendAdapter
                    PListview!!.setOnItemClickListener { adapterView, view, i, l ->
                        val intent=Intent(Intent.ACTION_CALL)

                        if (ActivityCompat.checkSelfPermission(this@pending,android.Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED)
                         {
                             ActivityCompat.requestPermissions(this@pending, arrayOf(android.Manifest.permission.CALL_PHONE),1)
                         }else
                         {
                             intent.data= Uri.parse("tel:"+ListItms[l.toInt()].PPhonenum!!.toString())
                             startActivity(intent)
                         }
                    }
                }
            }
        })
        registerForContextMenu(PListview)
        val InternetChk=ConnectionChk()
        if (InternetChk==false)
        {
            Snackbar.make(Coolay,"مفيش اتصال بالنت جرب مرة تانية لما تكون موصل النت",Snackbar.LENGTH_LONG).show()
        }
    }

    fun ConnectionChk():Boolean
    {
        val CM:ConnectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo=CM.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menu!!.add(0, v!!.id, 0, "اقبل طلب الحجز")
        menu.add(0, v.id, 0, "امسح طلب الحجز")
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        val itemInfo:AdapterView.AdapterContextMenuInfo= item!!.menuInfo as AdapterView.AdapterContextMenuInfo
        when (item!!.title) {
            "اقبل طلب الحجز" -> {
                //arrlst.add(List(ListItms[itemInfo.position].PTeamName!!.toString(),ListItms[itemInfo.position].PTeamLeader!!.toString(),ListItms[itemInfo.position].PTimeing!!.toString()))
                val UID= UUID.randomUUID().mostSignificantBits.toString()
                val DataSend=AcmDatabase.child(UID)
                DataSend.child("TeamName").setValue(ListItms[itemInfo.position].PTeamName!!.toString())
                DataSend.child("TeamLeader").setValue(ListItms[itemInfo.position].PTeamLeader!!.toString())
                DataSend.child("Timing").setValue(ListItms[itemInfo.position].PTimeing!!.toString())
            }
            "امسح طلب الحجز" -> {
                itemInfo.position
                ListItms.removeAt(itemInfo.position)
                PendAdapter = ListAdapterTwo(this@pending, ListItms)
                PListview!!.adapter = PendAdapter

            }
        }
        return true
    }
}

