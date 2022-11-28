package com.example.iwarm.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.iwarm.R
import com.example.iwarm.data.response.Weather2Response
import com.example.iwarm.data.response.WeatherListResponse
import com.example.iwarm.databinding.TabRecyclerItemBinding
import com.example.iwarm.databinding.WeeklyWeatherItemBinding
import java.text.SimpleDateFormat
import java.util.*

class WeeklyWeatherAdapter(private val response: MutableList<WeatherListResponse>) : RecyclerView.Adapter<WeeklyWeatherAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = WeeklyWeatherItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(response[position], position)
    }

    override fun getItemCount(): Int = response.size

    inner class ViewHolder(private val binding: WeeklyWeatherItemBinding): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: WeatherListResponse, position: Int) {
            val temp = item.main.temp-273
            binding.temperatureItemText.text = (Math.round(temp * 1) /1f).toString() + "°"
            binding.dayOfTheWeekText.text = getWeekDay("E", position) + "요일"
            binding.dateText.text = getWeekDay("yyyy.MM.dd", position)

            item.weather.map {
                setImage(it, binding, position)
            }
        }
    }

    private fun getWeekDay(pattern: String, position: Int): String {
        val now = System.currentTimeMillis()
        val date = Date(now)
        val dateFormat = SimpleDateFormat(pattern, Locale.KOREA)

        val cal = Calendar.getInstance()
        cal.time = date
        cal.add(Calendar.DATE, position)
        return dateFormat.format(cal.time)
    }

    private fun setImage(response: Weather2Response, item: WeeklyWeatherItemBinding, position: Int) {
        val image = item.weatherItemImage
        when (response.main) {
            "Clear" -> {
                if (getWeekDay("kk", position).toInt() >= 19) image.setImageResource(R.drawable.sunny_night)
                else image.setImageResource(R.drawable.sunny)
            }
            "Snow" -> {
                image.setImageResource(R.drawable.snowy)
            }
            "Rain" -> {
                image.setImageResource(R.drawable.rainy)
            }
            "Clouds" -> {
                if (response.description == "few clouds" || response.description == "scattered clouds") {
                    if (getWeekDay("kk", position).toInt() >= 19) image.setImageResource(R.drawable.sunny_cloud_night)
                    else image.setImageResource(R.drawable.sun_cloud)
                } else {
                    image.setImageResource(R.drawable.cloudy)
                }
            }
        }
    }
}