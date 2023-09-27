package com.example.movieapp.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.model.MovieItem
import com.example.movieapp.network.ApiClient
import com.example.movieapp.util.Constans
import kotlinx.coroutines.launch
import java.lang.Exception

class HomeViewModel : ViewModel() {

    val movieList : MutableLiveData<List<MovieItem?>?> = MutableLiveData()
    val isLoading = MutableLiveData(false)
    val errorMessage : MutableLiveData<String?> = MutableLiveData()

    fun getMovieList(){
        isLoading.value = true

        viewModelScope.launch {
            try {
                val response = ApiClient.getClient().getMovieList(token = Constans.BEARER_TOKEN)

                if (response.isSuccessful){
                    movieList.postValue(response.body()?.movieItems)
                } else {
                    if (response.message().isNullOrEmpty()){
                        errorMessage.value = "Bilinmeyen bir hata meydana geldi."
                    } else{
                        errorMessage.value = response.message()
                    }
                }
            } catch (e : Exception){
                errorMessage.value = e.message
            }
            finally {
                isLoading.value = false
            }
        }
    }
}