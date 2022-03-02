package com.wanho.presentation.result

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import com.wanho.presentation.R
import com.wanho.presentation.databinding.FragmentResultBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

// 검색 결과를 지도형식으로 표현하는 프레그먼트
class ResultFragment : Fragment(), OnMapReadyCallback {

    private lateinit var binding: FragmentResultBinding
    private val placeViewModel: PlaceViewModel by sharedViewModel()

    private lateinit var googleMap: GoogleMap
    private lateinit var mapView: MapView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_result, container, false)
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

        settingCamera()
        settingCircle()
        settingPlaceList()
    }


    // 식당 리스트를 마킹 해주는 함수
    private fun settingPlaceList() {

        placeViewModel.placeList.observe(viewLifecycleOwner, { placeList ->

            placeList.forEachIndexed { index, placeModel ->

                var marker : Marker? = null

                marker = googleMap.addMarker(MarkerOptions()
                    .position(LatLng(placeModel.latitude, placeModel.longitude)))

                marker?.tag = index
            }

            googleMap.setOnMarkerClickListener {
                placeViewModel.onItemClick(placeList[it.tag as Int])
                false
            }

        })
    }


    // 지도에 원을 그리는 함수
    private fun settingCircle() {
        val circleOption = CircleOptions()
            .center(LatLng(placeViewModel.latitude, placeViewModel.longitude))
            .radius(placeViewModel.radius.toDouble())
            .strokeWidth(10f)
            .strokeColor(Color.MAGENTA)

        googleMap.addCircle(circleOption)
    }

    // 지도 카메라 이동 및 범위를 지정해주는 함수
    private fun settingCamera() {
        googleMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    placeViewModel.latitude,
                    placeViewModel.longitude
                ), 15.0f
            )
        )

        googleMap.setMinZoomPreference(15.0f)

        val adelaideBounds = placeViewModel.getCameraBounds()
        googleMap.setLatLngBoundsForCameraTarget(adelaideBounds)
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