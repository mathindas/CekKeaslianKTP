package com.rivaldo.cekkeaslianktp

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
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
        window.sharedElementEnterTransition.duration = 500

        binding.etKtp.setText(intent.extras?.get(EXTRAS_TEXT).toString())
        updateView()

        binding.filledTextField.setEndIconOnClickListener { updateView() }
        binding.etKtp.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    updateView()
                    return true
                }
                return false
            }
        })
    }

    private fun updateView(){
        binding.animationView.visibility = View.VISIBLE
        if (getInfo()) {
            binding.apply {
                tlView.visibility = View.VISIBLE
                llView.visibility = View.GONE
                animationView.setAnimation(R.raw.success)
                animationView.playAnimation()
                tvStatus.text = getString(R.string.valid)
                tvStatus.setTextColor(baseContext.getColor(R.color.green))
            }
        }else{
            binding.apply {
                tlView.visibility = View.GONE
                llView.visibility = View.VISIBLE
                animationView.setAnimation(R.raw.error)
                animationView.playAnimation()
                tvStatus.text = getString(R.string.invalid)
                tvStatus.setTextColor(baseContext.getColor(R.color.red))
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

    companion object{
        const val EXTRAS_TEXT = "EXTRASTEXT"
    }
}