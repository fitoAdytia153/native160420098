package com.ubaya.uts_anmp_160420098.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.ubaya.uts_anmp_160420098.model.Account
import com.ubaya.uts_anmp_160420098.model.Hero

class ViewModel(application: Application) : AndroidViewModel(application){
    private val prefs = application.getSharedPreferences("account", Context.MODE_PRIVATE)
    val visited = MutableLiveData<Hero?>()
    val accountLD = MutableLiveData<Account?>()
    var selectedHeroId: Int = -1

    fun clearAccount() {
        prefs.edit().clear().apply()
        accountLD.value = null
    }

    fun lastVisited(hero: Hero) {
        visited.value = hero
    }

    fun fetch(username: String) {
        val url = "http://192.168.0.171/hero/get_akun.php"
        val request = object : StringRequest(Method.POST, url,
            Response.Listener<String> { response ->
                val gson = Gson()
                val user = gson.fromJson(response, Account::class.java)
                accountLD.postValue(user)
            },
            Response.ErrorListener { error ->
                Log.e("fetchError", error.toString())
            }) {
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["username"] = username
                return params
            }
        }

        Volley.newRequestQueue(getApplication<Application>().applicationContext).add(request)
    }
}
