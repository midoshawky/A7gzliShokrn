package com.shawky.a7gzlyshokrn

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.listitem.view.*

class ListAdapter:BaseAdapter
{
    var ListItems=ArrayList<List<Any?>>()
    var context:Context?=null
    constructor(context: Context,ListItems:ArrayList<List<Any?>>):super()
    {
        this.context=context
        this.ListItems=ListItems
    }
    @SuppressLint("ViewHolder")
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val Teams=this.ListItems[p0]
        val ListInflat=context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val Infview=ListInflat.inflate(R.layout.listitem,null)

        Infview.TeamTv.text=Teams.TeamName
        Infview.NameTv.text=Teams.TeamLeader
        Infview.TimeTv.text=Teams.Timeing
        return Infview
    }

    override fun getItem(p0: Int): Any {
        return ListItems[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return ListItems.size
    }

}