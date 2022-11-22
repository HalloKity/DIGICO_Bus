package com.kt.digicobus.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kt.digicobus.R

class MoreListAdapter(var context: Context, private val resource: Int,var icon_list: MutableList<Int>, var icon_name_list: MutableList<String>)
    : RecyclerView.Adapter<CurOrderHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurOrderHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(resource, parent, false)

        return CurOrderHolder(itemView)
    }

    override fun onBindViewHolder(holder: CurOrderHolder, position: Int) {

        var img = icon_list[position]

//        var resId = context.resources.getIdentifier(
//            img.substring(0, img.length - 4),
//            "drawable",
//            "com.kt.digicobus"
//        )

        print("??? : $img")
        holder.icon.setImageResource(img)
        holder.name.text = icon_name_list[position]
    }

    override fun getItemCount(): Int {
        return icon_list.size
    }
}

class CurOrderHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var icon: ImageView = itemView!!.findViewById(R.id.img)
    var name: TextView = itemView!!.findViewById(R.id.tv)
}