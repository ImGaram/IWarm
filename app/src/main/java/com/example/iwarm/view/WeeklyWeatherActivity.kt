package com.example.iwarm.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.iwarm.R
import com.example.iwarm.databinding.ActivityWeeklyWeatherBinding

class WeeklyWeatherActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWeeklyWeatherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWeeklyWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}