package com.fao.orderfy.presentacion.actividades

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fao.orderfy.databinding.ActivityMainBinding
import com.fao.orderfy.datos.Entidades.Tienda

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}