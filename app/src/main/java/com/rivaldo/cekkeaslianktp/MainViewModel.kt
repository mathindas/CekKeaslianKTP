package com.rivaldo.cekkeaslianktp

import android.content.Context
import androidx.lifecycle.ViewModel
import com.rivaldo.cekkeaslianktp.Utils.toMap
import org.json.JSONObject

class MainViewModel : ViewModel() {

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