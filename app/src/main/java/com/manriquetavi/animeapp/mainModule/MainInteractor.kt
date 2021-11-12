package com.manriquetavi.animeapp.mainModule

import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.manriquetavi.animeapp.AnimeApplication
import com.manriquetavi.animeapp.common.entities.AnimeEntity
import com.manriquetavi.animeapp.common.utils.Constants
import org.json.JSONObject

class MainInteractor {

    fun getAnimes(callback: (MutableList<AnimeEntity>) -> Unit){
        getAnimesAPI { storeList -> callback(storeList) }
    }

    fun getAnimesAPI(callback: (MutableList<AnimeEntity>) -> Unit){

        val animeList = mutableListOf<AnimeEntity>()

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, Constants.GET_ANIME_PATH, null,{ response ->

            val jsonList = response.getJSONArray(Constants.DATA_PROPERTY)


            for (i in 0 until jsonList.length()){
                var title: String?
                val coverImage: String?
                val youtubeVideoId: String
                val creationAt: String?
                val description: String

                val property = jsonList.getJSONObject(i).getJSONObject(Constants.ATTRIBUTES_PROPERTY)

                var aux = jsonList.getJSONObject(i).getJSONObject(Constants.ATTRIBUTES_PROPERTY).getJSONObject(Constants.TITLES_PROPERTY)
                title = aux.optString("en").toString()
                if (title != null){
                    title = aux.getString("en_jp").toString()
                }

                val aux1 = property.optJSONObject("coverImage")
                if(aux1 != null){
                    coverImage = aux1.getString("original").toString()
                } else{
                    coverImage = " "
                }

                youtubeVideoId = Constants.YOUTUBE_BASE_URL + property.optString("youtubeVideoId")
                creationAt = property.optString("createdAt")
                description = property.optString("description")

                val animeEntity = AnimeEntity(i,title,coverImage,youtubeVideoId,creationAt,description)

                animeList.add(animeEntity)
            }
            callback(animeList)
        }, {
            it.printStackTrace()
            callback(animeList)
        })

        AnimeApplication.animeAPI.addToRequestQueue(jsonObjectRequest)
    }
}