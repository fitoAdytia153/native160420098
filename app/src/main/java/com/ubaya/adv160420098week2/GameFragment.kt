package com.ubaya.adv160420098week2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.ubaya.adv160420098week2.databinding.FragmentGameBinding
import kotlin.random.Random

class GameFragment : Fragment() {
    private lateinit var binding: FragmentGameBinding
    private lateinit var action: NavDirections
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val random = Random

        val firstRandom = random.nextInt(1,100)
        val secondRandom = random.nextInt(1,100)

        binding.txtAngka1.text = firstRandom.toString()
        binding.txtAngka2.text = secondRandom.toString()

        val firstNum= binding.txtAngka1.text.toString().toInt()
        val secondNum = binding.txtAngka2.text.toString().toInt()

        val total = firstNum + secondNum

        if (arguments != null) {
            val playerName = GameFragmentArgs.fromBundle(requireArguments()).playerName
            binding.txtTurn.text = "$playerName's Turn"
        }

        binding.btnSubmit.setOnClickListener {
            val userAnswer = binding.txtAnswer.text.toString().toInt()
            var score = GameFragmentArgs.fromBundle(requireArguments()).score
            score ++
            if(userAnswer == total) {
                val playerName = GameFragmentArgs.fromBundle(requireArguments()).playerName
                action = GameFragmentDirections.actionRepeatFragment(playerName, score++)
            } else {
                score --
                action = GameFragmentDirections.actionEndFragment(score)
            }
            Navigation.findNavController(it).navigate(action)
        }
    }
}