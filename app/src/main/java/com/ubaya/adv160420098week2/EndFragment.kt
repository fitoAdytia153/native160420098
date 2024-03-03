package com.ubaya.adv160420098week2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.ubaya.adv160420098week2.databinding.FragmentEndBinding
import com.ubaya.adv160420098week2.databinding.FragmentGameBinding
import kotlin.random.Random

class EndFragment : Fragment() {
    private lateinit var binding: FragmentEndBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEndBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            val hasil = EndFragmentArgs.fromBundle(requireArguments()).score
            binding.txtScore.text = "Your score is $hasil"
        }

        binding.btnBack.setOnClickListener {
            val action = EndFragmentDirections.actionMainFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }
}