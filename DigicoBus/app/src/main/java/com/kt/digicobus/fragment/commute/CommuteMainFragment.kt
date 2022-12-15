package com.kt.digicobus.fragment.commute

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.kt.digicobus.R
import com.kt.digicobus.data.data.Companion.commuteBusInfoList
import com.kt.digicobus.data.data.Companion.commuteLeaveBusInfoList
import com.kt.digicobus.databinding.FragmentCommuteMainBinding


// 통근버스 1
class CommuteMainFragment : Fragment() {
    private lateinit var binding : FragmentCommuteMainBinding

    private lateinit var ctx: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommuteMainBinding.inflate(layoutInflater)


        //선택된 리스트 없으면 0, 있으면 1
//        if(routeChoiceState == 0){
//            binding.btnChoice.isEnabled = false
//        }else{
//            binding.btnChoice.isEnabled = true
//        }
        //전체값으로 데이터 받아서 달력으로 넘어가기 -> 버튼이 메인페이지에 있어서 어쩔 수 없음.
        binding.btnChoice.setOnClickListener {
            var startCnt = 0
            var endCnt = 0
            // 만약에 출근에 클릭이 되어있다면
            for(i in 0 until commuteBusInfoList.size){
                if(commuteBusInfoList[i].isClick){
                    startCnt++
                }
            }
            for(i in 0 until commuteLeaveBusInfoList.size){
                if(commuteLeaveBusInfoList[i].isClick){
                    endCnt++
                }
            }

            if(startCnt > 0 || endCnt > 0){
                val bundle = Bundle()
                bundle.putInt("commuteId", (if (startCnt > 0) 0 else 1))

                container?.findNavController()?.navigate(R.id.action_CommuteMainFragment_to_CommuteCalendarChoiceFragment, bundle)
            }else{
                Toast.makeText(ctx, "노선이 선택되지 않았습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        //출퇴근 fragment 연결
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.topNavigationView, navController)

        return binding.root
    }
}