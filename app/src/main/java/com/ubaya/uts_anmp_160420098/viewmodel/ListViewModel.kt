package com.ubaya.uts_anmp_160420098.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ubaya.uts_anmp_160420098.model.Hero

class ListViewModel(application: Application): AndroidViewModel(application)  {
    val heroLD = MutableLiveData<List<Hero>>()
    val heroLoadErrorLD = MutableLiveData<String?>()
    val loadingLD = MutableLiveData<Boolean>()
    val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    fun refresh() {
        heroLoadErrorLD.value = null
        loadingLD.value = true
        Log.d("Cek", "volley masuk")

        queue = Volley.newRequestQueue( getApplication()  )
        val url = "http://192.168.0.171/hero/hero.json"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                try {
                    val gson = Gson()
                    val hero = gson.fromJson(response, Array<Hero>::class.java).toList()

                    if(hero.isNotEmpty()) {
                        heroLD.value = hero
                        loadingLD.value = false
                    } else {
                        heroLoadErrorLD.value = "Data kosong"
                        loadingLD.value = false
                    }

                    Log.d("show", hero.toString())
                } catch (e: Exception) {
                    Log.e(TAG, "Error parsing JSON: ", e)
                    heroLoadErrorLD.value = "Gagal memuat data"
                    loadingLD.value = false
                }
            }
            ,
            { error ->
                Log.e(TAG, "Volley error: $error")
                heroLoadErrorLD.value = "Gagal memuat data"
                loadingLD.value = false
            })

        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }
    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }
}