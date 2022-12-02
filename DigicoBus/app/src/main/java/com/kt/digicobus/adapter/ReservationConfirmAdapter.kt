package com.kt.digicobus.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.kt.digicobus.R
import com.kt.digicobus.data.TicketContent
import com.kt.digicobus.fragment.commute.CommuteBusChoiceFragment
import com.kt.digicobus.fragment.commute.CommuteFragment
import com.kt.digicobus.fragment.commute.CommuteMainFragment
import kotlinx.android.synthetic.main.listview_ticket.view.*

class ReservationConfirmAdapter(var context: Context, private val resource: Int,  var ticketContentsList: MutableList<TicketContent>)
    : RecyclerView.Adapter<ReservationHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(resource, parent, false)

        return ReservationHolder(itemView)
    }

    override fun onBindViewHolder(holder: ReservationHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return ticketContentsList.size
    }
}

class ReservationHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tv_date: TextView = itemView!!.findViewById(R.id.tv_date)
    val img_qr_code: ImageView = itemView!!.findViewById(R.id.img_qr_code)
    val tv_to_place: TextView = itemView!!.findViewById(R.id.tv_to_place)
    val tv_from_place: TextView = itemView!!.findViewById(R.id.tv_from_place)
    val tv_to_time: TextView = itemView!!.findViewById(R.id.tv_to_time)
    val tv_from_time: TextView = itemView!!.findViewById(R.id.tv_from_time)
    val google_map: ImageView = itemView!!.findViewById(R.id.google_map)
}