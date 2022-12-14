package com.kt.digicobus.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.kt.digicobus.R
import com.kt.digicobus.activity.FAQActivity
import com.kt.digicobus.activity.NoticeActivity
import com.kt.digicobus.activity.ReservationDetailsActivity
import com.kt.digicobus.activity.SettingActivity

class MoreListAdapter(var context: Context, private val resource: Int,var icon_list: MutableList<Int>, var icon_name_list: MutableList<String>)
    : RecyclerView.Adapter<CurOrderHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurOrderHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(resource, parent, false)

        return CurOrderHolder(itemView)
    }

    override fun onBindViewHolder(holder: CurOrderHolder, position: Int) {

        var img = icon_list[position]

        holder.icon.setImageResource(img)
        holder.name.text = icon_name_list[position]

        holder.constraint.setOnClickListener{
            var intent:Intent

            val name = holder.name.text
            when (name) {
                "공지사항" -> {
                    intent = Intent(context, NoticeActivity::class.java)
                    context?.startActivity(intent)
                }
                "예약내역" -> {
                    intent = Intent(context,ReservationDetailsActivity::class.java)
                    context?.startActivity(intent)
                }
                "FAQ" -> {
                    intent = Intent(context,FAQActivity::class.java)
                    context?.startActivity(intent)
                }
                "설정" -> {
                    intent = Intent(context,SettingActivity::class.java)
                    context?.startActivity(intent)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return icon_list.size
    }
}

class CurOrderHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var constraint: ConstraintLayout = itemView!!.findViewById(R.id.constraint)
    var icon: ImageView = itemView!!.findViewById(R.id.img)
    var name: TextView = itemView!!.findViewById(R.id.tv)
}