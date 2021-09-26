package com.bogdan.cryptodashboard.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bogdan.cryptodashboard.R
import com.bogdan.cryptodashboard.databinding.FragmentMainBinding
import com.bogdan.cryptodashboard.model.CardInfoViewAdapter
import com.bogdan.cryptodashboard.model.CardInfo
import com.bogdan.cryptodashboard.viewmodel.MainFragmentViewModel
import com.bogdan.cryptodashboard.viewmodel.MainViewModelFactory

class MainFragment: Fragment() {

    private lateinit var fragmentViewModel: MainFragmentViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setupViewModel()
    }

    private fun setupViewModel() {
        val factory = MainViewModelFactory()
        fragmentViewModel = ViewModelProvider(this, factory).get(MainFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentMainBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_main,
            container,
            false)
        val view = binding.root

        binding.lifecycleOwner = this
        setupObservers(binding)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerViewAndObserveForChanges(view)
    }

    private fun setupRecyclerViewAndObserveForChanges(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.wallet_recycler_view)
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = RecyclerView.VERTICAL
        recyclerView.layoutManager = layoutManager

        observeCardInfoChanges(recyclerView)
    }

    private fun observeCardInfoChanges(recyclerView: RecyclerView) {
        val cardInfoObserver: Observer<List<CardInfo>> =
            Observer<List<CardInfo>> { cardInfoList ->
                val cardInfoViewAdapter = CardInfoViewAdapter(requireContext(), cardInfoList)
                recyclerView.adapter = cardInfoViewAdapter
                recyclerView.isNestedScrollingEnabled = false
            }
        fragmentViewModel.cardInfo.observe(requireActivity(), cardInfoObserver)
    }

    private fun setupObservers(binding: FragmentMainBinding) {
        observeForChanges(fragmentViewModel.walletBalanceInUsd, binding.walletFiatBalance)
    }

    private fun observeForChanges(liveData: LiveData<String>, text: TextView) {
        liveData.observe(requireActivity(), text::setText)
    }

    companion object {
        const val FRAGMENT_TAG = "MAIN_FRAGMENT_TAG"
    }
}