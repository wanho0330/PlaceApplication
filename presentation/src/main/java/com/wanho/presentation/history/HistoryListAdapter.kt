package com.wanho.presentation.history

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.wanho.domain.model.PlaceModel
import com.wanho.presentation.BR
import com.wanho.presentation.R
import com.wanho.presentation.databinding.ItemHistoryBinding
import com.wanho.presentation.utils.AlertDialogHelper

// 다녀온 식당 내역의 리스트뷰 어답터
class HistoryListAdapter(private val historyViewModel: HistoryViewModel) :
    RecyclerView.Adapter<HistoryListAdapter.ViewHolder>() {

    var placeList: List<PlaceModel> = mutableListOf()

    // DataBinding을 이용하여 ViewHolder 작성
    inner class ViewHolder(private val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(placeModel: PlaceModel) {

            // setVariable : 구체적인 결합 클래스를 알지 못할 때 사용하는 함수 >> 추후 정리 필요
            binding.setVariable(BR.model, placeModel)

            // ViewHolder 클릭 시 viewModel 에 데이터를 전송하여 함수 실행
            binding.root.setOnClickListener {
                historyViewModel.onItemClick(placeModel)
            }

            // viewHolder 롱 클릭 시 Dialog 실행
            binding.root.setOnLongClickListener {
                AlertDialogHelper(binding.root.context)
                    .dialogCreate("히스토리 삭제", "해당 내역을 삭제하시겠습니까?") { historyViewModel.onLongItemClick(placeModel) }
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemHistoryBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(placeList[position])
        holder.itemView.animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.anim_recycler_view)
    }

    override fun getItemCount(): Int {
        return placeList.size
    }



}