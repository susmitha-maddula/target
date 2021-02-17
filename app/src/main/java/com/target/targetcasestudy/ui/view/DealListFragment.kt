package com.target.targetcasestudy.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.target.targetcasestudy.R
import com.target.targetcasestudy.data.model.DealItem
import com.target.targetcasestudy.network.RetrofitBuilder
import com.target.targetcasestudy.ui.DealItemAdapter
import com.target.targetcasestudy.ui.DealItemClickListener
import com.target.targetcasestudy.ui.viewmodels.DealListViewModel
import com.target.targetcasestudy.ui.viewmodels.DealListModelFactory
import com.target.targetcasestudy.utils.Status
import kotlinx.android.synthetic.main.fragment_deal_list.*

class DealListFragment : Fragment() {

    private lateinit var viewModel: DealListViewModel
    private lateinit var dealsAdapter: DealItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_deal_list, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        deals_recycler_view.layoutManager = LinearLayoutManager(requireContext())
        dealsAdapter = DealItemAdapter(listOf(), DealItemClickListener { id ->
            val directions = DealListFragmentDirections.actionListToDetails(varDealId = id)
            findNavController().navigate(directions)
        })
        deals_recycler_view.adapter = dealsAdapter
        setViewModel()
        setObservers()
    }

    private fun setViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            DealListModelFactory((RetrofitBuilder.apiService))
        ).get(DealListViewModel::class.java)
    }

    private fun setObservers() {
        viewModel.getDeals().observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        deals_recycler_view.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        resource.data?.let { products -> retrieveList(products.products) }
                    }
                    Status.ERROR -> {
                        deals_recycler_view.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                        deals_recycler_view.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun retrieveList(deals: List<DealItem>) {
        dealsAdapter.apply {
            setDeals(deals)
            notifyDataSetChanged()
        }
    }

}
