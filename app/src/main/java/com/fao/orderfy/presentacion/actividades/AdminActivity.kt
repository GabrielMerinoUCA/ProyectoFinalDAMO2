package com.fao.orderfy.presentacion.actividades

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fao.orderfy.databinding.ActivityAdminBinding

class AdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}