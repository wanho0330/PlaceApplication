package com.wanho.presentation.history

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.wanho.presentation.dialog.ConfirmDialog
import com.wanho.presentation.R
import com.wanho.presentation.databinding.ActivityHistoryBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


// 찾은 식당 내역을 표시해주는 액티비티
class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding
    private val historyViewModel: HistoryViewModel by viewModel()

    private lateinit var historyListAdapter: HistoryListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_history)

        binding.view = this
        binding.lifecycleOwner = this
        binding.viewModel = historyViewModel

        settingRecyclerView()

    }

    // 식당 내역 리스트 뷰 세팅
    private fun settingRecyclerView() {
        historyListAdapter = HistoryListAdapter(historyViewModel)
        binding.recyclerView.adapter = historyListAdapter

        historyViewModel.getHistoryAll()

        // 리스트 아이템 클릭시 SingleLiveEvent를 이용하여 다이얼로그 실행
        historyViewModel.singleLiveEvent.observe(this, { place ->
            ConfirmDialog(place).show(supportFragmentManager, "confirmDialog")

        })
    }
}