package com.vita.weatherforecast.presentation.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView

import com.vita.weatherforecast.domain.model.WeatherListDomain
import com.squareup.picasso.Picasso
import com.vita.weatherforecast.R

class WeatherForecastDetailsAdapter(
    val context: Context
) : RecyclerView.Adapter<WeatherForecastDetailsAdapter.ViewHolder>() {

    private var weatherListData: WeatherListDomain? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_weather_hour_of_day, parent, false)
        return ViewHolder(layoutView)
    }

    override fun getItemCount(): Int {
        return 1
//        return if (weatherListData.isEmpty()) {
//            0
//        } else {
//            weatherListData.size
//        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setTransaction(weatherListDomain: WeatherListDomain?) {
        if (weatherListDomain != null) {
            weatherListData = weatherListDomain
        }
        notifyDataSetChanged()
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val weatherDataList = weatherListData
        val iconName = weatherDataList?.weather?.get(0)?.icon

        holder.hourOfDay.text =
            weatherDataList?.dtTxt?.substringAfter(" ")?.substringBeforeLast(":") ?: "00:00"
        holder.temperature.text = weatherDataList?.main?.temp.toString().substringBefore(".") + "°"
        Picasso.get().load("https://openweathermap.org/img/wn/$iconName@2x.png").into(holder.forecastIcon)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val hourOfDay: TextView = view.findViewById(R.id.textViewHourOfDay)
        val temperature: TextView = view.findViewById(R.id.textViewTemp)
        var forecastIcon: ImageView = view.findViewById(R.id.imageViewForecastIcon)
    }
}