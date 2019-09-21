package com.example.mksob.itunesandroidapplication

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.mksob.itunesandroidapplication.adapter.TrackAdapter
import com.example.mksob.itunesandroidapplication.adapter.TrackCallListener
import com.example.mksob.itunesandroidapplication.databinding.RetrofitFragmentBindings
import com.example.mksob.itunesandroidapplication.db.DateBi
import com.example.mksob.itunesandroidapplication.model.DateBinding
import com.example.mksob.itunesandroidapplication.model.Track
import com.example.mksob.itunesandroidapplication.viewModel.TrackViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class TrackFragment : Fragment() {
    lateinit var tView: View
    lateinit var trackViewModel: TrackViewModel
    lateinit var trackCallListener: TrackCallListener
    val date = getCurrentDateTime()
    val dateFMT = SimpleDateFormat("yyyy-MM-dd").format(date)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        trackViewModel = ViewModelProviders.of(activity!!).get(TrackViewModel::class.java)
    }

    lateinit var progressDialog: ProgressDialog
    lateinit var retrofitBinding: RetrofitFragmentBindings
    @SuppressLint("NewApi")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        retrofitBinding = DataBindingUtil.inflate(inflater, R.layout.post_data_binding, container, false)

        tView = retrofitBinding.parentRetrofitlist
        retrofitBinding.pbLoading.visibility = View.VISIBLE

        var dateBinding = DateBinding(0, dateFMT)

        Log.e("CLICK DATE ", dateBinding.date + " " + tView + " " + retrofitBinding.dateBi.toString())
        val d = DateBi(1, dateBinding.date)

        trackViewModel.insertDate(d)
        retrofitBinding.dateBi = dateBinding
        //   progressDialog = ProgressDialog.show(activity,"Progress","Loading...",false)
        setAdapter()
        trackViewModel.fetchPostsFromWebSevice().observe(this, object : Observer<List<Track>> {
            override fun onChanged(t: List<Track>?) {
                trackViewModel.insertAllPosts(t)
//                Log.e("CLICK TRACK ", trackViewModel.insertAllPosts(t))
                /*  if(progressDialog.isShowing){
                      progressDialog.dismiss()
                  }*/
                if (retrofitBinding.pbLoading.visibility == View.VISIBLE) {
                    retrofitBinding.pbLoading.visibility = View.GONE
                    retrofitBinding.progressBarTitle.visibility = View.GONE
                }
            }
        })
        trackViewModel.getAllPosts().observe(this, object : Observer<List<Track>> {
            override fun onChanged(t: List<Track>?) {
                userPostAdapter.setListItems(t)
                Log.e("CLICK TRACK ", userPostAdapter.trackList.toString())
//                Log.e("CLICK", " " + userPostAdapter.trackList?.get(1)?.getTrackName())
                /*  if(progressDialog.isShowing){
                      progressDialog.dismiss()
                  }*/

            }
        })
        return tView
    }


    /* private fun initViews(view: View){
         retrofitRecyclerView = view.findViewById(R.id.post_list)as RecyclerView
     }*/
    lateinit var userPostAdapter: TrackAdapter
    var trackList: ArrayList<Track>? = null
    private fun setAdapter() {

        userPostAdapter = TrackAdapter()
        retrofitBinding.recycleView.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        retrofitBinding.recycleView.adapter = userPostAdapter
        retrofitBinding.recycleView!!.addOnItemTouchListener(TrackCallListener(this!!.activity!!, object : TrackCallListener.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {

                Log.e("CLICK", "" + position + " " + view + " " + arrayOf(trackViewModel.getAllPosts().toString() + " " + trackList?.get(0)
                        ?.getTrackGenre()) + " " + userPostAdapter.trackList?.get(position))
                if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
//                    (activity as MainActivity).show(userPostAdapter.trackList?.get(position)!!)
                    (activity as MainActivity).show(position)
                }
            }
        }))
    }

    fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }
}
