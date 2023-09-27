package com.example.movieapp.ui.detail

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.network.ApiClient
import com.example.movieapp.util.Constans
import kotlinx.coroutines.launch
import java.lang.Exception

class DetailViewModel : ViewModel() {

    val movieResponse : MutableLiveData<movieDetailResponse> = MutableLiveData()
    val isLoading = MutableLiveData(false)
    val errorMessage : MutableLiveData<String?> = MutableLiveData()


    @SuppressLint("SuspiciousIndentation")
    fun getMovieDetail(movieId : Int) {
        isLoading.value = true

        viewModelScope.launch {
            try {
            val response = ApiClient.getClient().getMovieDetail(movieId = movieId.toString(), token = Constans.BEARER_TOKEN)
                if (response.isSuccessful){
                    movieResponse.postValue(response.body())
                } else {
                    if (response.message().isNullOrEmpty()){
                        errorMessage.value = "Bilinmeyen Bir Hata!"
                    } else {
                        errorMessage.value = response.message()
                    }
                }
            } catch (e : Exception) {
                errorMessage.value = e.message

        } finally {
            isLoading.value = false
        }
    }
    }
}