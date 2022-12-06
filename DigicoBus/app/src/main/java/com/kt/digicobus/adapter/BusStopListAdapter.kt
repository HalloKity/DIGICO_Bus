package com.kt.digicobus.adapter

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.kt.digicobus.R
import com.kt.digicobus.data.BusStopContent
import com.kt.digicobus.dialog.MyBottomDialogStopLocation
import com.naver.maps.geometry.LatLng
import androidx.core.view.get
import com.kt.digicobus.data.data
import com.kt.digicobus.data.data.Companion.busChoiceInfo
import com.kt.digicobus.databinding.FragmentCommuteBusChoiceBinding
import kotlinx.android.synthetic.main.listview_detail_bus_info.view.*

class BusStopListAdapter(var context: Context, var binding: FragmentCommuteBusChoiceBinding, private val resource: Int, var busStopList: MutableList<BusStopContent>)
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
        holder.tv_bus_stop_location.text = busStopList[position].tv_bus_stop_location
        holder.tv_departure_time.text = busStopList[position].tv_departure_time

        holder.constraint.setOnClickListener{
            busStopList[position].isClick = true
            holder.constraint.setBackgroundColor(context.getColor(R.color.mint_dark))

            for(i in 0 until busStopList.size){
                if(i != position){
                    busStopList[i].isClick = false
                }
            }
            binding.btnReserve.isEnabled = true

            notifyDataSetChanged()
        }

        if(!busStopList[position].isClick){
            holder.constraint.setBackgroundColor(context.getColor(R.color.white))
        }else if(busStopList[position].isClick){
            holder.constraint.setBackgroundColor(context.getColor(R.color.mint_dark))
        }


//        for(i in busStopList){
//            if(busStopList[position].isClick){
//                holder.constraint.setBackgroundColor(context.getColor(R.color.mint_dark))
//            }else if(!busStopList[position].isClick){
//                holder.constraint.setBackgroundColor(context.getColor(R.color.white))
//            }
//        }
//        notifyDataSetChanged()

        //지도 클릭시 넘어감
        holder.btn_map.setOnClickListener {

            val bottomDialog = MyBottomDialogStopLocation(
                busStopList[position].tv_bus_stop_location,
                busStopList[position].tv_departure_time,
                LatLng(
                    busStopList[position].tv_location_latitude,
                    busStopList[position].tv_location_longitude
                )
            )

            bottomDialog.show(
                (holder.itemView.context as FragmentActivity).supportFragmentManager,
                bottomDialog.tag
            )
        }
    }

    override fun getItemCount(): Int {
        return busStopList.size
    }
}

class BusStopHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var tv_bus_stop_location: TextView = itemView!!.findViewById(R.id.tv_bus_stop_location)
    var tv_departure_time: TextView = itemView!!.findViewById(R.id.tv_departure_time)
    var btn_map: ImageView = itemView!!.findViewById(R.id.btn_map)
    var constraint: ConstraintLayout = itemView!!.findViewById(R.id.constraint_bus_detail)
}