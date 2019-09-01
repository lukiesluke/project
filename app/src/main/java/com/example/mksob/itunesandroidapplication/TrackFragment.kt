package com.example.mksob.itunesandroidapplication

import android.app.ProgressDialog
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.mksob.itunesandroidapplication.adapter.TrackAdapter
import com.example.mksob.itunesandroidapplication.model.Track
import com.example.mksob.itunesandroidapplication.viewModel.TrackViewModel
import kotlinx.android.synthetic.main.post_data_binding.*
import com.example.mksob.itunesandroidapplication.databinding.RetrofitFragmentBindings

//import kotlinx.android.synthetic.main.post_data_binding.*

class TrackFragment : Fragment() {
    lateinit var tView : View
    lateinit var trackViewModel: TrackViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        trackViewModel = ViewModelProviders.of(activity!!).get(TrackViewModel::class.java)
    }

    lateinit var progressDialog: ProgressDialog
    lateinit var retrofitBinding: RetrofitFragmentBindings
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //  retroFitView = inflater.inflate(R.layout.fragment_retro_fit_post,container,false)
        // initViews(retroFitView)

        /*postListViewModel.getAllPosts().observe(this,object :Observer<List<ResultModel>>{
            override fun onChanged(t: List<ResultModel>?) {
                userPostAdapter.setListItems(t)
            }

        })
*/

        retrofitBinding = DataBindingUtil.inflate(inflater,R.layout.post_data_binding,container,false)
        tView = retrofitBinding.getRoot()
        retrofitBinding.pbLoading.visibility = View.VISIBLE

        //   progressDialog = ProgressDialog.show(activity,"Progress","Loading...",false)
        setAdapter()
        trackViewModel.fetchPostsFromWebSevice().observe(this,object :Observer<List<Track>>{
            override fun onChanged(t: List<Track>?) {
                trackViewModel.insertAllPosts(t)
                /*  if(progressDialog.isShowing){
                      progressDialog.dismiss()
                  }*/
                if(retrofitBinding.pbLoading.visibility == View.VISIBLE){
                    retrofitBinding.pbLoading.visibility = View.GONE
                    retrofitBinding.progressBarTitle.visibility = View.GONE
                }
            }
        })
        trackViewModel.getAllPosts().observe(this,object:Observer<List<Track>>{
            override fun onChanged(t: List<Track>?) {
                userPostAdapter.setListItems(t)
                /*  if(progressDialog.isShowing){
                      progressDialog.dismiss()
                  }*/

            }
        })
        /*
        userViewModel.getAllUsers().observe(this, object : Observer <List<User>> {
            override fun onChanged(users: List<User>?) {
                // Update the cached copy of the words in the adapter.
                userAdapter.setListItems(users)
            }
        })
         */

        return tView
    }


    /* private fun initViews(view: View){
         retrofitRecyclerView = view.findViewById(R.id.post_list)as RecyclerView
     }*/
    lateinit var userPostAdapter:TrackAdapter
    private  fun setAdapter(){
        /* if(progressDialog!=null && progressDialog.isShowing){
             progressDialog.dismiss()
         }*/
        /* if(retrofitBinding.pbLoading.visibility == View.VISIBLE){
             retrofitBinding.pbLoading.visibility = View.GONE
             retrofitBinding.progressBarTitle.visibility = View.GONE
         }*/
        userPostAdapter = TrackAdapter()
        retrofitBinding.postList.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        //   retrofitRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        retrofitBinding.postList.addItemDecoration(DividerItemDecoration(activity,DividerItemDecoration.VERTICAL))
        retrofitBinding.postList.adapter = userPostAdapter

    }
}