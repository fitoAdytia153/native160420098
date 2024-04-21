package com.ubaya.uts_anmp_160420098.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubaya.uts_anmp_160420098.databinding.FragmentHeroListBinding
import com.ubaya.uts_anmp_160420098.viewmodel.ListViewModel

class HeroListFragment : Fragment() {
    private lateinit var heroesViewModel: ListViewModel
    private lateinit var heroesListAdapter: HeroListAdapter
    private lateinit var binding: FragmentHeroListBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHeroListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        heroesViewModel = ViewModelProvider(this)[ListViewModel::class.java]

        heroesListAdapter = HeroListAdapter(listOf())
        binding.recView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = heroesListAdapter
        }
        binding.refreshLayout.setOnRefreshListener {
            heroesViewModel.refresh()
            binding.refreshLayout.isRefreshing = false
        }
        heroesViewModel.refresh()
        observeViewModel()
    }

    private fun observeViewModel() {
        heroesViewModel.heroLD.observe(viewLifecycleOwner, Observer { heroes ->
            heroes?.let {
                binding.recView.visibility = View.VISIBLE
                heroesListAdapter.updateHeroList(it)
            }
        })

        heroesViewModel.heroLoadErrorLD.observe(viewLifecycleOwner, Observer { isError ->
            binding.txtError.visibility = if (!isError.isNullOrBlank()) View.VISIBLE else View.GONE
            binding.txtError.text = isError ?: "Terdapat error"
        })

        heroesViewModel.loadingLD.observe(viewLifecycleOwner, Observer { isLoading ->
            if (isLoading) {
                binding.progressLoad.visibility = View.VISIBLE
                binding.recView.visibility = View.GONE
                binding.txtError.visibility = View.GONE
            } else {
                binding.progressLoad.visibility = View.GONE
                binding.recView.visibility = View.VISIBLE
            }
        })
    }
}