package com.kt.digicobus.adapter

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kt.digicobus.R
import com.kt.digicobus.data.TicketContent
import com.kt.digicobus.data.data
import com.kt.digicobus.dialog.BottomSheetQrcodeHelp
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper

class BusRouteAdapter(var context: Context, private val resource: Int,  var ticketContentsList: MutableList<TicketContent>)
    : RecyclerView.Adapter<BusRouteHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusRouteHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(resource, parent, false)

        return BusRouteHolder(itemView)
    }

    override fun onBindViewHolder(holder: BusRouteHolder, position: Int) {

        if(position == 0){
            holder.topLine.visibility = View.GONE
            holder.bottomLine.visibility = View.VISIBLE
        }else if(position == ticketContentsList.size-1){
            holder.topLine.visibility = View.VISIBLE
            holder.bottomLine.visibility = View.GONE
        }


    }

    override fun getItemCount(): Int {
        return ticketContentsList.size
    }
}

class BusRouteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val place: TextView = itemView!!.findViewById(R.id.tv_route_name)
    val time: TextView = itemView!!.findViewById(R.id.tv_departure_time)

    val topLine: View = itemView!!.findViewById(R.id.line_top)
    val bottomLine: View = itemView!!.findViewById(R.id.line_bottom)
}