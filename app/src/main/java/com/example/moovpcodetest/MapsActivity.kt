package com.example.moovpcodetest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.doOnPreDraw
import androidx.core.view.updatePadding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.moovpcodetest.adapter.ListItemClickListener
import com.example.moovpcodetest.adapter.PeopleAdapter
import com.example.moovpcodetest.databinding.ActivityMapsBinding
import com.example.moovpcodetest.model.People
import com.example.moovpcodetest.viewmodel.MainViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, ListItemClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private val mainViewModel: MainViewModel by viewModel()

    private val adapter: PeopleAdapter by lazy {
        PeopleAdapter(this)
    }

    private var scrollToTopOnNextUpdate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        handleInsets()
        setupListView()
        startObserve()
    }

    private fun setupListView() {
        binding.rvPeopleList.adapter = adapter
    }

    private fun startObserve() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                mainViewModel.peopleListFlow.collectLatest {
                    adapter.submitList(it) {
                        if (scrollToTopOnNextUpdate) {
                            binding.rvPeopleList.doOnPreDraw {
                                scrollToTopOnNextUpdate = false
                                binding.rvPeopleList.smoothScrollToPosition(0)
                            }
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                mainViewModel.peopleDaoFlow.collectLatest {
                    addMarkers(it)
                }
            }
        }
    }

    private fun addMarkers(list: List<People>) {
        list.forEach { people ->
            val position = LatLng(people.latitude, people.longitude)
            mMap.addMarker(
                MarkerOptions().position(position)
                    .title(people.fullName)
            )
        }
    }

    private fun handleInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.rvPeopleList) { listView, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())

            listView.updatePadding(
                bottom = insets.bottom
            )
            binding.topSafeArea.updatePadding(
                top = insets.top
            )
            binding.bottom.updatePadding(
                bottom = insets.bottom
            )

            windowInsets
        }
    }

    override fun onItemClick(item: People) {
        scrollToTopOnNextUpdate = true
        mainViewModel.updateSelectedItem(item)
        moveToMarkerPosition(item)
    }

    private fun moveToMarkerPosition(item: People) {
        val startPoint = LatLng(item.latitude, item.longitude)
        mMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(startPoint, 10f)
        )
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMarkerClickListener {
            scrollToTopOnNextUpdate = true
            mainViewModel.onMarkerClick(
                it.title ?: return@setOnMarkerClickListener false
            )
            false
        }

        // notify the view model when map is ready
        mainViewModel.onMapReady()

        val startPoint = LatLng(22.294333, 114.171959)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startPoint, 10f))
    }
}