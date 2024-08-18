package com.dv.countries_dwij

import com.dv.countries_dwij.databinding.ActivityFavouriteCountriesBinding
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dv.countries_dwij.adapters.FavouritesAdapter

import com.dv.countries_dwij.models.FavCountry
import com.dv.countries_dwij.repositories.CountriesRepository


class FavouriteCountries : AppCompatActivity() {
    private lateinit var binding: ActivityFavouriteCountriesBinding
    private lateinit var favouritesAdapter: FavouritesAdapter
    private lateinit var countriesRepository: CountriesRepository
    private lateinit var countriesArrayList: ArrayList<FavCountry>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavouriteCountriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(this.binding.menuToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)

        this.countriesRepository = CountriesRepository(applicationContext)

        countriesArrayList = ArrayList()
        countriesRepository.retrieveFavouriteCountries()

        countriesRepository.favouriteCountries.observe(this, androidx.lifecycle.Observer { countriesList ->
            if(countriesList != null){
                //clear the existing list to avoid duplicate records
                countriesArrayList.clear()
                countriesArrayList.addAll(countriesList)
                Log.d("debugchecker", "oncheck:$countriesArrayList ")
                favouritesAdapter.notifyDataSetChanged()

            }else{
                Log.d("testtag", "onResume:$countriesList ")}
        })
        favouritesAdapter = FavouritesAdapter(countriesArrayList, {pos -> rowClicked(pos)})

        binding.rvItems.adapter = favouritesAdapter
        binding.rvItems.layoutManager = LinearLayoutManager(this)
        binding.rvItems.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )


//        this.countriesRepository = CountriesRepository(applicationContext)

        countriesRepository.retrieveFavouriteCountries()

        countriesRepository.favouriteCountries.observe(this, androidx.lifecycle.Observer { countriesList ->
            if(countriesList != null) {
                countriesArrayList.clear()
                countriesArrayList.addAll(countriesList)
                favouritesAdapter.notifyDataSetChanged()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_home -> {
                startActivity(Intent(this, MainActivity::class.java))
                true
            }
            R.id.menu_favourites -> {
                startActivity(Intent(this, FavouriteCountries::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()

    }

    fun rowClicked(pos:Int){
        Log.d("rowClicked", "rowClicked: *******************************************")
    }

}