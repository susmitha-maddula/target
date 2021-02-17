package com.target.targetcasestudy.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.target.targetcasestudy.R
import com.target.targetcasestudy.data.model.DealItem
import kotlinx.android.synthetic.main.deal_list_item.view.*

class DealItemAdapter(private var deals: List<DealItem>, val clickListener: DealItemClickListener) :
    RecyclerView.Adapter<DealItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DealItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.deal_list_item, parent, false)
        return DealItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return (if (deals == null) 0 else deals.size)
    }

    override fun onBindViewHolder(viewHolder: DealItemViewHolder, position: Int) {
        viewHolder.bind(deals[position], clickListener)
    }

    fun setDeals(deals: List<DealItem>) {
        this.deals = deals
    }
}

class DealItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(deal: DealItem, clickListener: DealItemClickListener) {
        itemView.apply {
            deal_list_item_title.text = deal.title

            deal.sale_price?.let {
                deal_list_item_price.text = it.display_string
            } ?: run {
                deal_list_item_price.text = deal.regular_price.display_string
            }

            deal.image_url?.apply {
                Glide.with(deal_list_item_image_view.context)
                    .load(this)
                    .into(deal_list_item_image_view)
            }

            deal_list_item_aisle.text = deal.aisle

            itemView.setOnClickListener {
                clickListener.onClick(deal)
            }

        }
    }

}

class DealItemClickListener(val clickListener: (dealId: Int) -> Unit) {
    fun onClick(deal: DealItem) = clickListener(deal.id)
}
