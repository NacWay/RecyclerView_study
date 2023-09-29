package com.study.recyclingview_study.counries

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.study.recyclingview_study.R
import com.study.recyclingview_study.SimpleItemTouchHelperCallback

class CountriesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_counries)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerCounries)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = CountriesAdapter(CountriesList().countries, FlagsList().flags).also { recyclerView.adapter = it }
        recyclerView.addItemDecoration(
            DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        )
        val callback: ItemTouchHelper.Callback = SimpleItemTouchHelperCallback(adapter)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(recyclerView)
    }
}