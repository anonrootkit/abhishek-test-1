package com.example.tast1.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.tast1.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var homeBinding : FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel

    private val homeAdapter : HomeAdapter by lazy {
        HomeAdapter(requireContext(), ArrayList())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeBinding = FragmentHomeBinding.inflate(inflater)
        return homeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, HomeViewModel.Factory(requireContext())).get(HomeViewModel::class.java)

        viewModel.fetchStatus.observe(viewLifecycleOwner){status ->
            if (status != null){
                when(status){
                    FetchStatus.FETCHING -> {
                        homeBinding.progressBar.visibility = View.VISIBLE
                    }
                    FetchStatus.FETCHED -> {
                        homeBinding.progressBar.visibility = View.GONE
                    }
                    FetchStatus.ERROR -> {
                        homeBinding.progressBar.visibility = View.GONE
                        homeBinding.errorLabel.text = "Some error occurred..."
                        homeBinding.errorLabel.visibility = View.VISIBLE
                        homeBinding.retryButton.visibility = View.VISIBLE
                    }
                }
            }
        }

        viewModel.genericList.observe(viewLifecycleOwner){
            if (it != null){
                homeAdapter.clear()
                homeAdapter.addAll(it)
                homeAdapter.notifyDataSetChanged()
            }
        }

        homeBinding.retryButton.setOnClickListener {
            viewModel.fetchDataFromInternet()
        }

        homeBinding.listView.adapter = homeAdapter

        viewModel.fetchDataFromInternet()
    }
}
