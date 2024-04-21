package com.ubaya.uts_anmp_160420098.model

import com.google.gson.annotations.SerializedName

data class Hero(
    var id:Int,
    var name:String?,
    var author:String?,
    var description:String?,
    @SerializedName("photo_url")
    var photoUrl:String?,
    @SerializedName("skill_name")
    var skillName:List<String>,
    @SerializedName("skill_description")
    var skillDesc: List<String>
)

