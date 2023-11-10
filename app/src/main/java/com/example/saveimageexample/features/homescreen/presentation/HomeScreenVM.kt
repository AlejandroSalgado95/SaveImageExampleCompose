package com.example.saveimageexample.features.homescreen.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.saveimageexample.database.entity.ImageEntity
import com.example.saveimageexample.features.homescreen.data.ImageRepository

@HiltViewModel
class HomeScreenVM @Inject constructor(
    private val repository: ImageRepository,
) : ViewModel() {
    var homeState by mutableStateOf(HomeScreenState())

    fun getImage() {
        viewModelScope.launch {
            var myImage = repository.getImage()
            myImage?.let {
                homeState = homeState.copy(imageEntity = myImage)
            }
        }
    }
    fun setImage(myImage : ImageEntity){
        viewModelScope.launch {
            repository.setImage(myImage)
            homeState = homeState.copy(imageEntity = myImage)
        }
    }

}