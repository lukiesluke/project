package com.example.mksob.itunesandroidapplication.db

import android.annotation.SuppressLint
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.example.mksob.itunesandroidapplication.model.ResultTrack
import com.example.mksob.itunesandroidapplication.model.Track
import okhttp3.OkHttpClient
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*
import java.util.concurrent.TimeUnit

class APIServiceFactory {
    private fun providesOkHttpClientBuilder(): OkHttpClient {

        val httpClient = OkHttpClient.Builder()
        return httpClient.readTimeout(1200, TimeUnit.SECONDS)
                .connectTimeout(1200, TimeUnit.SECONDS).build()

    }

    fun providesWebService(): LiveData<List<Track>> {
        val data = MutableLiveData<List<Track>>()
        var webserviceResponseList: List<Track>

        try {
            val retrofit = Retrofit.Builder()
                    .baseUrl(APIUrl.BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(providesOkHttpClientBuilder())
                    .build()

            //Defining retrofit api service
            val service = retrofit.create(APIService::class.java)
            service.makeRequest().enqueue(object : Callback<ResultTrack> {
                override fun onResponse(call: Call<ResultTrack>, response: Response<ResultTrack>) {
                    webserviceResponseList = response.body()!!.track!!
                    data.setValue(webserviceResponseList)
                }

                override fun onFailure(call: Call<ResultTrack>, t: Throwable) {
                    Log.d("Repository", "Failed:::")
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return data
    }

    @SuppressLint("NewApi")
    private fun parseJson(response: String?): List<Track> {
        val apiResults = ArrayList<Track>()
        var dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:SS'Z'")

        try {
            val jsonObject = JSONObject(response)
            val jsonArray: JSONArray = jsonObject.getJSONArray("results")

            for (jsonIndex in 0..(jsonArray.length() - 1)) {
                var jsonInfo: JSONObject = jsonArray.getJSONObject(jsonIndex)
                val mMovieModel = Track()

                if (!jsonInfo.isNull("trackName")) {
                    mMovieModel.setTrackName(jsonInfo.getString("trackName"))
                }

                if (!jsonInfo.isNull("primaryGenreName")) {
                    mMovieModel.setTrackGenre(jsonInfo.getString("primaryGenreName"))
                }

                if (!jsonInfo.isNull("artworkUrl100")) {
                    mMovieModel.setImageUrl(jsonInfo.getString("artworkUrl100")?.replace("100x100", "600x600"))
                }

                if (!jsonInfo.isNull("collectionPrice")) {
                    mMovieModel.setCollectionPrice(jsonInfo.getDouble("collectionPrice"))
                }

                if (!jsonInfo.isNull("longDescription")) {
                    mMovieModel.setTrackDescription(jsonInfo.getString("longDescription"))
                }

                if (!jsonInfo.isNull("releaseDate")) {
                    mMovieModel.setReleaseDate(LocalDate.parse(jsonInfo.getString("releaseDate"), formatter).format(dateFormatter))
                }

                apiResults.add(mMovieModel)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
            Log.d("Repository CATCH!!!!!", "Repository JSONS " + e)
        }

        Log.i(javaClass.simpleName, apiResults.size.toString())
        return apiResults

    }
}

