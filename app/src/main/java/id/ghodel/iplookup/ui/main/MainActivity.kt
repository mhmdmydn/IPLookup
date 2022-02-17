package id.ghodel.iplookup.ui.main

import android.Manifest
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import id.ghodel.iplookup.R
import id.ghodel.iplookup.databinding.ActivityMainBinding
import id.ghodel.iplookup.utils.hideKeyboard
import pub.devrel.easypermissions.EasyPermissions

const val REQUEST_CODE_LOCATION = 123

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    var latitude: Double? = null
    var longitude: Double? = null
    var city: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnSearch.setOnClickListener {
            binding.apply {
                if(!txtSearchPhotos.text.isNullOrEmpty()){
                    it.hideKeyboard()
                    mMap.clear()
                    val ipQuery = binding.txtSearchPhotos.text.toString()
                    viewModel.searchIp(ipQuery)
                }else{
                    Toast.makeText(applicationContext, getString(R.string.field_cannot_be_empty), Toast.LENGTH_SHORT).show()
                }
            }


        }
        setupGoogleMaps()
    }

    private fun setupGoogleMaps() {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        enablePermission(googleMap)
        viewModel.ipResponse.observe(this, {ip ->

            latitude = ip.latitude
            longitude = ip.longitude
            city = ip.city

            mMap = googleMap
            val position = LatLng(latitude!!, longitude!!)
            mMap.addMarker(MarkerOptions().position(position).title("Marker in $city"))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(position))
            mMap.animateCamera(CameraUpdateFactory.zoomTo(12.0f))
            mMap.uiSettings.isZoomControlsEnabled = true

            binding.txtSearchPhotos.hint = ip.ip

            binding.apply {
                tvIp.text = ip.ip
                tvVersion.text = ip.version
                tvCity.text = ip.city
                tvRegion.text = ip.region
                tvCountry.text = ip.country_name
                tvLatitude.text = ip.latitude.toString()
                tvLongitude.text = ip.longitude.toString()
                tvAsnumber.text = ip.asn
                tvOrganization.text = ip.org
            }
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    @SuppressLint("MissingPermission")
    private fun enablePermission(googleMap: GoogleMap){

        if(hasLocationPermission()){
            googleMap.isMyLocationEnabled = true
        }else {
            EasyPermissions.requestPermissions(this, getString(R.string.location),
                REQUEST_CODE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
            )
        }
    }

    private fun hasLocationPermission(): Boolean {
        return EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION)
    }
}