package com.fao.orderfy.presentacion.actividades

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fao.orderfy.databinding.ActivityClienteBinding

class ClienteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityClienteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClienteBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}