package com.marceloacuna.myappsemana9

import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database


data class HourlyForecast(
    val hour: String = "",
    val temperature: Int = 0
)

data class FuturoModel(
    val day: String = "",
    val state: String = "",
    val descripcion: String = "",
    val maxTemp: Int = 0,
    val minTemp: Int = 0
)

data class CityWeatherInfo(
    val rainProbability : Int = 0,
    val windSpeed   : Int = 0,
    val humidity :  Int = 0,
    val hourlyForecast: List<HourlyForecast> = listOf(),
    val next7Day: List<FuturoModel> = listOf(),
)

class ServidorClima {
    private val database: DatabaseReference = Firebase.database.reference.child("ciudad")

    fun enviarDatosClima(weatherData: Map<String, CityWeatherInfo>){
        database.setValue(weatherData).addOnSuccessListener {
            println("Datos enviados!")
        }.addOnFailureListener { error ->
            println("error al enviar los datos: ${error.message}")
        }
    }


    fun crearDatosClima(): Map<String, CityWeatherInfo>{
        return mapOf(
            "Colina" to CityWeatherInfo(
                rainProbability = 40,
                windSpeed = 15,
                humidity =  65,
                hourlyForecast = listOf(
                    HourlyForecast("08:00", 12),
                    HourlyForecast("09:00", 18),
                    HourlyForecast("10:00", 20),
                    HourlyForecast("11:00", 21)
                ),
                next7Day = listOf(
                    FuturoModel("Lunes", "cloudy", "Cloudy", 27, 16),
                    FuturoModel("Martes", "cloudy", "Cloudy", 27, 16),
                    FuturoModel("Miercoles", "cloudy", "Cloudy", 27, 16),
                    FuturoModel("Jueves", "cloudy", "Cloudy", 27, 16),
                    FuturoModel("Viernes", "cloudy", "Cloudy", 27, 16),
                    FuturoModel("Sabado", "cloudy", "Cloudy", 27, 16),
                    FuturoModel("Domingo", "cloudy", "Cloudy", 27, 16)
                )
            ),
            "Quito" to CityWeatherInfo(
                rainProbability = 40,
                windSpeed = 15,
                humidity = 65,
                hourlyForecast = listOf(
                    HourlyForecast("08:00", 12),
                    HourlyForecast("12:00", 18),
                    HourlyForecast("16:00", 22),
                    HourlyForecast("20:00", 19)
                ),
                next7Day = listOf(
                    FuturoModel("Lunes", "cloudy", "Cloudy", 28, 16),
                    FuturoModel("Martes", "cloudy", "Cloudy", 28, 16),
                    FuturoModel("Miercoles", "cloudy", "Cloudy", 28, 16),
                    FuturoModel("Jueves", "cloudy", "Cloudy", 28, 16),
                    FuturoModel("Viernes", "cloudy", "Cloudy", 28, 16),
                    FuturoModel("Sabado", "cloudy", "Cloudy", 28, 16),
                    FuturoModel("Domingo", "cloudy", "Cloudy", 28, 16)
                )
            ),
            "Buenos Aires" to CityWeatherInfo(
                rainProbability = 70,
                windSpeed = 20,
                humidity = 80,
                hourlyForecast = listOf(
                    HourlyForecast("08:00", 14),
                    HourlyForecast("12:00", 20),
                    HourlyForecast("16:00", 25),
                    HourlyForecast("20:00", 22)
                ),
                next7Day = listOf(
                    FuturoModel("Lunes", "cloudy", "Cloudy", 21, 16),
                    FuturoModel("Martes", "cloudy", "Cloudy", 21, 16),
                    FuturoModel("Miercoles", "cloudy", "Cloudy", 21, 16),
                    FuturoModel("Jueves", "cloudy", "Cloudy", 21, 16),
                    FuturoModel("Viernes", "cloudy", "Cloudy", 21, 16),
                    FuturoModel("Sabado", "cloudy", "Cloudy", 21, 16),
                    FuturoModel("Domingo", "cloudy", "Cloudy", 21, 16)
                )
            ),
            //New York Mountain View
            "Mountain View" to CityWeatherInfo(
                rainProbability = 50,
                windSpeed = 18,
                humidity = 60,
                hourlyForecast = listOf(
                    HourlyForecast("08:00", 10),
                    HourlyForecast("12:00", 15),
                    HourlyForecast("16:00", 20),
                    HourlyForecast("20:00", 17)
                ),
                next7Day = listOf(
                    FuturoModel("Lunes", "cloudy", "Cloudy", 22, 16),
                    FuturoModel("Martes", "cloudy", "Cloudy", 22, 16),
                    FuturoModel("Miercoles", "cloudy", "Cloudy", 22, 16),
                    FuturoModel("Jueves", "cloudy", "Cloudy", 22, 16),
                    FuturoModel("Viernes", "cloudy", "Cloudy", 22, 16),
                    FuturoModel("Sabado", "cloudy", "Cloudy", 22, 16),
                    FuturoModel("Domingo", "cloudy", "Cloudy", 22, 16)
                )
            ),

            "London" to CityWeatherInfo(
                rainProbability = 80,
                windSpeed = 25,
                humidity = 85,
                hourlyForecast = listOf(
                    HourlyForecast("08:00", 9),
                    HourlyForecast("12:00", 14),
                    HourlyForecast("16:00", 18),
                    HourlyForecast("20:00", 16)
                ),
                next7Day = listOf(
                    FuturoModel("Lunes", "cloudy", "Cloudy", 22, 16),
                    FuturoModel("Martes", "cloudy", "Cloudy", 22, 16),
                    FuturoModel("Miercoles", "cloudy", "Cloudy", 22, 16),
                    FuturoModel("Jueves", "cloudy", "Cloudy", 22, 16),
                    FuturoModel("Viernes", "cloudy", "Cloudy", 22, 16),
                    FuturoModel("Sabado", "cloudy", "Cloudy", 22, 16),
                    FuturoModel("Domingo", "cloudy", "Cloudy", 22, 16)
                )
            ),

            "Tokyo" to CityWeatherInfo(
                rainProbability = 30,
                windSpeed = 12,
                humidity = 70,
                hourlyForecast = listOf(
                    HourlyForecast("08:00", 18),
                    HourlyForecast("12:00", 23),
                    HourlyForecast("16:00", 27),
                    HourlyForecast("20:00", 24)
                ),
                next7Day = listOf(
                    FuturoModel("Lunes", "cloudy", "Cloudy", 23, 16),
                    FuturoModel("Martes", "cloudy", "Cloudy", 23, 16),
                    FuturoModel("Miercoles", "cloudy", "Cloudy", 23, 16),
                    FuturoModel("Jueves", "cloudy", "Cloudy", 23, 16),
                    FuturoModel("Viernes", "cloudy", "Cloudy", 23, 16),
                    FuturoModel("Sabado", "cloudy", "Cloudy", 24, 16),
                    FuturoModel("Domingo", "cloudy", "Cloudy", 23, 16)
                )
            )



        )
    }


}