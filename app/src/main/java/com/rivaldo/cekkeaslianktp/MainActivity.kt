package com.rivaldo.cekkeaslianktp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.rivaldo.cekkeaslianktp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSearch.setOnClickListener {
            val text = binding.etKtp.text.toString()
            val text2 = viewModel.getValueProvinsi(text, baseContext) + viewModel.getValueKabkot(text, baseContext) + viewModel.getValueKecamatan(text, baseContext)
            binding.tv.text = text2
        }
    }

}