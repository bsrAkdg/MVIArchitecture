package com.bsrakdg.mviarchitecture.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.bsrakdg.mviarchitecture.ui.main.state.MainStateEvent
import com.bsrakdg.mviarchitecture.ui.main.state.MainViewState
import com.bsrakdg.mviarchitecture.util.AbsentLiveData

class MainViewModel : ViewModel() {

    private val _stateEvent: MutableLiveData<MainStateEvent> = MutableLiveData()
    private val _viewState: MutableLiveData<MainViewState> = MutableLiveData()

    val viewState: LiveData<MainViewState>
        get() = _viewState

    // Listen state event changes and handle it
    val dataState: LiveData<MainViewState> = Transformations.switchMap(_stateEvent) { stateEvent ->
        stateEvent?.let {
            handleStateEvent(it)
        }
    }

    private fun handleStateEvent(stateEvent: MainStateEvent): LiveData<MainViewState> {
        return when (stateEvent) {
            is MainStateEvent.GetBlogPostsEvent -> {
                AbsentLiveData.create()
            }

            is MainStateEvent.GetUserEvent -> {
                AbsentLiveData.create()

            }
            is MainStateEvent.None -> {
                AbsentLiveData.create()
            }
        }
    }
}