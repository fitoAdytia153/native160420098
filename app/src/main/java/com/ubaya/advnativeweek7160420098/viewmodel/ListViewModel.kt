package com.ubaya.advnativeweek7160420098.viewmodel

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
import com.ubaya.advnativeweek7160420098.model.Student

class ListViewModel(application: Application): AndroidViewModel(application)  {
    val studentsLD = MutableLiveData<ArrayList<Student>>()
    val studentLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()
    val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    fun refresh() {

        studentLoadErrorLD.value = false
        loadingLD.value = true

        queue = Volley.newRequestQueue( getApplication()  )
        val url = "http://adv.jitusolution.com/student.php?"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                try {
                    val gson = Gson()
                    val studentType = object : TypeToken<List<Student>>() {}.type
                    val students = gson.fromJson<List<Student>>(response, studentType)
                    studentsLD.value = ArrayList(students)
                    loadingLD.value = false
                } catch (e: Exception) {
                    Log.e(TAG, "Error parsing JSON: ", e)
                    studentLoadErrorLD.value = true
                    loadingLD.value = false
                }
            }
            ,
            { error ->
                Log.e(TAG, "Vollery error: $error")
                studentLoadErrorLD.value = true
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