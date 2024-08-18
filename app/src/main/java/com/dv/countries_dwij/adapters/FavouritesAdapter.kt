package com.dv.countries_dwij.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dv.countries_dwij.R
import com.dv.countries_dwij.models.Countries
import com.dv.countries_dwij.models.FavCountry

class FavouritesAdapter(private val countriesList:List<FavCountry>,
                        private val rowClickHandler: (Int) -> Unit
): RecyclerView.Adapter<FavouritesAdapter.FavouriteViewHolder>() {
    inner class FavouriteViewHolder(itemView: View) : RecyclerView.ViewHolder (itemView) {
        init {
            itemView.setOnClickListener {
                rowClickHandler(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.countries_row_layout, parent, false)
        return FavouriteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return countriesList.size
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        val tv = holder.itemView.findViewById<TextView>(R.id.tvCountryName)
        val countryDetails = countriesList[position]
        tv.text = countryDetails.name
    }
}