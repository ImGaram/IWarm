package com.example.iwarm.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.iwarm.adapter.WeeklyWeatherAdapter
import com.example.iwarm.data.response.WeatherListResponse
import com.example.iwarm.databinding.ActivityWeeklyWeatherBinding

class WeeklyWeatherActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWeeklyWeatherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWeeklyWeatherBinding.inflate(layoutInflater)

        val response = intent.getSerializableExtra("list") as List<WeatherListResponse>
        Log.d("TAG", "onCreate: $response")

        binding.backImage.setOnClickListener { finish() }

        val list = mutableListOf<WeatherListResponse>()
        for (i in response.indices) {
            if (i % 7 == 0) {
                list.add(response[i])
                Log.d("TAG", "onCreate: $i, ${response[i]}")
            }
        }
        val adapter = WeeklyWeatherAdapter(list)
        binding.weeklyWeatherRecyclerView.adapter = adapter
        binding.weeklyWeatherRecyclerView.layoutManager = LinearLayoutManager(this)

        setContentView(binding.root)
    }
}