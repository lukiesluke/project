package com.example.mksob.itunesandroidapplication.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableField
import com.example.mksob.itunesandroidapplication.db.APIServiceFactory
import com.example.mksob.itunesandroidapplication.db.DateBi
import com.example.mksob.itunesandroidapplication.db.DateDB
import com.example.mksob.itunesandroidapplication.db.TrackDB
import com.example.mksob.itunesandroidapplication.model.Track

class TrackViewModel : AndroidViewModel {

    private var postInfoDBRepository: TrackDB
    private var dateInfo: DateDB
    private var webServiceRepository: APIServiceFactory
    private lateinit var retroObservable: LiveData<List<Track>>
    private var mAllPosts: LiveData<List<Track>>
    private var dateString: LiveData<String>

    var observableTrack: LiveData<Track>? = null
    var track = ObservableField<Track>()
    private var trackName: MutableLiveData<String>

    constructor(application: Application) : super(application) {
        postInfoDBRepository = TrackDB(application)
        dateInfo = DateDB(application)
        webServiceRepository = APIServiceFactory()
        mAllPosts = postInfoDBRepository.getAllPosts()
        trackName = MutableLiveData()
        dateString = dateInfo.getDate()
    }

//    init {
//
//        this.trackName = MutableLiveData()
//    }

    init {

        this.trackName = MutableLiveData()

//        observableTrack = Transformations.switchMap(trackName) { input ->
//            if (input.isEmpty()) {
//                Log.i(TAG, "ProjectViewModel projectID is absent!!!")
//                return@switchMap ABSENT
//            }
//
//            Log.i("TAG", "ProjectViewModel projectID is " + trackName.value)
//
////            providesWebService()("Google", projectID.value)
//            Log.i("TAG", "ProjectViewModel projectID is " + trackName.value)
//        }
    }


    fun getAllPosts(): LiveData<List<Track>> {
        return mAllPosts
    }

    fun fetchPostsFromWebSevice(): LiveData<List<Track>> {
        retroObservable = webServiceRepository.providesWebService()
        return retroObservable
    }

    fun setTrackName(trackName: String) {
        this.trackName.setValue(trackName)
    }

    fun setTrack(track: Track) {
        this.track.set(track)
    }

    fun insertAllPosts(postLists: List<Track>?) {
        if (retroObservable.value != null) {
            postInfoDBRepository.insertPost(postLists)
        }
    }

    fun insertDate(dateBi: DateBi) {
        dateInfo.insertDate(dateBi)
    }

    fun getDate(): LiveData<String> {
        return dateInfo.getDate()
    }

    companion object {
        private val TAG = TrackViewModel::class.java!!.getName()
        private val ABSENT = MutableLiveData<String>()
    }
}
