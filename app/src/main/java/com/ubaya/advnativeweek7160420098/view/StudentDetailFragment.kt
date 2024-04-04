package com.ubaya.advnativeweek7160420098.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import com.ubaya.advnativeweek7160420098.R
import com.ubaya.advnativeweek7160420098.databinding.FragmentStudentDetailBinding
import com.ubaya.advnativeweek7160420098.viewmodel.DetailViewModel
import java.util.concurrent.TimeUnit
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.disposables.Disposable


class StudentDetailFragment : Fragment() {

    private lateinit var viewModel: DetailViewModel
    private lateinit var binding: FragmentStudentDetailBinding
    private var subscription: Disposable? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        val args: StudentDetailFragmentArgs by navArgs()
        val studentId = args.studentId

        viewModel.fetch(studentId)

        viewModel.studentLD.observe(viewLifecycleOwner, Observer { student ->
            binding.txtId.setText(student.id.toString())
            binding.txtName.setText(student.name)
            binding.txtBod.setText(student.dob)
            binding.txtPhone.setText(student.phone)

            Picasso.get().load(student.photoUrl).into(binding.imgStudent)

            subscription?.dispose()

            binding.btnUpdate.setOnClickListener {
                subscription = io.reactivex.rxjava3.core.Observable.timer(5, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        Log.d("Messages", "five seconds")
                        student.name?.let { MainActivity.showNotification(it, "A new notification created", R.drawable.baseline_person_add_24) }
                    }
            }
        })
    }
    override fun onDestroyView() {
        super.onDestroyView()
        subscription?.dispose()
    }

}
