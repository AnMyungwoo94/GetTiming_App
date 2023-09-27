package com.myungwoo.gettiming_app.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.myungwoo.gettiming_app.R
import com.myungwoo.gettiming_app.databinding.FragmentCoinListBinding
import com.myungwoo.gettiming_app.db.entity.InterestCoinEntity
import com.myungwoo.gettiming_app.view.adapter.CoinListRVAdapter
import com.myungwoo.gettiming_app.viewModel.MainViewModel
import timber.log.Timber


class CoinListFragment : Fragment() {
    private lateinit var binding: FragmentCoinListBinding
    private val viewModel: MainViewModel by activityViewModels()
    private val selectedList = ArrayList<InterestCoinEntity>()
    private val unselectedList = ArrayList<InterestCoinEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCoinListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAllInterestCoinData()
        viewModel.selectedCoinList.observe(viewLifecycleOwner, Observer {
//            Timber.d(it.toString())

            //라이브데이터가 실행될 때마다 쌓여지니 clear해주기
            selectedList.clear()
            unselectedList.clear()

            for (item in it) {

                if (item.selected) {
                    selectedList.add(item)
                } else {
                    unselectedList.add(item)
                }
            }
            Timber.d(selectedList.toString())
            Timber.d(unselectedList.toString())

            setSelectedListRV()
        })
    }

    private fun setSelectedListRV() {
        val selectedRVAdapter = CoinListRVAdapter(requireContext(), selectedList)
        binding.selectedCoinRV.adapter = selectedRVAdapter
        binding.selectedCoinRV.layoutManager = LinearLayoutManager(requireContext())

        selectedRVAdapter.itemClick = object : CoinListRVAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
               viewModel.updateInterestCoinData(selectedList[position])
            }
        }

        val unselectedRVAdapter = CoinListRVAdapter(requireContext(), unselectedList)
        binding.unSelectedCoinRV.adapter = unselectedRVAdapter
        binding.unSelectedCoinRV.layoutManager = LinearLayoutManager(requireContext())

        unselectedRVAdapter.itemClick = object : CoinListRVAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                viewModel.updateInterestCoinData(unselectedList[position])
            }
        }
    }
}