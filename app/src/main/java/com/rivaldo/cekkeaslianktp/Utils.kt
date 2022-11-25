package com.rivaldo.cekkeaslianktp

import org.json.JSONArray
import org.json.JSONObject
import java.time.LocalDate
import java.time.Period

class Utils {
    companion object {
        fun JSONObject.toMap(): Map<String, *> = keys().asSequence().associateWith {
            when (val value = this[it]) {
                is JSONArray -> {
                    val map = (0 until value.length()).associate { Pair(it.toString(), value[it]) }
                    JSONObject(map).toMap().values.toList()
                }
                is JSONObject -> value.toMap()
                JSONObject.NULL -> null
                else -> value
            }
        }

        fun getVersion():String{
            return "Version : ${BuildConfig.VERSION_NAME}"
        }
    }

}