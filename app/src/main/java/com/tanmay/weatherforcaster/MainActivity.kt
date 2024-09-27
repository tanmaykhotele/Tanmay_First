package com.tanmay.weatherforcaster

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import com.tanmay.weatherforcaster.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        RetrofitInstance.api.getWeather("nagpur", "77b3e1a87df4e652797dcff93a87cf7c", "metric")
            .enqueue(object : Callback<ResponseDataClass> {
                override fun onResponse(
                    call: Call<ResponseDataClass>, response: Response<ResponseDataClass>
                ) {
                    if (response.isSuccessful) {
                        val temp = response.body()?.main?.temp.toString()
                        val min = response.body()?.main?.tempMin.toString()
                        val max = response.body()?.main?.tempMax.toString()
                        val sunR = response.body()?.sys?.sunrise.toString()
                        val sunS = response.body()?.sys?.sunset.toString()
                        val coun = response.body()?.sys?.country.toString()
                        Log.d("TAG", "onResponse: $temp")
                        binding.temperature.text = temp + "℃"
                        binding.min.text = "Min: $min ℃"
                        binding.max.text = "Max: $max ℃"
                        binding.sunrise.text = sunR
                        binding.sunset.text = sunS
                        binding.country.text = coun
                        binding.date.text = date()
                        binding.day.text = day(System.currentTimeMillis())
                    }
                }

                override fun onFailure(call: Call<ResponseDataClass>, t: Throwable) {
                    Log.i(TAG, "onFailure: error")
                }

            })
    }



    private fun date(): String {
        val sd = SimpleDateFormat("dd/MM/YYYY", Locale.getDefault())
        return sd.format(Date())
    }

    fun day(timestamp: Long): String {
        val sd = SimpleDateFormat("EEEE", Locale.getDefault())
        return sd.format(Date())
    }
}