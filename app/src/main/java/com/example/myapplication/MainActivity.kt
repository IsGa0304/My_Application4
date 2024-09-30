package com.example.myapplication

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // Datos de ejemplo: lista de comidas típicas
    private val comidas = listOf(
        Comida("Tacos", "México", R.drawable.tacos, "Deliciosos tacos mexicanos."),
        Comida("Sushi", "Japón", R.drawable.sushi, "Fresco sushi japonés."),
        Comida("Pizza", "Italia", R.drawable.pizza, "Clásica pizza italiana."),
        Comida("Croissant", "Francia", R.drawable.croissant, "Esponjoso croissant francés."),
        Comida("Paella", "España", R.drawable.paella, "Tradicional paella española."),
        Comida("Kimchi", "Corea del Sur", R.drawable.kimchi, "Kimchi picante de Corea."),
        Comida("Curry", "India", R.drawable.curry, "Curry sabroso de la India."),
        Comida("Bratwurst", "Alemania", R.drawable.bratwurst, "Salchicha Bratwurst alemana."),
        Comida("Poutine", "Canadá", R.drawable.poutine, "Patatas con queso y salsa."),
        Comida("Feijoada", "Brasil", R.drawable.feijoada, "Guiso brasileño de frijoles.")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializamos el ListView
        val listView: ListView = findViewById(R.id.listView)

        // Creamos y asignamos el adaptador
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, comidas.map { it.nombre })
        listView.adapter = adapter

        // Configuramos la acción al hacer clic en un ítem de la lista
        listView.setOnItemClickListener { parent, view, position, id ->
            mostrarDialogo(comidas[position])
        }
    }

    // Función para mostrar el diálogo con la información de la comida seleccionada
    @SuppressLint("MissingInflatedId")
    private fun mostrarDialogo(comida: Comida) {
        // Configuramos el diálogo
        val builder = AlertDialog.Builder(this)
        builder.setTitle(comida.nombre)

        // Creamos una vista personalizada para el contenido del diálogo
        val dialogView = layoutInflater.inflate(R.layout.dialog_comida, null)
        val imageView: ImageView = dialogView.findViewById(R.id.dialogImage)
        val textView: TextView = dialogView.findViewById(R.id.dialogText)

        // Asignamos los datos a la vista
        imageView.setImageResource(comida.imagenRes)
        textView.text = comida.descripcion

        builder.setView(dialogView)

        // Añadimos el botón de compartir
        builder.setPositiveButton("Compartir") { dialog, which ->
            compartirComida(comida)
        }
        builder.setNegativeButton("Cerrar", null)

        // Mostramos el diálogo
        val dialog = builder.create()
        dialog.show()
    }

    // Función para compartir la información del platillo
    private fun compartirComida(comida: Comida) {
        val compartirIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, comida.nombre)
            putExtra(Intent.EXTRA_TEXT, "Disfruta de este platillo: ${comida.nombre} de ${comida.pais}.")
        }
        startActivity(Intent.createChooser(compartirIntent, "Compartir usando"))
    }

    // Modelo de datos para Comida
    data class Comida(
        val nombre: String,
        val pais: String,
        val imagenRes: Int,
        val descripcion: String
    )
}