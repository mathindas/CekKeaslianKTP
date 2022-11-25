package com.rivaldo.cekkeaslianktp

import android.content.Context
import androidx.lifecycle.ViewModel
import com.rivaldo.cekkeaslianktp.Utils.Companion.toMap
import org.json.JSONObject
import java.time.LocalDate
import java.time.Period

class MainViewModel : ViewModel() {

    fun getHari(year: Int, month: Int, dayOfMonth: Int) : String{
        return when(LocalDate.of(year, month, dayOfMonth).dayOfWeek.value){
            1 -> "Senin"
            2 -> "Selasa"
            3 -> "Rabu"
            4 -> "Kamis"
            5 -> "Jumat"
            6 -> "Sabtu"
            7 -> "Minggu"
            else -> "-"
        }
    }

    fun getBulan(month: Int): String{
        return when(month){
            1 -> "Januari"
            2 -> "Febuari"
            3 -> "Maret"
            4 -> "April"
            5 -> "Mei"
            6 -> "Juni"
            7 -> "Juli"
            8 -> "Agustus"
            9 -> "September"
            10 -> "Oktober"
            11-> "November"
            12 -> "Desember"
            else -> "-"
        }
    }

    fun getTahun(year: Int): Int{
        return when(year){
            in 1..21 -> "20$year".toInt()
            else -> "19$year".toInt()
        }
    }

    fun getAge(year: Int, month: Int, dayOfMonth: Int): String {
        val period = Period.between(LocalDate.of(year, month, dayOfMonth), LocalDate.now())
        return "${period.years} Tahun - ${period.months} Bulan - ${period.days} Hari"
    }

    fun getBirthday(month: Int, dayOfMonth: Int):String{
        val period = Period.between(LocalDate.of(LocalDate.now().year, month, dayOfMonth), LocalDate.now())
        var returnText = "${period.days} Hari Lagi"
        if(period.months!=0) returnText = "${period.months} Bulan - " + returnText
        return returnText
    }

    fun getZodiak(month: Int, dayOfMonth: Int):String{
        if (1 == month && dayOfMonth >= 20 || 2 == month && dayOfMonth < 19) return "Aquarius"
        else if (2 == month && dayOfMonth >= 19 || 3 == month && dayOfMonth < 21) return "Pisces"
        else if (3 == month && dayOfMonth >= 21 || 4 == month && dayOfMonth < 20) return "Aries"
        else if(4 == month && dayOfMonth >= 20 || 5 == month && dayOfMonth < 21) return "Taurus"
        else if (5 == month && dayOfMonth >= 21 || 6 == month && dayOfMonth < 22) return "Gemini"
        else if (6 == month && dayOfMonth >= 21 || 7 == month && dayOfMonth < 23) return "Cancer"
        else if (7 == month && dayOfMonth >= 23 || 8 == month && dayOfMonth < 23) return "Leo"
        else if (8 == month && dayOfMonth >= 23 || 9 == month && dayOfMonth < 23) return "Virgo"
        else if (9 == month && dayOfMonth >= 23 || 10 == month && dayOfMonth < 24) return "Libra"
        else if (10 == month && dayOfMonth >= 24 || 11 == month && dayOfMonth < 23) return "Scorpio"
        else if (11 == month && dayOfMonth >= 23 || 12 == month && dayOfMonth < 22) return "Sagitarius"
        else if (12 == month && dayOfMonth >= 22 || 1 == month && dayOfMonth < 20) return "Capricorn"
        else return "-"
    }

    fun getShio(year: Int): String{
        return when(year%12){
            0 -> "Monyet"
            1 -> "Ayam"
            2 -> "Anjing"
            3 -> "Babi"
            4 -> "Tikus"
            5 -> "Kerbau"
            6 -> "Harimau"
            7 -> "Kelinci"
            8 -> "Naga"
            9 -> "Ular"
            10 -> "Kuda"
            11 -> "Kambing"
            else -> "-"
        }
    }

    fun getValueProvinsi(key: String, context : Context) : String{
        return getMapProvinsi(context)[key].toString()
    }

    fun getValueKabkot(key: String, context : Context) : String{
        return getMapKabkot(context)[key].toString()
    }

    fun getValueKecamatan(key: String, context : Context) : String{
        return getMapKecamatan(context)[key].toString()
    }

    fun getMapProvinsi(context : Context) : Map<String, *> {
        val text = context.assets.open("provinsi.json").bufferedReader().use { it.readText() }
        return JSONObject(text).toMap()
    }

    fun getMapKabkot(context : Context) : Map<String, *> {
        val text = context.assets.open("kabkot.json").bufferedReader().use { it.readText() }
        return JSONObject(text).toMap()
    }

    fun getMapKecamatan(context : Context) : Map<String, *> {
        val text = context.assets.open("kecamatan.json").bufferedReader().use { it.readText() }
        return JSONObject(text).toMap()
    }
}