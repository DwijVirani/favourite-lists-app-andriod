package com.dv.countries_dwij.api

import com.dv.countries_dwij.models.Countries
import retrofit2.http.GET

interface CountriesInterface {
    @GET("/v3.1/independent?status=true")
    suspend fun getAllCountries():List<Countries>
}