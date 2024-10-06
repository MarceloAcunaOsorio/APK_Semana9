package com.marceloacuna.myappsemana9.Pages

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.marceloacuna.myappsemana9.AuthViewModel
import com.marceloacuna.myappsemana9.CityWeatherInfo
import com.marceloacuna.myappsemana9.Model.FutureModel
import com.marceloacuna.myappsemana9.Model.HourlyModel
import com.marceloacuna.myappsemana9.R
import com.marceloacuna.myappsemana9.Routes
import com.marceloacuna.myappsemana9.ui.theme.MyAppSemana9Theme

class LocationActivity(modifier: Modifier, navController: NavHostController,authViewModel: AuthViewModel) : ComponentActivity() {
    private lateinit var locationHelper: LocationHelper
    private var currentCity by mutableStateOf<String?>("")
    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_location)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        locationHelper = LocationHelper(this, this)

        database = FirebaseDatabase.getInstance().getReference("ciudad")


        locationHelper.getCurrentCity { city ->
            city?.let {
                currentCity = it
                obtenerDatosCiudaddeFirebase(it)
            } ?: run {
                Toast.makeText(this, "No se puedo obtener la ciudad", Toast.LENGTH_LONG).show()
            }
        }
    }


    private fun obtenerDatosCiudaddeFirebase(city: String){
        val cityRef =  database.child(city)

        cityRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val cityWeatherIndo = snapshot.getValue(CityWeatherInfo::class.java)

                    cityWeatherIndo?.let { weather ->
                        Log.d("Dato de Clima", "Ciudad $city")
                        Log.d("Dato de Clima", "P. Lluvia ${weather.rainProbability}")
                        Log.d("Dato de Clima", "P. Viento ${weather.windSpeed}")
                        Log.d("Dato de Clima", "P. Humedad ${weather.humidity}")

                        weather.hourlyForecast.forEach { forecast ->
                            Log.d("Dato de Clima", "hora: ${forecast.hour}, Temperatura: ${forecast.temperature}")
                        }

                        weather.next7Day.forEach{ nextfday ->
                            Log.d("Dato de Clima", "${nextfday.day}, Temperatura: ${nextfday.maxTemp}")

                        }
                    } ?: run {
                        Log.d("Dato de Clima", "No tenemos datos para esta ciudad")
                    }
                }else{
                    Log.d("Dato de Clima", "No tenemos datos para esta ciudad")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Error Data Clima", "Error al obtener datos: ${error.message}")
            }
        })

    }


}


@Composable
fun MyApp(modifier: Modifier, navController: NavController, authViewModel: AuthViewModel) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Routes.home) }) {
                Text("Home")
            }
        }
    ) { innerPadding ->
        // Aquí puedes agregar el contenido de tu pantalla
        // Modifica el innerPadding para que no se superponga con el FAB
        Box(modifier = Modifier.padding(innerPadding)) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(android.graphics.Color.parseColor("#59469d")),
                                Color(android.graphics.Color.parseColor("#643d67"))
                            )
                        )
                    )

            )
            {
                Column(modifier = Modifier.fillMaxSize()) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        item {
                            Text(
                                text = "Tiempo MyApp",
                                fontSize = 20.sp,
                                color = Color.White,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 48.dp),
                                textAlign = TextAlign.Center
                            )

                            Image(
                                painter = painterResource(id = R.drawable.icono_mano),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(150.dp)
                                    .padding(top = 8.dp)
                            )

                            //Display date and time
                            Text(
                                text = "sabado 05 de octubre | 18:00 AM",
                                fontSize = 19.sp,
                                color = Color.White,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp),
                                textAlign = TextAlign.Center
                            )

                            //Display temerature details
                            Text(
                                text = "25°", fontSize = 63.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp),
                                textAlign = TextAlign.Center
                            )

                            Text(
                                text = "H:27 L:18",
                                fontSize = 16.sp,
                                color = Color.White,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp),
                                textAlign = TextAlign.Center
                            )

                            //Box containg weather details like rain, wind speed,humidity
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 24.dp, vertical = 16.dp)
                                    .background(
                                        color = colorResource(id = R.color.purple),
                                        shape = RoundedCornerShape(25.dp)
                                    )
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(100.dp)
                                        .padding(horizontal = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    WeatherDetailItem(
                                        icon = R.drawable.icono_mano,
                                        value = "22%",
                                        label = "Rain"
                                    )
                                    WeatherDetailItem(
                                        icon = R.drawable.icono_mano,
                                        value = "12 Km/h",
                                        label = "Wind Speed"
                                    )
                                    WeatherDetailItem(
                                        icon = R.drawable.icono_mano,
                                        value = "18%",
                                        label = "Humidity"
                                    )
                                }

                            }

                            //Displaying "Today" label
                            Text(
                                text = "Hoy",
                                fontSize = 20.sp,
                                color = Color.White,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 24.dp, vertical = 8.dp)
                            )
                        }

                        //Display future hourly forecast using a LazyRow
                        item {
                            LazyRow(
                                modifier = Modifier.fillMaxWidth(),
                                contentPadding = PaddingValues(horizontal = 20.dp),
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                items(items) { item ->
                                    FutureModelViewHolder(item)
                                }
                            }
                        }


                        //Display "Future" label and next 7 day button
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 24.dp, vertical = 16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Future",
                                    fontSize = 20.sp,
                                    color = Color.White,
                                    modifier = Modifier.weight(1f)
                                )
                                Text(
                                    text = "Next 7 day>",
                                    fontSize = 14.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )

                            }
                        }

                        items(dailyItems) {
                            FutureItem(item = it, navController)

                        }
                    }
                }
            }
        }
    }
}





