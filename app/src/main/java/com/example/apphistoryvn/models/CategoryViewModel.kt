package com.example.apphistoryvn.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CategoryViewModel : ViewModel() {
    val liveCategoryList = MutableLiveData<List<categoryModel>>()
}