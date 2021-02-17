package com.target.targetcasestudy.ui.view

import android.app.Activity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.target.targetcasestudy.R
import com.target.targetcasestudy.data.model.DealItem
import com.target.targetcasestudy.network.RetrofitBuilder
import com.target.targetcasestudy.ui.viewmodels.DealDetailsModelFactory
import com.target.targetcasestudy.ui.viewmodels.DealDetailsViewModel
import com.target.targetcasestudy.utils.Status
import kotlinx.android.synthetic.main.content_middle.*
import kotlinx.android.synthetic.main.fragment_deal_item.*

class DealItemFragment : Fragment() {

    private lateinit var viewModel: DealDetailsViewModel
    private val safeArgs: DealItemFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_deal_item, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setViewModel()
        setObservers()
    }

    private fun setViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            DealDetailsModelFactory((RetrofitBuilder.apiService))
        ).get(DealDetailsViewModel::class.java)
    }


    private fun setObservers() {
        viewModel.getDealDetails(safeArgs.varDealId).observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        progressBar.visibility = View.GONE
                        resource.data?.let { product -> render(product) }
                    }
                    Status.ERROR -> {
                        progressBar.visibility = View.GONE
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                    }
                }
            }
        })
    }


    private fun render(product: DealItem) {
        deal_list_item_title.text = product.title
        product.description?.let { des ->
            deal_list_item_description.visibility = View.VISIBLE
            var prevEnd: Int = 0
            var prevStart: Int = 0
            var range: Int = 100

            if (des.length > 100) {
                read_more_txt.visibility = View.VISIBLE
                prevStart = prevEnd
                prevEnd = prevEnd + range
                if (prevEnd > des.length) {
                    read_more_txt.visibility = View.GONE
                    prevEnd = des.length
                }
                deal_list_item_description.append(des.substring(prevStart, prevEnd))
                read_more_txt.setOnClickListener {
                    prevStart = prevEnd
                    prevEnd = prevEnd + range
                    if (prevEnd > des.length) {
                        prevEnd = des.length
                        read_more_txt.visibility = View.GONE
                    }
                    deal_list_item_description.append(des.substring(prevStart, prevEnd))
                }

            } else {
                read_more_txt.visibility = View.GONE
                deal_list_item_description.text = des
            }
        }

        product.image_url?.apply {
            val displayMetrics = DisplayMetrics()
            (context as Activity?)!!.windowManager
                .defaultDisplay
                .getMetrics(displayMetrics)
            val width = displayMetrics.widthPixels
            val url = this.replace("300", width.toString())
            Glide.with(deal_details_image_view.context)
                .load(url)
                .into(deal_details_image_view)
        }

        product.sale_price?.let {
            deal_list_item_sale_price.text = it.display_string
            deal_list_item_regulr_price.text = product.regular_price.display_string

        } ?: run {
            deal_list_item_sale_price.text = product.regular_price.display_string
        }


    }

}
