package com.example.flavors_prototype

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flavors_prototype.databinding.ActivityCountryBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class CountryActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityCountryBinding

    private var markerChina: Marker? = null
    private var markerIndia: Marker? = null
    private var markerUsa: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCountryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Create Map Location for China, India and USA
        val china = LatLng(35.8617, 104.1954)
        val india = LatLng(25.5937, 78.9629)
        val usa = LatLng(37.0902, 264.2871)

        //Create Saved Markers on the Map & add data object to the Markers
        //Best to hardcode these values, it's redundant to call them from database
        markerChina = mMap.addMarker(MarkerOptions().position(china).title("China").icon(
            BitmapDescriptorFactory.fromResource(R.drawable.china)))
        markerChina?.tag = 0
        markerIndia = mMap.addMarker(MarkerOptions().position(india).title("India").icon(BitmapDescriptorFactory.fromResource(R.drawable.india)))
        markerIndia?.tag = 1
        markerUsa = mMap.addMarker(MarkerOptions().position(usa).title("USA").icon(BitmapDescriptorFactory.fromResource(R.drawable.usa)))
        markerUsa?.tag = 2

        //Move Camera to USA marker
        mMap.moveCamera(CameraUpdateFactory.newLatLng(usa))
        //Set a listener for marker clicks
        mMap.setOnMarkerClickListener(this)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        //Retrieve data of the marker tag
        val countrySelected = marker.tag as? Int
        val countryName = marker.title
        //Check if data was set before attempting to display the data
        countrySelected?.let{
            //Tag allows modification of Displayed Dishes depending on Country
            marker.tag = countrySelected
            //Toast.makeText(this, "${marker.title} has been clicked: Country = $countrySelected", Toast.LENGTH_SHORT).show()   //Debug tool
            Toast.makeText(this, countryName, Toast.LENGTH_SHORT).show()
            val countryDishes = Intent(this, DataActivity::class.java).putExtra("country_name", countryName)
            startActivity(countryDishes)
        }
        return false
    }
    //will create manin menu bar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        val inflater : MenuInflater = menuInflater
        with(inflater) {
            inflate(R.menu.main_menu,menu)
        }
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)

        if(item.itemId == R.id.cook_book)
        {
            startActivity(Intent(this, cookBookActivity::class.java))
            //return true
        }
        if(item.itemId == R.id.country_selection)
        {
            startActivity(Intent(this, CountryActivity::class.java))
            //return true
        }


        if(item.itemId == R.id.shoppingList_selection)
        {
            startActivity(Intent(this, ShoppingListActivity::class.java))
            //return true
        }


        return super.onOptionsItemSelected(item)
    }


}