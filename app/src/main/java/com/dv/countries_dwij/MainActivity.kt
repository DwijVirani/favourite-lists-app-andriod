package com.dv.countries_dwij

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dv.countries_dwij.adapters.CountriesAdapter
import com.dv.countries_dwij.api.CountriesInterface
import com.dv.countries_dwij.api.RetrofitInstance
import com.dv.countries_dwij.databinding.ActivityMainBinding
import com.dv.countries_dwij.models.Countries
import com.dv.countries_dwij.repositories.CountriesRepository
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import java.util.UUID

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: CountriesAdapter
    private var datasource:MutableList<Countries> = mutableListOf()
    private lateinit var countriesRepository: CountriesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(this.binding.menuToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)

        adapter = CountriesAdapter(datasource, {pos -> favBtnClickHandler(pos)})

        binding.rvItems.adapter = adapter
        binding.rvItems.layoutManager = LinearLayoutManager(this)
        binding.rvItems.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )

        this.countriesRepository = CountriesRepository(applicationContext)

        var api: CountriesInterface = RetrofitInstance.retrofitService
        lifecycleScope.launch {
            val countriesList:List<Countries> = api.getAllCountries()
            countriesList.sortedWith(compareBy{ it.name.common })
            datasource.clear()
            datasource.addAll(countriesList)
            adapter.notifyDataSetChanged()
        }

    }

    fun favBtnClickHandler(position:Int) {
        val currCountry = datasource[position]
        val countryToFavourite = Countries(name = currCountry.name, flags = currCountry.flags, isFavourite = true)
        this.countriesRepository.addFavouriteCountry(countryToFavourite)
        Snackbar.make(binding.root, "Country ${currCountry.name.common} added to favourites", Snackbar.LENGTH_SHORT).show()
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
}