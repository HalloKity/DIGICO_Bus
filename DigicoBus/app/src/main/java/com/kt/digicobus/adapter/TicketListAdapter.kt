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

class TicketListAdapter(var context: Context, private val resource: Int,  var ticketContentsList: MutableList<TicketContent>)
    : RecyclerView.Adapter<TicketHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(resource, parent, false)

        itemView.setOnClickListener{
            //fragment간 이동 with nav_graph action
            parent.findNavController().navigate(R.id.action_CommuteMainFragment_to_CommuteBusChoiceFragment2)

            //값 넘기는 거 확인해보기
        }
        return TicketHolder(itemView)
    }

    override fun onBindViewHolder(holder: TicketHolder, position: Int) {

        holder.icon_favorite.setOnClickListener{
            if(!ticketContentsList[position].isFavorite){
                holder.icon_favorite.setImageResource(R.drawable.icon_favorite)
                ticketContentsList[position].isFavorite = true
                ticketContentsList.add(0,ticketContentsList.removeAt(position))
            }else{
                holder.icon_favorite.setImageResource(R.drawable.icon_nonfavorite)
                ticketContentsList[position].isFavorite = false
                ticketContentsList.add(ticketContentsList.lastIndex,ticketContentsList.removeAt(position))
            }

            notifyDataSetChanged()
        }

        if(ticketContentsList[position].isFavorite){
            holder.icon_favorite.setImageResource(R.drawable.icon_favorite)
        }else if(!ticketContentsList[position].isFavorite){
            holder.icon_favorite.setImageResource(R.drawable.icon_nonfavorite)
        }

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
    var constraint : ConstraintLayout = itemView!!.findViewById(R.id.constraint)

    var icon_favorite: ImageView = itemView!!.findViewById(R.id.icon_favorite)
    var tv_large_place: TextView = itemView!!.findViewById(R.id.tv_large_place)
    var tv_small_place: TextView = itemView!!.findViewById(R.id.tv_small_place)
    var tv_start_place_name: TextView = itemView!!.findViewById(R.id.tv_start_place_name)
    var tv_start_time: TextView = itemView!!.findViewById(R.id.tv_start_time)
    var tv_end_place_name: TextView = itemView!!.findViewById(R.id.tv_end_place_name)
    var tv_end_time: TextView = itemView!!.findViewById(R.id.tv_end_time)


}