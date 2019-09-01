package com.example.mksob.itunesandroidapplication.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.example.mksob.itunesandroidapplication.db.APIServiceFactory
import com.example.mksob.itunesandroidapplication.db.TrackDB
import com.example.mksob.itunesandroidapplication.model.Track

class TrackViewModel: AndroidViewModel {

    private  var postInfoDBRepository: TrackDB
    private  var webServiceRepository: APIServiceFactory
    private  lateinit var retroObservable: LiveData<List<Track>>
    private  var mAllPosts: LiveData<List<Track>>


    constructor(application: Application) : super(application){
        postInfoDBRepository = TrackDB(application)
        webServiceRepository = APIServiceFactory()
        //   retroObservable = webServiceRepository.providesWebService()
        //  postInfoDBRepository.insertPost(retroObservable.value)
        mAllPosts  = postInfoDBRepository.getAllPosts()
    }

    fun getAllPosts(): LiveData<List<Track>> {
        return mAllPosts
    }


    fun fetchPostsFromWebSevice(): LiveData<List<Track>> {
        retroObservable =webServiceRepository.providesWebService()
        return retroObservable
    }

    fun insertAllPosts(postLists: List<Track>?){
        if(retroObservable.value!=null) {
            postInfoDBRepository.insertPost(postLists)
        }
    }
}