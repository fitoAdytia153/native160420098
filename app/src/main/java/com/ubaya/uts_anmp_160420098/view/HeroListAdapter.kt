package com.ubaya.uts_anmp_160420098.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.ubaya.uts_anmp_160420098.databinding.HeroListItemsBinding
import com.ubaya.uts_anmp_160420098.model.Hero

class HeroListAdapter(private var heroes: List<Hero>) : RecyclerView.Adapter<HeroListAdapter.HeroViewHolder>() {
    class HeroViewHolder(val binding: HeroListItemsBinding) : RecyclerView.ViewHolder(binding.root)

    fun updateHeroList(newHero: List<Hero>) {
        heroes = newHero
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroViewHolder {
        val binding = HeroListItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HeroViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HeroViewHolder, position: Int) {
        val hero = heroes[position]

        with(holder.binding) {
            Picasso.get().load(hero.photoUrl).into(imgHero)
            txtNamaHero.text = hero.name
            txtAuthor.text = hero.author
            txtDeskripsiHero.text = hero.description

            btnDetail.setOnClickListener {
                val action = HeroListFragmentDirections.actionHeroDetail(hero.id)
                it.findNavController().navigate(action)
            }
        }
    }

    override fun getItemCount(): Int = heroes.size
}