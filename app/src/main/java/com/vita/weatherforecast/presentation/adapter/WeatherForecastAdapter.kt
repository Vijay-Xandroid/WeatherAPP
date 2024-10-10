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
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.vita.weatherforecast.domain.model.WeatherListDomain
import com.vita.weatherforecast.utils.extension.getDateTime
import com.squareup.picasso.Picasso
import com.vita.weatherforecast.R
import java.time.format.TextStyle
import java.util.Locale

class WeatherForecastAdapter(
    val context: Context,
    private val onWeatherListListener: OnWeatherListListener
) : RecyclerView.Adapter<WeatherForecastAdapter.ViewHolder>() {

    private var weatherListData: List<WeatherListDomain> = listOf()

    interface OnWeatherListListener {
        fun onWeatherListener(weatherListData: WeatherListDomain)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_forecast, parent, false)
        return ViewHolder(layoutView)
    }

    override fun getItemCount(): Int {
        return if (weatherListData.isEmpty()) {
            0
        } else {
            weatherListData.size
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setTransaction(weatherListDomain: List<WeatherListDomain>?) {
        if (weatherListDomain != null) {
            weatherListData = weatherListDomain
        }
        notifyDataSetChanged()
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val weatherDataList = weatherListData[position]
        val iconName = weatherDataList.weather?.get(0)?.icon

        if (weatherDataList.dt?.let {
                getDateTime(it)!!.name == "MONDAY"} == true) {
            holder.cardView.setBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.monday_color
                )
            )
        } else if (weatherDataList.dt?.let {
                getDateTime(it)!!.name == "TUESDAY"} == true) {
            holder.cardView.setBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.tuesday_color
                )
            )
        } else if (weatherDataList.dt?.let {
                getDateTime(it)!!.name == "WEDNESDAY"} == true) {
            holder.cardView.setBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.wednesday_color
                )
            )
        } else if (weatherDataList.dt?.let {
                getDateTime(it)!!.name == "THURSDAY"} == true) {
            holder.cardView.setBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.thursday_color
                )
            )
        } else if (weatherDataList.dt?.let {
                getDateTime(it)!!.name == "FRIDAY"} == true) {
            holder.cardView.setBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.friday_color
                )
            )
        } else if (weatherDataList.dt?.let {
                getDateTime(it)!!.name == "SATURDAY"} == true) {
            holder.cardView.setBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.mainTextColor
                )
            )
        } else if (weatherDataList.dt?.let {
                getDateTime(it)!!.name == "SUNDAY"} == true) {
            holder.cardView.setBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.sunday_color
                )
            )
        }
        else {
            holder.cardView.setBackgroundColor(ContextCompat.getColor(context, R.color.holo_orange_dark))
        }
        holder.timeOfDay.text =
            weatherDataList.dtTxt?.substringAfter(" ")?.substringBeforeLast(":") ?: "00:00"
        holder.dayOfWeek.text = weatherDataList.dt?.let {
            getDateTime(it)?.getDisplayName(
                TextStyle.FULL,
                Locale.getDefault()
            )
        }
        holder.temperature.text = weatherDataList.main?.temp.toString().substringBefore(".") + "°"
        holder.minimumTemp.text =
            weatherDataList.main?.tempMin.toString().substringBefore(".") + "°"
        holder.maximumTemp.text =
            weatherDataList.main?.tempMax.toString().substringBefore(".") + "°"
        Picasso.get().load("https://openweathermap.org/img/wn/$iconName@2x.png").into(holder.weatherImage)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        var cardView: ConstraintLayout = view.findViewById(R.id.forecastCard)
        val timeOfDay: TextView = view.findViewById(R.id.textViewTimeOfDay)
        val dayOfWeek: TextView = view.findViewById(R.id.textViewDayOfWeek)
        val temperature: TextView = view.findViewById(R.id.textViewTemp)
        val minimumTemp: TextView = view.findViewById(R.id.tempMinText)
        val maximumTemp: TextView = view.findViewById(R.id.tempMaxText)
        var weatherImage: ImageView = view.findViewById(R.id.imageViewForecastIcon)

        override fun onClick(p0: View?) {
            onWeatherListListener.onWeatherListener(weatherListData[adapterPosition])
        }

        init {
            view.setOnClickListener(this)
        }
    }
}