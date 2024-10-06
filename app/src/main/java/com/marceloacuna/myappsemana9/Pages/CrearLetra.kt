package com.marceloacuna.myappsemana9.Pages

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.marceloacuna.myappsemana9.Model.Letras_Model

/* FUNCION CREAR LETRA*/
@Composable
fun crealetra(){
    var nombreLetra by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var imagen by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally)

    {

        Spacer(modifier = Modifier.height(50.dp))


        //texto de pantalla Crear y su estilo
        androidx.compose.material3.Text(text = "Letra", fontSize = 28.sp,fontWeight = FontWeight.Bold)
        //salto de linea
        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            value = nombreLetra,
            onValueChange = { nombreLetra = it },
            label = { androidx.compose.material.Text("Nombre Letra") },
            modifier = Modifier.size(width = 380.dp, height = 60.dp),
            shape = RoundedCornerShape(40)
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { androidx.compose.material.Text("Descripcion Letra") },
            modifier = Modifier.size(width = 380.dp, height = 60.dp),
            shape = RoundedCornerShape(40)
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = imagen,
            onValueChange = { imagen = it },
            label = { androidx.compose.material.Text("Imagen") },
            modifier = Modifier.size(width = 380.dp, height = 60.dp),
            shape = RoundedCornerShape(40)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {

            when {
                nombreLetra == "" -> errorMessage = "Nombre no debe quedar vacio"
                descripcion == "" -> errorMessage = "Email no debe quedar vacio"
                imagen == "" -> errorMessage = "Password no debe quedar vacio"

                else -> {
                    val database = FirebaseDatabase.getInstance()
                    val ref = database.getReference("model_abecedario")

                    val model_abecedario = Letras_Model(
                        nombre = nombreLetra,
                        descripcion = descripcion,
                        imgen = imagen
                    )
                    ref.push().setValue(model_abecedario).addOnCompleteListener{task ->
                        if(task.isSuccessful)
                        {
                            Toast.makeText(context, "Letra creada", Toast.LENGTH_SHORT).show()
                        }
                        else
                        {
                            Toast.makeText(context, "Error al crear letra", Toast.LENGTH_SHORT).show()
                        }
                    }

                }
            }
        },shape = RoundedCornerShape(16.dp),
            modifier = Modifier.size(width = 280.dp, height = 40.dp)
        ) {
            androidx.compose.material.Text(
                "Crear",
                style = TextStyle(
                    fontSize = 20.sp, // Tamaño de fuente
                    fontWeight = FontWeight.Bold // Peso de la fuente //
                )
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (errorMessage.isNotEmpty()) {
            androidx.compose.material.Text(text = errorMessage, color = Color.Red)
        }
    }
}


/*obtener listado*/
private lateinit var database : DatabaseReference
private fun obtenerlistadoletras(letra: String){
    database = FirebaseDatabase.getInstance().getReference("model_abecedario")
    val letraRef = database.child(letra)

    letraRef.addListenerForSingleValueEvent(object: ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            if(snapshot.exists()) {
                val letraListadoIndo = snapshot.getValue(Letras_Model::class.java)

                letraListadoIndo?.let { abecedario ->
                    Log.d("listado de letras", "nombre: ${abecedario.nombre}, descripcion : ${abecedario.descripcion}, imagen: ${abecedario.imgen}")
                }?: run{
                    Log.d("listado de letras", "no existe letra")
                }
            }else
            {
                Log.d("listado de letras", "no existe letra")
            }
        }

        override fun onCancelled(error: DatabaseError) {
            Log.e("Error Data Letras", "Error al obtener datos: ${error.message}")
        }
    })
}