package com.wanho.presentation.result

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.wanho.domain.model.PlaceModel
import com.wanho.presentation.BR
import com.wanho.presentation.R
import com.wanho.presentation.databinding.ItemPlaceBinding

// 검색 결과를 리스트로 표시해주기 위한 리스트어답터
class ResultListAdapter(private val placeViewModel: PlaceViewModel) :
    RecyclerView.Adapter<ResultListAdapter.ViewHolder>() {

    var placeList: List<PlaceModel> = mutableListOf()

    inner class ViewHolder(private var binding: ItemPlaceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(placeModel: PlaceModel) {
            binding.setVariable(BR.model, placeModel)

            binding.root.setOnClickListener {
                placeViewModel.onItemClick(placeModel)
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemPlaceBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(placeList[position])
        holder.itemView.animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.anim_recycler_view)

    }

    override fun getItemCount(): Int {
        return placeList.size
    }
}