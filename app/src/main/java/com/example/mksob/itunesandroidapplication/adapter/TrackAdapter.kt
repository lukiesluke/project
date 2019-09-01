package com.example.mksob.itunesandroidapplication.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.mksob.itunesandroidapplication.R
import com.example.mksob.itunesandroidapplication.model.Track
import com.example.mksob.itunesandroidapplication.databinding.TrackAdapterBinding

public class TrackAdapter(): RecyclerView.Adapter<TrackAdapter.TrackHolder>() {
    var trackList: List<Track>? = null
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): TrackHolder {
        val postItemBinding: TrackAdapterBinding = DataBindingUtil.inflate(LayoutInflater.from(parent?.context), R.layout.track_list_view, parent, false)
        //  val userPostHolder:UserPostHolder = UserPostHolder(postItemBinding)
        return TrackHolder(postItemBinding)
    }

    override fun onBindViewHolder(holder: TrackHolder, pos: Int) {
        holder!!.postItemBinding.model = trackList!!.get(pos)
    }

    override fun getItemCount(): Int {
        if (trackList != null) {
            return trackList!!.size
        } else
            return 0;
    }

    public fun setListItems(trackList: List<Track>?){
        this.trackList = trackList;
        notifyDataSetChanged()
    }


    inner class TrackHolder(var postItemBinding: TrackAdapterBinding) : RecyclerView.ViewHolder(postItemBinding.getRoot())
}