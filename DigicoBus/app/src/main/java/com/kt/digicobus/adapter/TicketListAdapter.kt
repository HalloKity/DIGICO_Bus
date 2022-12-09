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
import com.kt.digicobus.databinding.FragmentCommuteBusChoiceBinding
import com.kt.digicobus.databinding.FragmentCommuteGoToWorkBinding
import com.kt.digicobus.fragment.commute.CommuteBusChoiceFragment
import com.kt.digicobus.fragment.commute.CommuteFragment
import com.kt.digicobus.fragment.commute.CommuteMainFragment
import kotlinx.android.synthetic.main.listview_ticket.view.*

class TicketListAdapter(var context: Context,var binding:FragmentCommuteGoToWorkBinding, private val resource: Int, var ticketContentsList: MutableList<TicketContent>)
    : RecyclerView.Adapter<TicketHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(resource, parent, false)

        itemView.setOnClickListener{
            //fragment간 이동 with nav_graph action
//            parent.findNavController().navigate(R.id.action_CommuteMainFragment_to_CommuteBusChoiceFragment2)

            //값 넘기는 거 확인해보기
        }
        return TicketHolder(itemView)
    }

    override fun onBindViewHolder(holder: TicketHolder, position: Int) {

        holder.constraint.setOnClickListener{
            ticketContentsList[position].isClick = true
            it.setBackgroundColor(context.resources.getColor(R.color.mint_choice))
            binding.tvStartPlace.text = ticketContentsList[position].mainPlace

            for(i in 0 until ticketContentsList.size){
                if(i != position){
                    ticketContentsList[i].isClick = false
                }
            }

            notifyDataSetChanged()
        }

        if(!ticketContentsList[position].isClick){
            holder.constraint.setBackgroundColor(context.resources.getColor(R.color.white))
        }else if(ticketContentsList[position].isClick){
            holder.constraint.setBackgroundColor(context.resources.getColor(R.color.mint_choice))
        }

//        holder.icon_favorite.setOnClickListener{
//            if(!ticketContentsList[position].isFavorite){
//                holder.icon_favorite.setImageResource(R.drawable.icon_favorite)
//                ticketContentsList[position].isFavorite = true
//                ticketContentsList.add(0,ticketContentsList.removeAt(position))
//            }else{
//                holder.icon_favorite.setImageResource(R.drawable.icon_nonfavorite)
//                ticketContentsList[position].isFavorite = false
//                ticketContentsList.add(ticketContentsList.lastIndex,ticketContentsList.removeAt(position))
//            }
//
//            notifyDataSetChanged()
//        }

        if(ticketContentsList[position].isFavorite){
            holder.favorite.setImageResource(R.drawable.icon_yellow_star)
        }

        holder.line.text = ticketContentsList[position].line
        holder.mainPlace.text = ticketContentsList[position].mainPlace
        holder.detailPlace.text = ticketContentsList[position].detailPlace
        holder.departureTime.text = ticketContentsList[position].departureTime

        // 더보기 클릭시 하단 모달창
        holder.more.setOnClickListener{

        }
    }

    override fun getItemCount(): Int {
        return ticketContentsList.size
    }
}

class TicketHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var constraint: ConstraintLayout = itemView.findViewById(R.id.constraint)

    var favorite: ImageView = itemView.findViewById(R.id.icon_star)
    var line: TextView = itemView.findViewById(R.id.tv_line)
    var mainPlace: TextView = itemView.findViewById(R.id.tv_main_place)
    var detailPlace: TextView = itemView.findViewById(R.id.tv_detail_place)
    var departureTime: TextView = itemView.findViewById(R.id.tv_departure_time)
    var more: ImageView = itemView.findViewById(R.id.icon_more)
}