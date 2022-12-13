package com.kt.digicobus.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kt.digicobus.GOGenieApplication
import com.kt.digicobus.R
import com.kt.digicobus.adapter.ReservationConfirmAdapter
import com.kt.digicobus.data.model.ReserveSearch
import com.kt.digicobus.databinding.FragmentReservationConfirmBinding
import com.kt.digicobus.dialog.DialogAfterSeatChoice
import com.kt.digicobus.fragment.commute.TAG
import com.kt.digicobus.service.ReservationService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper

class ReservationConfirmFragment : Fragment() {
    private lateinit var binding : FragmentReservationConfirmBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var reservationConfirmAdapter: ReservationConfirmAdapter

    private lateinit var ctx: Context
    private lateinit var ticketList : MutableList<ReserveSearch>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReservationConfirmBinding.inflate(layoutInflater)

        setDataAndAdapter()

        return binding.root
    }

    private fun setAdapter(){

        // RecyclerView 객체 생성
        recyclerView = binding.recyclerview
        recyclerView.layoutManager = LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false)
        OverScrollDecoratorHelper.setUpOverScroll(recyclerView, OverScrollDecoratorHelper.ORIENTATION_VERTICAL)

        // 2. Adapter 객체 생성(한 행을 위해 반복 생성할 Layout과 데이터 전달)
        reservationConfirmAdapter = ReservationConfirmAdapter(ctx, R.layout.card_reservation_confirm, ticketList, ::onClickReservationCancelBtn)

        // 3. RecyclerView와 Adapter 연결
        recyclerView.adapter = reservationConfirmAdapter
    }

    private fun setDataAndAdapter() {
        CoroutineScope(Dispatchers.Main).launch {
            getAllReservationBusInfo()
            setAdapter()
        }
    }

    // 예약 취소
    private fun onClickReservationCancelBtn(position: Int) {
        val dialogCancel = DialogAfterSeatChoice(ctx, null, ticketList[position], setReservationConfirmAdapter = ::setDataAndAdapter)
        dialogCancel.show()
    }

    private suspend fun getAllReservationBusInfo() {
        withContext(Dispatchers.IO) {
            val service = GOGenieApplication.retrofit.create(ReservationService::class.java)
            val response = service.selectAllReservationBusInfo().execute()

            if (response.code() == 200) {
                var resp = response.body()
                ticketList = (resp as List<ReserveSearch>).toMutableList()

            } else {
                Log.d(TAG, "getAllReservationBusInfo: error code")
            }
        }
    }
}