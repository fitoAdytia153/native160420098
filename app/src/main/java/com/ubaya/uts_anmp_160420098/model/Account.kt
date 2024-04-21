package com.ubaya.uts_anmp_160420098.model

import com.google.gson.annotations.SerializedName

data class Account(
    @SerializedName("nama_depan")
    var firstName:String,
    @SerializedName("nama_belakang")
    var lastName:String?,
    var username:String?,
    var password:String?
)

