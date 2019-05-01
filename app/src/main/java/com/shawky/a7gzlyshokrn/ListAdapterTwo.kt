package com.shawky.a7gzlyshokrn

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.support.v7.widget.CardView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.pendinglist.view.*

class ListAdapterTwo: BaseAdapter
{
    var ListItms:ArrayList<Plist>
    var context: Context?=null
    constructor(context: Context, ListItms:ArrayList<Plist>):super()
    {
        this.context=context
        this.ListItms=ListItms
    }
    @SuppressLint("ViewHolder")
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val PTeams=this.ListItms[p0]
        val ListInflat:LayoutInflater
            ListInflat=context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val Infview=ListInflat.inflate(R.layout.pendinglist,null)
        Infview!!.PTeamTv.text=PTeams.PTeamName
        Infview.PNameTv.text=PTeams.PTeamLeader
        Infview.PTimeTv.text=PTeams.PTimeing
        Infview.phonetv.text=PTeams.PPhonenum
        Infview.RegCodeTV.text=PTeams.RegCode
        var anim: Animation? =null
        val CV=Infview.cv as CardView
        anim=AnimationUtils.loadAnimation(context,R.anim.slider) as Animation
        Infview.startAnimation(anim)
        return Infview
    }

    override fun getItem(p0: Int): Any {
        return ListItms[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return ListItms.size
    }

}