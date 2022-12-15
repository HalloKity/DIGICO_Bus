package com.kt.digicobus.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.kt.digicobus.R
import com.kt.digicobus.data.BusStopContent
import kotlinx.android.synthetic.main.listview_detail_bus_info.view.*

class BusStopListAdapter(var context: Context, private val resource: Int, var busStopList: MutableList<BusStopContent>,
                            val onClickItem: (Int, Boolean) -> Unit)
    : RecyclerView.Adapter<BusStopHolder>() {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusStopHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(resource, parent, false)

        itemView.setOnClickListener{
//            it.constraint.setBackgroundColor(context.getColor(R.color.mint_dark))
        }

        return BusStopHolder(itemView)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: BusStopHolder, position: Int) {
        holder.tv_bus_stop_location.text = busStopList[position].busStopLocation
        holder.tv_departure_time.text = busStopList[position].departureTime

        // 노선 line
        if(position == 0) holder.line_route_top.visibility = View.INVISIBLE
        else if (position == busStopList.size-1) holder.line_route_bottom.visibility = View.INVISIBLE
        else {
            holder.line_route_top.visibility = View.VISIBLE
            holder.line_route_bottom.visibility = View.VISIBLE
        }

        holder.constraint.setOnClickListener{
            busStopList[position].isClick = !busStopList[position].isClick

            for(i in 0 until busStopList.size){
                if(i != position){
                    busStopList[i].isClick = false
                }
            }
            notifyDataSetChanged()

            onClickItem(position, busStopList[position].isClick)
        }

        if(!busStopList[position].isClick){
            holder.constraint.setBackgroundColor(context.getColor(R.color.white))
        }else if(busStopList[position].isClick){
            holder.constraint.setBackgroundColor(context.getColor(R.color.mint_dark))
        }
    }

    override fun getItemCount(): Int {
        return busStopList.size
    }
}

class BusStopHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var tv_bus_stop_location: TextView = itemView!!.findViewById(R.id.tv_bus_stop_location)
    var tv_departure_time: TextView = itemView!!.findViewById(R.id.tv_departure_time)
    var line_route_top: View = itemView!!.findViewById(R.id.line_route_top)
    var line_route_bottom: View = itemView!!.findViewById(R.id.line_route_bottom)
    var constraint: ConstraintLayout = itemView!!.findViewById(R.id.constraint_bus_detail)
}