package com.dv.countries_dwij.repositories

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.dv.countries_dwij.models.Countries
import com.dv.countries_dwij.models.FavCountry
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.EventListener

class CountriesRepository(val context: Context) {
    private val db = Firebase.firestore
    private val TAG = this.toString();

    private val COLLECTION_USER = "Users"
    private val COLLECTION_COUNTRIES = "Countries"
    private val FIELD_COUNTRY_NAME = "name"
    private val FIELD_FLAG = "flag"
    private val FIELD_IS_FAVOURITE = "isFavourite"

    private val userEmail = "dwijvirani23@gmail.com"

    var favouriteCountries : MutableLiveData<List<FavCountry>> = MutableLiveData<List<FavCountry>>()

    fun addFavouriteCountry(country: Countries) {
        try {
            val data: MutableMap<String, Any> = HashMap()

            data[FIELD_COUNTRY_NAME] = country.name.common
//            data[FIELD_FLAG] = country.flags.png
//            data[FIELD_IS_FAVOURITE] = true

            db.collection(COLLECTION_USER)
                .document(userEmail)
                .collection(COLLECTION_COUNTRIES)
                .document(country.name.common)
                .set(data)
                .addOnSuccessListener { docRef ->
                    Log.d(TAG, "addFavouriteCountry: New country document created with ID: $docRef")
                }
                .addOnFailureListener{ex ->
                    Log.d(TAG, "addFavouriteCountry: Unable to create country document. Error: $ex")
                }
        } catch (ex : java.lang.Exception) {
            Log.e(TAG, "addUserToDB: Unable to create user : $ex", )
        }
    }

    fun retrieveFavouriteCountries() {
        try {
            db.collection(COLLECTION_USER)
                .document(userEmail)
                .collection(COLLECTION_COUNTRIES)
                .addSnapshotListener(EventListener{ result, error ->
                    if (error != null){
                        Log.e(TAG,
                            "retrieveAllCountries: Listening to Countries collection failed due to error : $error", )
                        return@EventListener
                    }

                    if (result != null){
                        Log.d(TAG, "retrieveAllCountries: Number of documents retrieved : ${result.size()}")

                        val tempList : MutableList<FavCountry> = ArrayList<FavCountry>()

                        for (docChanges in result.documentChanges){

                            val currentDocument : FavCountry = docChanges.document.toObject(FavCountry::class.java)
                            Log.d(TAG, "retrieveAllCountries: currentDocument : $currentDocument")

                            when(docChanges.type){
                                DocumentChange.Type.ADDED -> {
                                    //do necessary changes to your local list of objects
                                    tempList.add(currentDocument)
                                }
                                DocumentChange.Type.MODIFIED -> {

                                }
                                DocumentChange.Type.REMOVED -> {

                                }
                            }
                        }//for
                        Log.d(TAG, "retrieveAllCountries: tempList : $tempList")
                        //replace the value in allExpenses
                        favouriteCountries.postValue(tempList)

                    }else{
                        Log.d(TAG, "retrieveAllCountries: No data in the result after retrieving")
                    }
                })
        } catch (ex : java.lang.Exception) {
            Log.e(TAG, "retrieveAllCountries: Unable to retrieve all countries : $ex", )
        }
    }

    fun removeFavourites(countryToRemove: Countries) {
        try{
            db.collection(COLLECTION_USER)
                .document(userEmail)
                .collection(COLLECTION_COUNTRIES)
                .document(countryToRemove.name.common)
                .delete()
                .addOnSuccessListener { docRef ->
                    Log.d(TAG, "updateFavourites: Document updated successfully : $docRef")
                }
                .addOnFailureListener { ex ->
                    Log.e(TAG, "updateFavourites: Failed to update document : $ex", )
                }
        }
        catch (ex : Exception){
            Log.e(TAG, "updateFavourites: Unable to update expense due to exception : $ex", )
        }
    }
}