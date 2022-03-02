package com.wanho.presentation.search

import android.annotation.SuppressLint
import android.graphics.Color
import android.location.Location
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.Circle
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.wanho.presentation.R
import com.wanho.presentation.databinding.FragmentMapsBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


// 내 위치 및 검색 범위를 확인할 수 있는 프레그먼트
class MapsFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener {

    private lateinit var binding: FragmentMapsBinding
    private val locationViewModel: LocationViewModel by sharedViewModel()

    private lateinit var myLocation: Location

    private lateinit var googleMap: GoogleMap
    private lateinit var mapView: MapView

    private lateinit var circleOptions: CircleOptions
    private lateinit var circle : Circle

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_maps, container, false)
        mapView = binding.map
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner

    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        this.googleMap.uiSettings.isZoomControlsEnabled = true

        settingCircle()
        settingMyLocation()
        settingRadius()

    }

    // 검색 원 초기화 함수
    private fun settingCircle() {
        circleOptions = CircleOptions()
            .strokeWidth(10f)
            .strokeColor(Color.MAGENTA)
            .center(LatLng(0.0, 0.0))
            .radius(0.0)
        circle = googleMap.addCircle(circleOptions)

    }

    // 내 위치에 따른 카메라 이동 함수
    @SuppressLint("MissingPermission")
    private fun settingMyLocation() {

        var cameraMoveUpdateFlag = true

        locationViewModel.location.observe(viewLifecycleOwner, { location ->
            myLocation = location
            if (cameraMoveUpdateFlag) {
                googleMap.isMyLocationEnabled = true
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(myLocation.latitude, myLocation.longitude), 15.0f))

                circleOptions.center(LatLng(myLocation.latitude, myLocation.longitude))
                circleOptions.radius(locationViewModel.INIT_RADIUS.toDouble())
                circle = googleMap.addCircle(circleOptions)

                cameraMoveUpdateFlag = false
            }
        })
    }


    // 검색 범위 slide에 따라 원의 표시를 바꿔주는 함수
    private fun settingRadius() {
        locationViewModel.radius.observe(viewLifecycleOwner, { radius ->
            circle.remove()

            circleOptions.radius(radius.toDouble())
            circle = googleMap.addCircle(circleOptions)
        })
    }



    // 내 위치 버튼 클릭할 경우 이동하는 함수
    override fun onMyLocationButtonClick(): Boolean {
        settingMyLocation()
        return false
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }


}