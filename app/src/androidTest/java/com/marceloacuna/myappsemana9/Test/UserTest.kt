package com.marceloacuna.myappsemana9.Test

import com.marceloacuna.myappsemana9.Model.User_Model
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class userTest {
    private lateinit var usermodel : User_Model

    @Before
    fun setUp() {
        usermodel = User_Model("estudiante@gmail.com", "@123gHft")
    }

    @Test
    fun testGetemail() {
        assertEquals("estudiante@gmail.com",usermodel.email, "estudiante@gmail.com")
    }

    @Test
    fun testGetPassword(){
        assertEquals("@123gHft",usermodel.contraseña,"@123gHft")
    }
}