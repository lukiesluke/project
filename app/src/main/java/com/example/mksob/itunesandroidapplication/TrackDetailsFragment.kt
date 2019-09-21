package com.example.mksob.itunesandroidapplication

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mksob.itunesandroidapplication.databinding.TrackDetailsBinding
import com.example.mksob.itunesandroidapplication.model.Track
import com.example.mksob.itunesandroidapplication.viewModel.TrackViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.track_details.*

class TrackDetailsFragment: Fragment() {


    lateinit var tView : View
    lateinit var trackViewModel: TrackViewModel
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
////        trackViewModel = ViewModelProviders.of(activity!!).get(TrackDViewModel::class.java)
//    }

    lateinit var retrofitBinding: TrackDetailsBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        retrofitBinding = DataBindingUtil.inflate(inflater,R.layout.track_details,container,false)
        tView = retrofitBinding.getRoot()

        return tView
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        trackViewModel = ViewModelProviders.of(activity!!).get(TrackViewModel::class.java)

        var trackDetails = Track()

//
//        trackViewModel.setTrackName(arguments!!.getString(trackName))


//        trackViewModel.setTrack(arguments!!.getString(trackName))
//
//        binding!!.projectViewModel = viewModel
//        binding!!.isLoading = true

        Log.e("CLICK FRAGMENT CREATED " , arguments!!.getInt(post.toString()).toString() + " " + trackViewModel.toString())
        trackViewModel.getAllPosts().observe(this,object:Observer<List<Track>>{
            override fun onChanged(t: List<Track>?) {
                trackName.setText(t!![arguments!!.getInt(post.toString())].getTrackName())
                Picasso.get().load(t!![arguments!!.getInt(post.toString())].getImageUrl()).into(imageURL)
                trackDescription.setText(t!![arguments!!.getInt(post.toString())].getTrackDescription())
                trackGenre.setText(t!![arguments!!.getInt(post.toString())].getTrackGenre())
                trackPrice.setText(resources.getString(R.string.currency, t!![arguments!!.getInt(post.toString())].getCollectionPrice().toString()))
            }
        })
    }

    companion object {
        private var post = 0
        /** Creates track fragment for specific track index  */
//        fun forProject(track: String): TrackDetailsFragment {
        fun forProject(p0s: Int) : TrackDetailsFragment {
            val fragment = TrackDetailsFragment()
            val args = Bundle()

            args.putInt(post.toString(), p0s)
            fragment.arguments = args
            return fragment
        }
    }
}
