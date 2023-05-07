package com.fao.orderfy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fao.orderfy.databinding.ActivityVendedorBinding

class VendedorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVendedorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVendedorBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_vendedor)
    }
}