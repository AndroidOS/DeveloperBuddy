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
    val stackItems = MutableLiveData<List<Stack>>()
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
//                        Toast.makeText(
//                            getApplication(),
//                            "Items retrieved from endpoint",
//                            Toast.LENGTH_SHORT
//                        ).show()

                        soItems.value = itemList.items
                        if (itemList.items != null) {
                            storeItemsLocally(createStackListFromDownload(itemList.items))
                            //fetchFromDatabase()
                        } else {
                            Toast.makeText(
                                getApplication(),
                                "Error retrieving items",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        //fetchFromDatabase()

                    }

                    override fun onError(e: Throwable) {
                        Log.d(TAG, " RxJava error")
                        loading.value = false
                        e.printStackTrace()
                    }

                })
        )
    }

    private fun fetchFromDatabase() {
        loading.value = true
        launch {
            val stacks = StackDatabase(getApplication()).stackDao().getAllStacks()
            stacksRetrieved(stacks)
            //Toast.makeText(getApplication(), "Stack users retrieved from database. ${stacks.size} objects", Toast.LENGTH_SHORT).show()
        }
    }

    private fun stacksRetrieved(stackList: List<Stack>) {
        stackItems.value = stackList
        loading.value = false
        Toast.makeText(
            getApplication(),
            "Database retrieval ${stackList.size} objects",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun storeItemsLocally(list: List<Stack>) {
        launch {
            val dao = StackDatabase(getApplication()).stackDao()
            dao.deleteAllStacks()
            val result = dao.insertAll(*list.toTypedArray())
            var i = 0
            Toast.makeText(
                getApplication(),
                "Database insert ${result.size} objects",
                Toast.LENGTH_SHORT
            ).show()
            while (i < list.size) {
                list[i].uuid = result[i].toInt()
                ++i
            }

            fetchFromDatabase()

        }
    }

    private fun createStackListFromDownload(list: List<Item>): List<Stack> {
        var stackList = mutableListOf<Stack>()
        for (item in list) {
            val stack = Stack(
                item.user?.user_id,
                item.user?.reputation,
                item.user?.user_type,
                item.user?.profile_image,
                item.user?.display_name,
                item.name,
                item.user?.accept_rate,
                item.badge_type,
                item.badge_id
            )

            stackList.add(stack)
        }

        return stackList
    }


    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}