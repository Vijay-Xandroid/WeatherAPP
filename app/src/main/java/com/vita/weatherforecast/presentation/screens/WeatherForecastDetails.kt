package com.vita.weatherforecast.presentation.screens

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.vita.weatherforecast.presentation.adapter.WeatherForecastDetailsAdapter

import com.vita.weatherforecast.data.remote.Constants
import com.vita.weatherforecast.domain.model.WeatherListDomain

import com.vita.weatherforecast.utils.extension.getDateTime
import com.vita.weatherforecast.utils.extension.logTrace
import com.squareup.picasso.Picasso
import com.vita.weatherforecast.R
import com.vita.weatherforecast.databinding.FragmentWeatherDetailBinding
import java.time.format.TextStyle
import java.util.Locale

class WeatherForecastDetails : AppCompatActivity() {

    private lateinit var binding: FragmentWeatherDetailBinding
    private lateinit var weatherForecastDetailsAdapter: WeatherForecastDetailsAdapter

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentWeatherDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.white)

        binding.recyclerViewHourOfDay.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.HORIZONTAL, false
        )
        weatherForecastDetailsAdapter = WeatherForecastDetailsAdapter(this)
        binding.recyclerViewHourOfDay.adapter = weatherForecastDetailsAdapter

        val weatherDetails =
            intent?.getParcelableExtra<WeatherListDomain>(Constants.WEATHER_DETAILS)
        logTrace("WeatherDetailsPassed $weatherDetails")
        val city = intent?.getStringExtra(Constants.CITY)
        val country = intent?.getStringExtra(Constants.COUNTRY)

        weatherDetails.let {
            weatherForecastDetailsAdapter.setTransaction(it)
        }

        initView(weatherDetails, city, country)

        binding.fabClose.setOnClickListener {
            onBackPressed()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    private fun initView(
        weatherDetails: WeatherListDomain?,
        city: String?,
        country: String?
    ) {
        binding.cityName.text = "$city, $country"

        var position = 0
        val iconName = weatherDetails?.weather?.get(0)?.icon

        if (weatherDetails?.dt?.let {
                getDateTime(it)!!.name == "MONDAY"} == true) {
            binding.cardView.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.monday_color
                )
            )
        } else if (weatherDetails?.dt?.let {
                getDateTime(it)!!.name == "TUESDAY"} == true) {
            binding.cardView.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.tuesday_color
                )
            )
        } else if (weatherDetails?.dt?.let {
                getDateTime(it)!!.name == "WEDNESDAY"} == true) {
            binding.cardView.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.wednesday_color
                )
            )
        } else if (weatherDetails?.dt?.let {
                getDateTime(it)!!.name == "THURSDAY"} == true) {
            binding.cardView.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.thursday_color
                )
            )
        } else if (weatherDetails?.dt?.let {
                getDateTime(it)!!.name == "FRIDAY"} == true) {
            binding.cardView.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.friday_color
                )
            )
        } else if (weatherDetails?.dt?.let {
                getDateTime(it)!!.name == "SATURDAY"} == true) {
            binding.cardView.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.mainTextColor
                )
            )
        } else if (weatherDetails?.dt?.let {
                getDateTime(it)!!.name == "SUNDAY"} == true) {
            binding.cardView.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.sunday_color
                )
            )
        }
        else {
            binding.weatherDetailsContainerBgd.setBackgroundColor(ContextCompat.getColor(this, R.color.holo_orange_dark))
        }

        binding.hourTextView.text = weatherDetails?.dtTxt
            ?.substringAfter(" ")?.substringBeforeLast(":") ?: "00:00"
        binding.textViewDayOfWeek.text = weatherDetails?.dt?.let {
            getDateTime(it)?.getDisplayName(
                TextStyle.FULL,
                Locale.getDefault()
            )
        }
        Picasso.get().load("https://openweathermap.org/img/wn/$iconName@2x.png").into(binding.imageViewForecastIcon)
        binding.textViewTemp.text = weatherDetails?.main?.temp.toString().substringBefore(".") + "°"
        binding.tempMinText.text = weatherDetails?.main?.tempMin.toString().substringBefore(".") + "°"
        binding.tempMaxText.text = weatherDetails?.main?.tempMax.toString().substringBefore(".") + "°"
    }
}