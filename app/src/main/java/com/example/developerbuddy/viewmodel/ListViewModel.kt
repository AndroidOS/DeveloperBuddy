package com.example.developerbuddy.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.developerbuddy.model.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

private const val TAG = "ListViewModel"

class ListViewModel(application: Application) : BaseViewModel(application) {


    private val sOService = SOApiService()
    private val disposable = CompositeDisposable()

    val soItems = MutableLiveData<List<Item>>()
    val loading = MutableLiveData<Boolean>()
    val menu_email = MutableLiveData<Boolean>()

    fun refresh() {
        fetchFromRemote()
    }

    
    private fun fetchFromRemote() {
        loading.value = true
        disposable.add(
            sOService.getSOItems()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<RootObject>() {
                    override fun onSuccess(itemList: RootObject) {
                        //Log.d(TAG, "List size =  ${jobList.size}")
                        Toast.makeText(
                            getApplication(),
                            "Items retrieved from endpoint",
                            Toast.LENGTH_SHORT
                        ).show()
                        soItems.value = itemList.items
                        //storeItemsLocally(itemList)

                    }

                    override fun onError(e: Throwable) {
                        Log.d(TAG, " RxJava error")
                        loading.value = false
                        e.printStackTrace()
                    }

                })
        )
    }

    private fun storeItemsLocally(list: List<Stack>) {
        launch {
            val dao = StackDatabase(getApplication()).stackDao()
            dao.deleteAllStacks()
            val result = dao.insertAll(*list.toTypedArray())
            var i = 0
            while (i < list.size) {
                list[i].uuid = result[i].toInt()
                ++i
            }

        }
    }


    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}