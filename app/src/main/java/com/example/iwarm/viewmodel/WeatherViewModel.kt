package com.example.iwarm.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.iwarm.data.response.WeatherResponse
import com.example.iwarm.repository.WeatherRepository
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val repository: WeatherRepository, private val lat: Double,
    private val lot: Double, private val key: String
): ViewModel() {
    private val _getWeatherLiveData: MutableLiveData<WeatherResponse?> = MutableLiveData()
    val getWeatherLiveData: MutableLiveData<WeatherResponse?>
    get() = _getWeatherLiveData

    init {
        viewModelScope.launch {
            _getWeatherLiveData.value = repository.getWeather(lat, lot, key)
        }
    }

    class Factory(private val application: Application, private val lat: Double,
                  private val lot: Double, private val key: String
    ): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return WeatherViewModel(WeatherRepository(application), lat, lot, key) as T
        }
    }
}