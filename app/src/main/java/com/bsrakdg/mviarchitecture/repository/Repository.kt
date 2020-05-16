package com.bsrakdg.mviarchitecture.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.bsrakdg.mviarchitecture.network.CustomRetrofitBuilder
import com.bsrakdg.mviarchitecture.ui.main.state.MainViewState
import com.bsrakdg.mviarchitecture.util.ApiEmptyResponse
import com.bsrakdg.mviarchitecture.util.ApiErrorResponse
import com.bsrakdg.mviarchitecture.util.ApiSuccessResponse

object Repository {

    fun getBlogPosts(): LiveData<MainViewState> {
        return Transformations.switchMap(
            CustomRetrofitBuilder.networkService.getBlogPosts()
        ) { apiResponse ->
            object : LiveData<MainViewState>() {
                override fun onActive() {
                    super.onActive()
                    when (apiResponse) {
                        is ApiSuccessResponse -> {
                            value = MainViewState(
                                blogPosts = apiResponse.body
                            )
                        }

                        is ApiErrorResponse -> {
                            value = MainViewState()
                        }

                        is ApiEmptyResponse -> {
                            value = MainViewState()
                        }
                    }
                }
            }

        }
    }

    fun getUser(userId: String): LiveData<MainViewState> {
        return Transformations.switchMap(
            CustomRetrofitBuilder.networkService.getUser(userId)
        ) { apiResponse ->
            object : LiveData<MainViewState>() {
                override fun onActive() {
                    super.onActive()
                    when (apiResponse) {
                        is ApiSuccessResponse -> {
                            value = MainViewState(
                                user = apiResponse.body
                            )
                        }

                        is ApiErrorResponse -> {
                            value = MainViewState()
                        }

                        is ApiEmptyResponse -> {
                            value = MainViewState()
                        }
                    }
                }
            }

        }
    }

}