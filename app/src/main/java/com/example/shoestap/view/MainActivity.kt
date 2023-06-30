package com.example.shoestap.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.shoestap.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // NOTA: Uso Navigator para gestionar las ventanas,
        // cada ventana es un fragment
    }
}