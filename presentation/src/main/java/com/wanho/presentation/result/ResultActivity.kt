package com.wanho.presentation.result

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.wanho.presentation.R
import com.wanho.presentation.databinding.ActivityResultBinding
import com.wanho.presentation.dialog.ConfirmDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

// 검색 결과를 표시해주는 액티비티
class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private val placeViewModel: PlaceViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_result)

        binding.view = this
        binding.lifecycleOwner = this

        // 내 위치를 SearchActivity로부터 받아옴
        val latitude = intent.getDoubleExtra("latitude", 0.0)
        val longitude = intent.getDoubleExtra("longitude", 0.0)
        val radius = intent.getIntExtra("radius", 0)

        // 내 위치를 ViewModel에 저장
        placeViewModel.setSearchOptions(latitude, longitude, radius)

        settingBottomAppBar()
        settingNavigationView()
        settingSingleLiveData()


    }

    private fun settingSingleLiveData() {
        placeViewModel.singleLiveEvent.observe(this, { place ->
            ConfirmDialog(place).show(supportFragmentManager, "confirmDialog")
        })
    }

    private fun settingNavigationView() {

        settingFragment(ListFragment())
        binding.bottomNavigationView.setOnItemSelectedListener {
            settingFragment(
                when (it.itemId) {
                    R.id.map_item -> ResultFragment()
                    R.id.list_item -> ListFragment()
                    else -> ListFragment()
                }
            )
            true
        }
    }

    fun settingFabButton() {
        placeViewModel.onRandomButtonClick()

    }

    private fun settingBottomAppBar() {
        binding.bottomNavigationView.background = null

    }


    private fun settingFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.result_frame_layout, fragment)
            .commit()

    }


}