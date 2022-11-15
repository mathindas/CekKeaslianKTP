package com.rivaldo.cekkeaslianktp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.rivaldo.cekkeaslianktp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSearch.setOnClickListener {
            getInfo()
        }
    }

    private fun getInfo() : Boolean{
        val text = binding.etKtp.text.toString()
        var isValid = true
        try {
            val kodeProvinsi = text.substring(0, 2)
            val kodeKabkot = text.substring(0, 4)
            val kodeKecamatan = text.substring(0, 6)
            val kodeTanggalLahir = text.substring(6, 8).toInt()
            val kodeBulanLahir = text.substring(8, 10).toInt()
            val kodeTahunLahir = text.substring(10, 12).toInt()
            val kodeUnikKomputeriasi = text.substring(12, 16)

            val provinsi = viewModel.getValueProvinsi(kodeProvinsi, baseContext)
            val kabkot = viewModel.getValueKabkot(kodeKabkot, baseContext)

            val kecamatan =
                viewModel.getValueKecamatan(kodeKecamatan, baseContext).split(" -- ")
            val namaKecamatan = kecamatan.first()
            val kodePosKecamatan = kecamatan.last()

            val isLaki = kodeTanggalLahir < 41
            val jenisKelamin = if (isLaki) "Laki-laki" else "Perempuan"
            val tanggal = if (isLaki) kodeTanggalLahir else (kodeTanggalLahir - 40)
            val bulan = viewModel.getBulan(kodeBulanLahir)
            val tahun = viewModel.getTahun(kodeTahunLahir)
            val tanggalLahir = viewModel.getHari(tahun, kodeBulanLahir, tanggal) + "$tanggal $bulan $tahun"

            val usia = viewModel.getAge(tahun, kodeBulanLahir, tanggal)
            val zodiak = viewModel.getZodiak(kodeBulanLahir, tanggal)
            val ulangTahun = viewModel.getBirthday(kodeBulanLahir, tanggal)
            val shio = viewModel.getShio(tahun)

            if (provinsi.equals(null) || kabkot.equals(null) || kecamatan.equals(null) ||
                tanggal > 31 || kodeBulanLahir > 12
            ) isValid = false

            val text2 =
                "$provinsi\n$kabkot\n$namaKecamatan\n$kodePosKecamatan\n$jenisKelamin\n$tanggalLahir\n$usia\n$zodiak\n$ulangTahun\n$shio"
            binding.tv.text = text2

        } catch (e: Exception) {
            e.printStackTrace()
            isValid = false
        }

        return isValid
    }
}