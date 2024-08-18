package com.dv.countries_dwij.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dv.countries_dwij.R
import com.dv.countries_dwij.models.Countries
import com.squareup.picasso.Picasso

class CountriesAdapter(
    private val countriesList:List<Countries>,
    private val favClickHandler: (Int) -> Unit
    ): RecyclerView.Adapter<CountriesAdapter.CountriesViewHolder>() {

    inner class CountriesViewHolder(itemView: View) : RecyclerView.ViewHolder (itemView) {
        init {
            val btnFavourite = itemView.findViewById<ImageButton>(R.id.btnFavourite)
            btnFavourite.setOnClickListener{
                favClickHandler(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountriesViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.countries_row_layout, parent, false)
        return CountriesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return countriesList.size
    }

    override fun onBindViewHolder(holder: CountriesViewHolder, position: Int) {
        val currCountry:Countries = countriesList[position]

        val tvTitle = holder.itemView.findViewById<TextView>(R.id.tvCountryName)
        tvTitle.text = currCountry.name.common

        val flagImg = holder.itemView.findViewById<ImageView>(R.id.imgFlag)
        Picasso.get().load(currCountry.flags.png).into(flagImg)
    }
}