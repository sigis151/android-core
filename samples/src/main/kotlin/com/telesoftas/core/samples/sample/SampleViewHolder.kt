package com.telesoftas.core.samples.sample

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.item_sample.view.*

class SampleViewHolder(
        itemView: View,
        private val listener: Listener
) : RecyclerView.ViewHolder(itemView) {
    private var sample = Sample.empty()

    init {
        itemView.setOnClickListener { listener.onItemSelected(sample, adapterPosition) }
    }

    fun bind(sample: Sample) {
        this.sample = sample
        itemView.nameTextView.text = sample.nameText
        itemView.descriptionTextView.text = sample.descriptionText
        val drawable = ContextCompat.getDrawable(itemView.context, sample.imageDrawableRes)
        itemView.iconImageView.setImageDrawable(drawable)
    }

    interface Listener {
        fun onItemSelected(item: Sample, adapterPosition: Int)
    }
}