//Display each future daily forecast item
@Composable
fun FutureItem(item: FutureModel, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = item.day,
            color = Color.White,
            fontSize = 14.sp
        )

        Image(
            painter = painterResource(id = getDrawableResourceId(picPath = item.picPath)),
            contentDescription = null, modifier = Modifier
                .padding(start = 32.dp)
                .size(45.dp)
        )
        Text(
            text = item.status,
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp),
            color = Color.White,
            fontSize = 14.sp
        )
        Text(
            text = "${item.highTemp}°",
            modifier = Modifier.padding(end = 16.dp),
            color = Color.White,
            fontSize = 14.sp
        )
        Text(
            text = "${item.lowTemp}°",
            color = Color.White,
            fontSize = 14.sp
        )
    }
}

@Composable
fun getDrawableResourceId(picPath: String): Int {
    return when (picPath) {
        "storm" -> R.drawable.icono_mano
        "cloudy" -> R.drawable.icono_mano
        "windy" -> R.drawable.icono_mano
        "cloudy_sunny" -> R.drawable.icono_mano
        "sunny" -> R.drawable.icono_mano
        "rainy" -> R.drawable.icono_mano
        else -> R.drawable.icono_mano
    }
}

//sample daily data
val dailyItems = listOf(
    FutureModel("Sab", "storm", "Storm", 24, 12),
    FutureModel("Dom", "cloudy", "Cloudy", 25, 16),
    FutureModel("Lun", "windy", "Windy", 29, 15),
    FutureModel("Mar", "cloudy_sunny", "Cloudy Sunny", 23, 15),
    FutureModel("Mie", "sunny", "Sunny", 28, 11),
    FutureModel("Jue", "rainy", "Rainy", 23, 12)
)

//Viewholder for each hourly forecast item
@Composable
fun FutureModelViewHolder(model: HourlyModel) {
    Column(
        modifier = Modifier
            .width(90.dp)
            .wrapContentHeight()
            .padding(4.dp)
            .background(
                color = colorResource(id = R.color.purple),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = model.hour,
            color = Color.White,
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            textAlign = TextAlign.Center
        )

        Image(
            painter = painterResource(
                id =
                when (model.picPath) {
                    "cloudy" -> R.drawable.icono_mano
                    "sunny" -> R.drawable.icono_mano
                    "wind" -> R.drawable.icono_mano
                    "rainy" -> R.drawable.icono_mano
                    "storm" -> R.drawable.icono_mano
                    else -> R.drawable.icono_mano
                }
            ), contentDescription = null,
            modifier = Modifier
                .size(45.dp)
                .padding(8.dp),
            contentScale = ContentScale.Crop
        )

        Text(
            text = "${model.temp}°",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            textAlign = TextAlign.Center
        )

    }
}


// sample hourly data
val items = listOf(
    HourlyModel("9 pm", 28, "cloudy"),
    HourlyModel("10 pm", 29, "sunny"),
    HourlyModel("11 pm", 30, "wind"),
    HourlyModel("12 pm", 31, "rainy"),
    HourlyModel("1 am", 32, "storm")
)

@Composable
fun WeatherDetailItem(icon: Int, value: String, label: String) {
    Column(
        modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = icon), contentDescription = null,
            modifier = Modifier.size(34.dp)
        )
        Text(
            text = value,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.white),
            textAlign = TextAlign.Center
        )
        Text(
            text = label,
            color = colorResource(id = R.color.white),
            textAlign = TextAlign.Center
        )
    }

}