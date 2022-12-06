package com.kt.digicobus.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import com.kt.digicobus.R
import com.kt.digicobus.data.BusStopContent
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
//            holder.constraint.setBackgroundColor(context.getColor(R.color.mint))

            //선택된 적이 있는 것
            //기존 선택된 색의 배경을 다시 하얀색으로 바꾸고, 선택된 곳의 색을 변환
            if(busChoiceInfo != -1){
                //기존 선택되었던 곳의 배경색을 하얀색으로 바꿈


                //현재 선택하 값 저장
                busChoiceInfo = position
                //선택한 곳의 배경을 바꿈
                holder.constraint.setBackgroundColor(context.getColor(R.color.mint_dark))
            }
            //처음에 -1 이었을 경우 == 아무것도 선택 안했을 경우
            else if(busChoiceInfo == -1){
                //선택한 곳의 배경을 바꾸고 버튼 활성화
                holder.constraint.setBackgroundColor(context.getColor(R.color.mint_dark))
                binding.btnReserve.isEnabled = true
                busChoiceInfo = position
            }
        }

        //지도 클릭시 넘어감
        holder.btn_map.setOnClickListener{
            println("지도 클릭")
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
    var constraint: ConstraintLayout = itemView!!.findViewById(R.id.constraint)
}