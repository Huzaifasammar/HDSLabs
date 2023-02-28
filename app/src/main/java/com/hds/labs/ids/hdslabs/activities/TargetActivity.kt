package com.hds.labs.ids.hdslabs.activities

import android.R
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.hds.labs.ids.hdslabs.databinding.ActivityTargetBinding


class TargetActivity : AppCompatActivity() {
    lateinit var binding:ActivityTargetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityTargetBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val pie = AnyChart.column()

        val data = ArrayList<DataEntry>()
        data.add(ValueDataEntry("John", 10000))
        data.add(ValueDataEntry("Jake", 12000))
        data.add(ValueDataEntry("Peter", 18000))
        pie.data(data)
        binding.anyChartView.setChart(pie)
    }
}