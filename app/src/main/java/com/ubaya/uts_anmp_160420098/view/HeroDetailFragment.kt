package com.ubaya.uts_anmp_160420098.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import com.ubaya.uts_anmp_160420098.databinding.FragmentHeroDetailBinding
import com.ubaya.uts_anmp_160420098.model.Hero
import com.ubaya.uts_anmp_160420098.viewmodel.DetailViewModel
import com.ubaya.uts_anmp_160420098.viewmodel.ViewModel

class HeroDetailFragment : Fragment() {

    private lateinit var detailViewModel: DetailViewModel
    private lateinit var binding: FragmentHeroDetailBinding
    private lateinit var viewModel: ViewModel
    private var currentSkillIndex = 0
    private var previousPageIndex = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentHeroDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        viewModel = ViewModelProvider(requireActivity())[ViewModel::class.java]

        val args: HeroDetailFragmentArgs by navArgs()
        val heroId = args.heroId
        viewModel.selectedHeroId = heroId

        detailViewModel.fetch(heroId)

        detailViewModel.heroLD.observe(viewLifecycleOwner) { hero ->
            hero?.let {
                viewModel.lastVisited(it)
                updatePage(it)
            }
        }

        viewModel.visited.observe(viewLifecycleOwner) { hero ->
            hero?.let {
                updatePage(it)
            }
        }
        setupSkillNavigation()
    }

    private fun setupSkillNavigation() {
        binding.btnNext.setOnClickListener {
            navigateSkills(true)
        }
        binding.btnPrevious.setOnClickListener {
            navigateSkills(false)
        }
    }

    private fun navigateSkills(isNext: Boolean) {
        viewModel.visited .value?.let { hero ->
            val skillSize = hero.skillName.size

            // indeks skill berikutnya dihitung dengan menggunakan operasi modulo (%) agar jika melebihi jumlah skill, kembali ke indeks awal.
            currentSkillIndex = if (isNext) {
                (currentSkillIndex + 1) % skillSize
            } else { // Jika hasil pengurangan indeks skill sebelumnya kurang dari 0, maka indeks akan dikembalikan ke indeks terakhir dalam daftar skill.
                if (currentSkillIndex - 1 < 0) skillSize - 1 else currentSkillIndex - 1
            }
            updatePage(hero)
        }
    }

    private fun updatePage(hero: Hero) {
        binding.apply {
            txtNamaHero.text = hero.name
            txtAuthor.text = hero.author
            displaySkillAndDescription(hero)
            Picasso.get().load(hero.photoUrl).into(imgDetailHero)
        }
    }

    private fun displaySkillAndDescription(hero: Hero) {
        binding.txtNamaSkill.text = hero.skillName[currentSkillIndex]
        binding.txtDeskripsiSkill.text = hero.skillDesc[currentSkillIndex]
    }
}