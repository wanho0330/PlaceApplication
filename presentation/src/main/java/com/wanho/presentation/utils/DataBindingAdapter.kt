package com.wanho.presentation.utils

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.wanho.domain.model.PlaceModel
import com.wanho.presentation.history.HistoryListAdapter
import com.wanho.presentation.result.ResultListAdapter

object DataBindingAdapter {
    @SuppressLint("NotifyDataSetChanged")
    @JvmStatic
    @BindingAdapter("dba:place_list")
    fun setBindPlaceList(recyclerView: RecyclerView, placeList: LiveData<List<PlaceModel>>) {
        recyclerView.adapter?.run {
            if (this is ResultListAdapter) {
                placeList.value?.let { this.placeList = it } ?: { this.placeList = listOf() }()
                this.notifyDataSetChanged()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    @JvmStatic
    @BindingAdapter("dba:distance")
    fun setBindDistance(textView: TextView, distance : Int) {
        textView.text = "${distance}M"
    }


    @SuppressLint("NotifyDataSetChanged")
    @JvmStatic
    @BindingAdapter("dba:history_list")
    fun setBindHistoryList(recyclerView: RecyclerView, historyList: LiveData<List<PlaceModel>>) {
        recyclerView.adapter?.run {
            if (this is HistoryListAdapter) {
                historyList.value?.let { this.placeList = it } ?: { this.placeList = listOf() }()
                this.notifyDataSetChanged()
            }
        }
    }
}