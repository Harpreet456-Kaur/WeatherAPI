package com.example.weatherapi

import android.content.Context
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapi.API.WeatherData
import com.example.weatherapi.API.WeatherInstance
import com.example.weatherapi.API.WeatherInterface
import com.example.weatherapi.databinding.ActivityTemperatureBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime

class Temperature : AppCompatActivity() {
    lateinit var binding: ActivityTemperatureBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTemperatureBinding.inflate(layoutInflater)

        binding.bar.visibility = View.VISIBLE
            checkPermissions()

        setContentView(binding.root)
    }


    private fun getData() {
        WeatherInstance.weatherInstance().create(WeatherInterface::class.java)
            .postData("30.6995988", "76.6927953", "90c5a8ba8fb9ff8e2ddb6495277008be")
            .enqueue(object : Callback<WeatherData?> {
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onResponse(
                    call: Call<WeatherData?>, response: Response<WeatherData?>
                ) {
                    val date = LocalDateTime.now().toString()
                    var temp = ((response.body()!!.main.temp) - 273).toInt()
                    var feel = ((response.body()!!.main.feels_like) - 273).toInt()

//                    val instant = Instant.ofEpochMilli(response.body()!!.sys.sunrise.toLong())
//                    val zoneId = ZoneId.systemDefault()
//                    val localTime = instant.atZone(zoneId)
//                    val formatString = "HH:mm:ss"
//                    val formattedTime = localTime.format(DateTimeFormatter.ofPattern(formatString))
//                    Log.d("TAG--->",formattedTime)

                    //val mini = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(response.body()!!.sys.sunrise*1000)
                    binding.bar.visibility = View.GONE
                    binding.day.text = date
                    binding.city.text = response.body()!!.name
                    binding.feelNum.text = feel.toString() + "  C"
                    binding.humidityNum.text = response.body()!!.main.humidity.toString() + "%"
                    binding.pressureNum.text = response.body()!!.main.pressure.toString() + "mb"
                    binding.windNum.text = response.body()!!.wind.speed.toString() + "km/h"
                    binding.groundNum.text = response.body()!!.main.grnd_level.toString()
                    binding.seaNum.text = response.body()!!.main.sea_level.toString()
                    binding.temp.text = temp.toString() + " C"
                    binding.riseNum.text = response.body()!!.sys.sunrise.toString()
                    binding.setNum.text = response.body()!!.sys.sunset.toString()
                    Log.d("TAG--->", response.body().toString())
                }

                override fun onFailure(call: Call<WeatherData?>, t: Throwable) {
                    Log.d("TAG--->", t.message.toString())
                }
            })
    }

    private fun checkPermissions() {
        if (isLocationEnabled()){
            getData()
        }
        else
        {
            Toast.makeText(this, "Please turn on location", Toast.LENGTH_LONG).show()
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }
}

