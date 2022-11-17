package com.rivaldo.cekkeaslianktp

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.rivaldo.cekkeaslianktp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSearch.setOnClickListener {
            binding.ivStatus.visibility = View.VISIBLE
            binding.tvValid.visibility = View.VISIBLE
            if (getInfo()) {
                binding.tlView.visibility = View.VISIBLE
                binding.ivStatus.setImageResource(R.drawable.ic_baseline_check_circle_24)
                binding.tvValid.text = getString(R.string.valid)
                binding.tvValid.setTextColor(resources.getColor(R.color.green))
            }else{
                binding.tlView.visibility = View.GONE
                binding.ivStatus.setImageResource(R.drawable.ic_round_error_24)
                binding.tvValid.text = getString(R.string.invalid)
                binding.tvValid.setTextColor(resources.getColor(R.color.red))
            }
        }
    }

    private fun getInfo(): Boolean {
        val noKtp = binding.etKtp.text.toString()
        var isValid = true
        try {
            val kodeProvinsi = noKtp.substring(0, 2)
            val kodeKabkot = noKtp.substring(0, 4)
            val kodeKecamatan = noKtp.substring(0, 6)
            val kodeTanggalLahir = noKtp.substring(6, 8).toInt()
            val kodeBulanLahir = noKtp.substring(8, 10).toInt()
            val kodeTahunLahir = noKtp.substring(10, 12).toInt()
            val kodeUnikKomputeriasi = noKtp.substring(12, 16)

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
            val tanggalLahir =
                viewModel.getHari(tahun, kodeBulanLahir, tanggal) + ", $tanggal $bulan $tahun"

            val usia = viewModel.getAge(tahun, kodeBulanLahir, tanggal)
            val zodiak = viewModel.getZodiak(kodeBulanLahir, tanggal)
            val ulangTahun = viewModel.getBirthday(kodeBulanLahir, tanggal)
            val shio = viewModel.getShio(tahun)

            if (provinsi.equals(null) || kabkot.equals(null) || kecamatan.equals(null) ||
                tanggal > 31 || kodeBulanLahir > 12
            ) isValid = false

            binding.apply {
                tvKtp.text = noKtp
                tvProvince.text = provinsi
                tvCity.text = kabkot
                tvDistrict.text = namaKecamatan
                tvPostalCode.text = kodePosKecamatan
                tvSex.text = jenisKelamin
                tvBirthdate.text = tanggalLahir
                tvAge.text = usia
                tvBirthday.text = ulangTahun
                tvZodiac.text = zodiak
                tvShio.text = shio
                tvCode.text = kodeUnikKomputeriasi
            }

        } catch (e: Exception) {
            e.printStackTrace()
            isValid = false
        }

        return isValid
    }
}