package com.ubaya.advnativeweek7160420098.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.ubaya.advnativeweek7160420098.R
import com.ubaya.advnativeweek7160420098.databinding.StudentListItemsBinding
import com.ubaya.advnativeweek7160420098.model.Student

class StudentListAdapter(val studentList: ArrayList<Student>) : RecyclerView.Adapter<StudentListAdapter.StudentViewHolder>(),
    ButtonDetailClickListener {
    class StudentViewHolder(val view: StudentListItemsBinding) : RecyclerView.ViewHolder(view.root)

    fun updateStudentList(newStudentList: ArrayList<Student>) {
        studentList.clear()
        studentList.addAll(newStudentList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        //val view = inflater.inflate(R.layout.student_list_item, parent, false)
        val view = DataBindingUtil.inflate<StudentListItemsBinding>(inflater,
            R.layout.student_list_items, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.view.student = studentList[position]
        holder.view.listener = this

//        val student = studentList[position]
//        with(holder.binding) {
//            txtID.text = student.id.toString()
//            txtName.text = student.name
//            progressBar.visibility = View.VISIBLE
//
//            Picasso.get()
//                .load(student.photoUrl)
//                .into(imgStory, object : Callback {
//                    override fun onSuccess() {
//                        progressBar.visibility = View.GONE
//                    }
//
//                    override fun onError(e: Exception?) {
//                        progressBar.visibility = View.GONE
//                        imgStory.visibility = View.INVISIBLE
//                        Log.e("PicassoError", "Error loading image: ${e?.message}")
//                    }
//                })
//
//            holder.binding.btnDetail.setOnClickListener {
//                val studentId = studentList[position].id
//                val action = StudentListFragmentDirections.actionStudentDetail(studentId.toString())
//                it.findNavController().navigate(action)
//            }
//
//        }
    }

    override fun getItemCount() = studentList.size

}

