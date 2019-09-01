package com.example.mksob.itunesandroidapplication.db

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
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
import java.util.ArrayList
import java.util.concurrent.TimeUnit

public class APIServiceFactory {
    private fun providesOkHttpClientBuilder(): OkHttpClient {

        val httpClient = OkHttpClient.Builder()
        return httpClient.readTimeout(1200, TimeUnit.SECONDS)
                .connectTimeout(1200, TimeUnit.SECONDS).build()

    }

    fun providesWebService(): LiveData<List<Track>> {
        val data = MutableLiveData<List<Track>>()
        var webserviceResponseList: List<Track>

//        val response = ""
        try {
            val retrofit = Retrofit.Builder()
                    .baseUrl(APIUrl.BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(providesOkHttpClientBuilder())
                    .build()

            //Defining retrofit api service
            val service = retrofit.create(APIService::class.java)
            //  response = service.makeRequest().execute().body();
            service.makeRequest().enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    Log.d("Repository", "Response::::" + response.body()!!)
                    webserviceResponseList = parseJson(response.body())
                    data.setValue(webserviceResponseList)
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.d("Repository", "Failed:::")
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return data

    }

    private fun parseJson(response: String?): List<Track> {

        val apiResults = ArrayList<Track>()
        val jsonObject: JSONObject
        val jsonArray1: JSONArray
        val jsonArray : JSONArray
        try {

            jsonObject = JSONObject(response)
            jsonArray = jsonObject.getJSONArray("results")
            Log.d("Repository ", "JSONS!!!!! " + jsonArray)

            for (i in 0 until jsonArray.length()) {

                var jsonInfo: JSONObject = jsonArray.getJSONObject(i)
                Log.d("Repository ", "JSONS OBJECT!!!!! " + jsonInfo)
                val mMovieModel = Track()
//                Log.d("Repository ", "JSONS ARTIST!!!!! " + Integer.parseInt(jsonInfo.getString("artistId")))
                //mMovieModel.setId(object.getString("id"));
                if(jsonInfo.isNull("trackId")) {
                    Log.d("Repository ", "trackId NULL")
                    mMovieModel.setId(Integer.parseInt("12345"))
                } else {
                    mMovieModel.setId(Integer.parseInt(jsonInfo.getString("trackId")))
                }
                if(jsonInfo.isNull("artistName")) {
                    Log.d("Repository ", "artistName NULL")
                    mMovieModel.setTitle("artistName")
                } else {
                    mMovieModel.setTitle(jsonInfo.getString("artistName"))
                }

                if(jsonInfo.isNull("trackName")) {
                    Log.d("Repository ", "trackName NULL")
                    mMovieModel.setBody("trackName")
                } else {
                    mMovieModel.setBody(jsonInfo.getString("trackName"))
                }

                if(jsonInfo.isNull("artworkUrl100")) {
                    Log.d("Repository ", "artworkUrl100 NULL")
                    mMovieModel.setImageUrl("artworkUrl100")
                } else {
                    mMovieModel.setImageUrl(jsonInfo.getString("artworkUrl100"))
                }

//                mMovieModel.setTitle(jsonInfo.getString("artistName"))
//                mMovieModel.setBody(jsonInfo.getString("trackName"))
//                mMovieModel.setImageUrl("http://via.placeholder.com/100/0000ff/ffffff?text=LazyImage" + i)
//                mMovieModel.setImageUrl(jsonInfo.getString("artworkUrl100"))
                apiResults.add(mMovieModel)
                Log.d("Repository ", "JSONS APIRESULTS!!!!! " + apiResults)
            }


        } catch (e: JSONException) {
            e.printStackTrace()
            Log.d("Repository CATCH!!!!!", "Repository JSONS " + e)
        }

        Log.i(javaClass.simpleName, apiResults.size.toString())
        return apiResults

    }


}