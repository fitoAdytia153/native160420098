package com.ubaya.uts_anmp_160420098.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.ubaya.uts_anmp_160420098.model.Hero

class DetailViewModel(application: Application) : AndroidViewModel(application){
    val heroLD = MutableLiveData<Hero>()

    fun fetch(heroId: Int) {
            val url = "http://192.168.0.171/hero/hero.php?id=$heroId"
        val request = StringRequest(Request.Method.GET, url, { response ->
            val gson = Gson()
            val hero = gson.fromJson(response, Hero::class.java)
            heroLD.postValue(hero)
        }, { error ->
            Log.e("fetchError", error.toString())
        })

        Volley.newRequestQueue(getApplication<Application>().applicationContext).add(request)
    }
}
