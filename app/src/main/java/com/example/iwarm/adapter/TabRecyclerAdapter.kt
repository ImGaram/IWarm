package com.example.iwarm.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.iwarm.R
import com.example.iwarm.data.response.Weather2Response
import com.example.iwarm.data.response.WeatherListResponse
import com.example.iwarm.databinding.TabRecyclerItemBinding
import java.lang.Math.round
import java.text.SimpleDateFormat
import java.util.*

class TabRecyclerAdapter(private val list: List<WeatherListResponse>, private val context: Context): RecyclerView.Adapter<TabRecyclerAdapter.PagerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val binding = TabRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bind(list[position], position)
    }

    override fun getItemCount(): Int = list.size

    inner class PagerViewHolder(private val item: TabRecyclerItemBinding): RecyclerView.ViewHolder(item.root) {
        fun bind(weatherListResponse: WeatherListResponse, position: Int) {
            val temp = weatherListResponse.main.temp-273
            item.currentDate.text = getDate("yyyy.MM.dd E", position)
            item.temperature.text = (round(temp * 1) /1f).toString() + "°c"
            item.humidityPercent.text = weatherListResponse.main.humidity.toString() + "%"
            item.windSpeed.text = weatherListResponse.wind.speed.toString() + "m/s"

            weatherListResponse.weather.map {
                setImage(it, item, position)
            }
        }
    }

    private fun setImage(response: Weather2Response, item: TabRecyclerItemBinding, position: Int) {
        val image = item.weatherImage
        when (response.main) {
            "Clear" -> {
                item.weatherInfo.text = "맑음"
                if (getDate("kk", position).toInt() >= 19) image.setImageResource(R.drawable.sunny_night)
                else image.setImageResource(R.drawable.sunny)
            }
            "Snow" -> {
                item.weatherInfo.text = "눈"
                image.setImageResource(R.drawable.snowy)
            }
            "Rain" -> {
                item.weatherInfo.text = "비"
                image.setImageResource(R.drawable.rainy)
            }
            "Clouds" -> {
                if (response.description == "few clouds" || response.description == "scattered clouds") {
                    item.weatherInfo.text = "약간 구름"
                    if (getDate("kk", position).toInt() >= 19) image.setImageResource(R.drawable.sunny_cloud_night)
                    else image.setImageResource(R.drawable.sun_cloud)
                } else {
                    item.weatherInfo.text = "흐림"
                    image.setImageResource(R.drawable.cloudy)
                }
            }
        }
    }

    private fun getDate(pattern: String, position: Int): String {
        val now = System.currentTimeMillis()
        val date = Date(now)
        val dateFormat = SimpleDateFormat(pattern, Locale.KOREA)

        val cal = Calendar.getInstance()
        cal.time = date
        cal.add(Calendar.DATE, position)
        return dateFormat.format(cal.time)
    }
}