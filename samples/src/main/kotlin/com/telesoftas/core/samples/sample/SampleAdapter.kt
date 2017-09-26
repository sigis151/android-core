package com.telesoftas.core.samples.sample

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.telesoftas.core.samples.R

class SampleAdapter : RecyclerView.Adapter<SampleViewHolder>() {
    private val items = mutableListOf<Sample>()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SampleViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val itemView = inflater.inflate(R.layout.item_sample, parent, false)
        return SampleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SampleViewHolder?, position: Int) {
        holder?.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun setItems(items: List<Sample>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
}