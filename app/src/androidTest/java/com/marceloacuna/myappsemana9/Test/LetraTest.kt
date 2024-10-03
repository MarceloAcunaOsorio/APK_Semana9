package com.marceloacuna.myappsemana9.Test

import com.marceloacuna.myappsemana9.Model.Letras_Model
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class letraTest {
    private lateinit var letramodel: Letras_Model

    @Before
    fun setUp() {
        letramodel = Letras_Model("Letra A", "Descripción de la imagen A", "imagenA.png")
    }


    @Test
    fun testGetNombre() {
        assertEquals("Letra A", letramodel.nombre, "Letra A")
    }

    @Test
    fun testGetDescripcion() {
        assertEquals("Descripción de la imagen A", letramodel.descripcion, "Descripción de la imagen A")
    }

    @Test
    fun testGetImagen() {
        assertEquals("imagenA.png", letramodel.imgen, "imagenA.png")
    }
}