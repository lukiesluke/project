package com.example.mksob.itunesandroidapplication.second

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.mksob.itunesandroidapplication.db.APIServiceFactory
import com.example.mksob.itunesandroidapplication.db.TrackDB
import com.example.mksob.itunesandroidapplication.model.Track

class TrackDViewModel : AndroidViewModel {

    private var postInfoDBRepository: TrackDB
    private var webServiceRepository: APIServiceFactory
    private lateinit var retroObservable: LiveData<List<Track>>
    private var mAllPosts: LiveData<List<Track>>
//    private var trackID : String

    private var trackName: MutableLiveData<String>

    constructor(application: Application) : super(application) {
        postInfoDBRepository = TrackDB(application)
        webServiceRepository = APIServiceFactory()
        mAllPosts = postInfoDBRepository.getAllPosts()
//        details = postInfoDBRepository.getDetails()!!
        trackName = MutableLiveData()
    }

    fun getAllPosts(): LiveData<List<Track>> {
        return mAllPosts
    }

//    fun getDetails(): String {
//        return details
//    }

    fun setTrackName(trackName: String) {
        this.trackName.setValue(trackName)
    }



    fun fetchPostsFromWebSevice(): LiveData<List<Track>> {
        retroObservable = webServiceRepository.providesWebService()
        return retroObservable
    }

    fun insertAllPosts(postLists: List<Track>?) {
        if (retroObservable.value != null) {
            postInfoDBRepository.insertPost(postLists)
        }
    }
}