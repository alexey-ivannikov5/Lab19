package ru.alexeyivannikov.lab19

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.alexeyivannikov.lab19.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }
}