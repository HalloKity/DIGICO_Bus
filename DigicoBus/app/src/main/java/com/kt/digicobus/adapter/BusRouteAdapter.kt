package com.kt.digicobus.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kt.digicobus.R
import com.kt.digicobus.data.model.BusEntireRoute
import com.kt.digicobus.data.model.ReserveSearch
import java.util.Date

class BusRouteAdapter(var context: Context, private val resource: Int,  var busEntireRouteList: MutableList<BusEntireRoute>,
                      var ticketDate: String)
    : RecyclerView.Adapter<BusRouteHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusRouteHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(resource, parent, false)

        return BusRouteHolder(itemView)
    }

    override fun onBindViewHolder(holder: BusRouteHolder, position: Int) {
        val item = busEntireRouteList[position]
        holder.routeName.text = item.mainPlace
        holder.departureTime.text = item.departureTime

        var today = Date()
        var ticketList = ticketDate.split("-")
        var ticketYear = ticketList[0]
        var ticketMonth = ticketList[1]
        var ticketDay = ticketList[2]
        println("today ${today.year} ${today.month}  ${today.date} ////// ${ticketYear} ${ticketMonth} ${ticketDay}")
        // 날짜 같고 포지션 1번째면
        if(2022 == ticketYear.toInt() && today.month+1 == ticketMonth.toInt() && today.date == ticketDay.toInt()
            && position == 1){
            // 출근일 때만
            val time = item.departureTime.split(":")
            val hour = time[0]
            val minute = time[1]
            if(hour.toInt() < 12){
                holder.icon.visibility = View.VISIBLE
//                holder.icon.siz
            }
        }

        if(position == 0){
            holder.topLine.visibility = View.GONE
            holder.bottomLine.visibility = View.VISIBLE
        }else if(position == busEntireRouteList.size-1){
            holder.topLine.visibility = View.VISIBLE
            holder.bottomLine.visibility = View.GONE
        }


    }

    override fun getItemCount(): Int {
        return busEntireRouteList.size
    }
}

class BusRouteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val place: TextView = itemView!!.findViewById(R.id.tv_route_name)
    val time: TextView = itemView!!.findViewById(R.id.tv_departure_time)
    val routeName: TextView = itemView!!.findViewById(R.id.tv_route_name)
    val departureTime: TextView = itemView!!.findViewById(R.id.tv_departure_time)

    val topLine: View = itemView!!.findViewById(R.id.line_top)
    val bottomLine: View = itemView!!.findViewById(R.id.line_bottom)

    var icon: ImageView = itemView!!.findViewById(R.id.icon_bus_location)
}