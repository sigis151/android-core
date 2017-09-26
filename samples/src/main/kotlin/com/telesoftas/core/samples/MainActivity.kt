package com.telesoftas.core.samples

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.telesoftas.core.samples.sample.Sample
import com.telesoftas.core.samples.sample.SampleAdapter
import com.telesoftas.core.samples.sample.SampleViewHolder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpRecyclerView(listOf())
    }

    private fun setUpRecyclerView(items: List<Sample>) {
        val adapter = SampleAdapter(createSampleViewHolderListener())
        recyclerView.adapter = adapter
        adapter.setItems(items)
    }

    private fun createSampleViewHolderListener(): SampleViewHolder.Listener {
        return object : SampleViewHolder.Listener {
            override fun onItemSelected(item: Sample, adapterPosition: Int) {
                // Do nothing
            }
        }
    }
}
