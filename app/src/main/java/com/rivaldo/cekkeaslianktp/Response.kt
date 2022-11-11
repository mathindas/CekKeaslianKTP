package com.rivaldo.cekkeaslianktp

import com.google.gson.annotations.SerializedName

data class Response(

	@field:SerializedName("provinsi")
	val provinsi: Provinsi? = null,

	@field:SerializedName("kecamatan")
	val kecamatan: Kecamatan? = null,

	@field:SerializedName("kabkot")
	val kabkot: Kabkot? = null
)
