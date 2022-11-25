package com.kt.digicobus.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kt.digicobus.R
import com.kt.digicobus.data.TicketContent

class TicketListAdapter(var context: Context, private val resource: Int,  var ticketContentsList: MutableList<TicketContent>)
    : RecyclerView.Adapter<TicketHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(resource, parent, false)

        return TicketHolder(itemView)
    }

    override fun onBindViewHolder(holder: TicketHolder, position: Int) {

        holder.tv_large_place.text = ticketContentsList[position].tv_large_place
        holder.tv_small_place.text = ticketContentsList[position].tv_small_place
        holder.tv_start_place_name.text = ticketContentsList[position].tv_start_place_name
        holder.tv_start_time.text = ticketContentsList[position].tv_start_time
        holder.tv_end_place_name.text = ticketContentsList[position].tv_end_place_name
        holder.tv_end_time.text = ticketContentsList[position].tv_end_time
    }

    override fun getItemCount(): Int {
        return ticketContentsList.size
    }
}

class TicketHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var tv_large_place: TextView = itemView!!.findViewById(R.id.tv_large_place)
    var tv_small_place: TextView = itemView!!.findViewById(R.id.tv_small_place)
    var tv_start_place_name: TextView = itemView!!.findViewById(R.id.tv_start_place_name)
    var tv_start_time: TextView = itemView!!.findViewById(R.id.tv_start_time)
    var tv_end_place_name: TextView = itemView!!.findViewById(R.id.tv_end_place_name)
    var tv_end_time: TextView = itemView!!.findViewById(R.id.tv_end_time)
}