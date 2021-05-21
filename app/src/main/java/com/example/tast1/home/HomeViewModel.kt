package com.example.tast1.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tast1.model.ExclusionItem
import com.example.tast1.model.Facility
import com.example.tast1.model.RootData
import com.example.tast1.repository.FetchDataRepository
import com.google.gson.Gson

class HomeViewModel(context: Context) : ViewModel() {

    private val url : String = "https://my-json-server.typicode.com/ricky1550/pariksha/db"

    private val fetchDataRepository : FetchDataRepository = FetchDataRepository(context)

    private val _fetchStatus = MutableLiveData<FetchStatus>()
    val fetchStatus : LiveData<FetchStatus>
        get() = _fetchStatus

    private val _facilities = MutableLiveData<List<Facility>>()

    private val _exclusions = MutableLiveData<List<List<ExclusionItem>>>()

    private val _genericList = MutableLiveData<List<Any>>()
    val genericList : LiveData<List<Any>>
        get() = _genericList


    fun fetchDataFromInternet() {
        _fetchStatus.value = FetchStatus.FETCHING

        fetchDataRepository.fetchData(
            url,
            onSuccess = { response ->
                val tempRootData = Gson().fromJson(response, RootData::class.java)
                _facilities.value = tempRootData.facilities
                _exclusions.value = tempRootData.exclusions
                parseFacilitiesToGenericList(tempRootData.facilities)
                _fetchStatus.value = FetchStatus.FETCHED
            },
            onFailure = {
                _facilities.value = null
                _exclusions.value = null
                _fetchStatus.value = FetchStatus.ERROR
            }
        )
    }

    private fun parseFacilitiesToGenericList(facilities: List<Facility>) {
        val list : ArrayList<Any> = ArrayList()

        facilities.map { facility ->
            list.add(facility.facilityName)

            facility.facilities.map { facilityItem ->
                list.add(facilityItem)
            }
        }

        _genericList.value = list
    }


    class Factory(private val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return HomeViewModel(context) as T
        }
    }
}

enum class FetchStatus {
    FETCHING,
    FETCHED,
    ERROR
}