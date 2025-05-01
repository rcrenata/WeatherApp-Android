package com.example.weatherapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cityInput = findViewById<EditText>(R.id.cityInput)
        val searchButton = findViewById<Button>(R.id.searchButton)
        val weatherResult = findViewById<TextView>(R.id.weatherResult)

        searchButton.setOnClickListener {
            val cidade = cityInput.text.toString()
            val apiKey = "2d0ab39638708336660fff3a15994807"
            val url = "https://api.openweathermap.org/data/2.5/weather?q=$cidade&appid=$apiKey&units=metric&lang=pt"

            val queue = Volley.newRequestQueue(this)

            val request = StringRequest(Request.Method.GET, url, { response ->
                val json = JSONObject(response)
                val temperatura = json.getJSONObject("main").getDouble("temp")
                val descricao = json.getJSONArray("weather")
                    .getJSONObject(0)
                    .getString("description")

                weatherResult.text = "Clima em $cidade: $descricao, $temperatura°C"
            }, {
                weatherResult.text = "Erro ao buscar clima. Verifique o nome da cidade."
            })

            queue.add(request)
        }
    }
}
