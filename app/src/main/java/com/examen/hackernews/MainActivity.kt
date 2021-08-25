package com.examen.hackernews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.examen.hackernews.databinding.ActivityMainBinding
import com.examen.hackernews.databinding.FragmentHackerNewsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}