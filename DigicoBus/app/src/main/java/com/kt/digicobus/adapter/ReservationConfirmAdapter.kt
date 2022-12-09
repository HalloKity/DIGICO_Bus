package com.kt.digicobus.adapter

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.kt.digicobus.R
import com.kt.digicobus.data.TicketContent
import com.kt.digicobus.dialog.BottomSheetQrcodeHelp

class ReservationConfirmAdapter(var context: Context, private val resource: Int,  var ticketContentsList: MutableList<TicketContent>)
    : RecyclerView.Adapter<ReservationHolder>() {
    val PERMISSIONS_CALL_PHONE = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(resource, parent, false)

        return ReservationHolder(itemView)
    }

    override fun onBindViewHolder(holder: ReservationHolder, position: Int) {
        holder.icon_info.setOnClickListener{
            val bottomDialog = BottomSheetQrcodeHelp()

            bottomDialog.show(
                (holder.itemView.context as FragmentActivity).supportFragmentManager,
                bottomDialog.tag
            )
        }

        val callNumber = holder.tv_car_number_content.text.toString()
        holder.tv_car_number_content.setOnClickListener{
            dialog(callNumber)
        }

        holder.icon_call.setOnClickListener{
            dialog(callNumber)
        }
    }

    override fun getItemCount(): Int {
        return ticketContentsList.size
    }

    fun dialog(callNumber: String){
        //권한확인 후 다이얼로그 뷰 띄우기
        AlertDialog.Builder(context)
            .setMessage("${callNumber}로 전화하시겠습니까?")
            .setPositiveButton("확인", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, which: Int) {
                    if (ContextCompat.checkSelfPermission(
                            context.applicationContext,
                            Manifest.permission.CALL_PHONE
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        ActivityCompat.requestPermissions(
                            context as Activity,
                            arrayOf(Manifest.permission.CALL_PHONE),
                            PERMISSIONS_CALL_PHONE
                        )
                    } else {
                        val callIntent = Intent(Intent.ACTION_CALL)
                        callIntent.data = Uri.parse("tel:${callNumber}")
                        context.startActivity(callIntent)
                    }
                }
            })
            .setNegativeButton("취소") { dialog, which -> }
            .create()
            .show()
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

    var tv_car_number_content: TextView = itemView!!.findViewById((R.id.tv_car_number_content))
    var icon_call: ImageView = itemView!!.findViewById(R.id.icon_call)
    val icon_info: ImageView = itemView!!.findViewById(R.id.icon_info)
}