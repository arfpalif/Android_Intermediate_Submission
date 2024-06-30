package com.dicoding.picodiploma.loginwithanimation.view.map

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.picodiploma.loginwithanimation.R
import com.dicoding.picodiploma.loginwithanimation.data.api.ApiConfig
import com.dicoding.picodiploma.loginwithanimation.data.response.GetStoryResponse
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityMapsBinding
import com.dicoding.picodiploma.loginwithanimation.view.ViewModelFactory
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private val viewModel by viewModels<MapsViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private var token: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupMapFragment()
        observeSession()
    }

    private fun setupMapFragment() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun observeSession() {
        viewModel.getSession().observe(this) { user ->
            token = user.token
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        setupMapUI()
        fetchStoriesAndDisplayMarkers()
    }

    private fun setupMapUI() {
        mMap.uiSettings.apply {
            isZoomControlsEnabled = true
            isIndoorLevelPickerEnabled = true
            isCompassEnabled = true
            isMapToolbarEnabled = true
        }
    }

    private fun fetchStoriesAndDisplayMarkers() {
        val client = ApiConfig.getApiService(token).getStoriesWithLocation()
        client.enqueue(object : Callback<GetStoryResponse> {
            override fun onResponse(call: Call<GetStoryResponse>, response: Response<GetStoryResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { responseBody ->
                        var lastLoc = LatLng(0.0, 0.0)
                        responseBody.listStory.forEach { story ->
                            story.lat?.let { lat ->
                                story.lon?.let { lon ->
                                    val latLng = LatLng(lat, lon)
                                    mMap.addMarker(
                                        MarkerOptions()
                                            .position(latLng)
                                            .title(story.name)
                                            .snippet(story.description)
                                    )
                                    lastLoc = latLng
                                }
                            }
                        }
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(lastLoc))
                    }
                } else {
                    showError(response.message())
                }
            }

            override fun onFailure(call: Call<GetStoryResponse>, t: Throwable) {
                showError(t.message)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_maps, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.normal_type -> {
                mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
                true
            }
            R.id.satellite_type -> {
                mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
                true
            }
            R.id.terrain_type -> {
                mMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
                true
            }
            R.id.hybrid_type -> {
                mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showError(message: String?) {
        Log.e("ERROR", "ERROR: $message")
        Toast.makeText(this@MapsActivity, "Error: $message", Toast.LENGTH_SHORT).show()
    }
}
