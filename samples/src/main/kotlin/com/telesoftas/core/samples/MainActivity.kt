package com.telesoftas.core.samples

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.telesoftas.core.samples.sample.Sample
import com.telesoftas.core.samples.sample.SampleAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpRecyclerView(listOf())
    }

    private fun setUpRecyclerView(items: List<Sample>) {
        val adapter = SampleAdapter()
        recyclerView.adapter = adapter
        adapter.setItems(items)
    }
}
