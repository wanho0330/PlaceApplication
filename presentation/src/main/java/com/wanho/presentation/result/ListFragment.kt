package com.wanho.presentation.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.wanho.presentation.dialog.ConfirmDialog
import com.wanho.presentation.R
import com.wanho.presentation.databinding.FragmentListBinding
import com.wanho.presentation.dialog.LoadingDialog
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

// 근처 식당 검색 결과를 리스트로 표시하는 프래그먼트
class ListFragment : Fragment() {

    private lateinit var binding : FragmentListBinding
    private lateinit var resultListAdapter: ResultListAdapter

    private val placeViewModel: PlaceViewModel by sharedViewModel()

    private lateinit var loadingDialog : LoadingDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.view = this
        binding.viewModel = placeViewModel
        binding.lifecycleOwner = this

        settingRecyclerView()
        settingDialog()

        placeViewModel.getPlaceRepository()
    }


    private fun settingDialog() {
        loadingDialog = LoadingDialog(requireContext())
        loadingDialog.show()

        placeViewModel.placeList.observe(viewLifecycleOwner, {
            loadingDialog.dismiss()
        })

    }

    private fun settingRecyclerView() {
        resultListAdapter = ResultListAdapter(placeViewModel)
        binding.recyclerView.adapter = resultListAdapter
    }


}