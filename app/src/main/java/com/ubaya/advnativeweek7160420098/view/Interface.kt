package com.ubaya.advnativeweek7160420098.view

import android.view.View
import androidx.navigation.Navigation

interface ButtonDetailClickListener {
    fun onButtonDetailClick(v: View) {
        val action = StudentListFragmentDirections.actionStudentDetail(v.tag.toString())
        Navigation.findNavController(v).navigate(action)
    }
}

interface ButtonUpdateClickListener {
    fun onButtonUpdateClick(v: View) {
        val action = StudentListFragmentDirections.actionStudentDetail(v.tag.toString())
        Navigation.findNavController(v).navigate(action)
    }
}

interface ButtonCreateNotificationClickListener {
    fun onButtonCreateNotificationClick(v: View) {
        val action = StudentListFragmentDirections.actionStudentDetail(v.tag.toString())
        Navigation.findNavController(v).navigate(action)
    }
}
