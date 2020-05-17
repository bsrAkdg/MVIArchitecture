package com.bsrakdg.mviarchitecture.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.bsrakdg.mviarchitecture.network.CustomRetrofitBuilder
import com.bsrakdg.mviarchitecture.ui.main.state.MainViewState
import com.bsrakdg.mviarchitecture.util.ApiEmptyResponse
import com.bsrakdg.mviarchitecture.util.ApiErrorResponse
import com.bsrakdg.mviarchitecture.util.ApiSuccessResponse
import com.bsrakdg.mviarchitecture.util.DataState

object Repository {

    fun getBlogPosts(): LiveData<DataState<MainViewState>> {
        return Transformations.switchMap(
            CustomRetrofitBuilder.networkService.getBlogPosts()
        ) { apiResponse ->
            object : LiveData<DataState<MainViewState>>() {
                override fun onActive() {
                    super.onActive()
                    when (apiResponse) {
                        is ApiSuccessResponse -> {
                            value = DataState.data(
                                data = MainViewState(
                                    blogPosts = apiResponse.body
                                )
                            )
                        }

                        is ApiErrorResponse -> {
                            value = DataState.error(apiResponse.errorMessage)
                        }

                        is ApiEmptyResponse -> {
                            value = DataState.error("http 204. Returned NOTHING!")
                        }
                    }
                }
            }
        }
    }

    fun getUser(userId: String): LiveData<DataState<MainViewState>> {
        return Transformations.switchMap(
            CustomRetrofitBuilder.networkService.getUser(userId)
        ) { apiResponse ->
            object : LiveData<DataState<MainViewState>>() {
                override fun onActive() {
                    super.onActive()
                    when (apiResponse) {
                        is ApiSuccessResponse -> {
                            value = DataState.data(
                                data = MainViewState(
                                    user = apiResponse.body
                                )
                            )
                        }

                        is ApiErrorResponse -> {
                            value = DataState.error(message = apiResponse.errorMessage)
                        }

                        is ApiEmptyResponse -> {
                            value = DataState.error(message = "http 204. Returned NOTHING!")
                        }
                    }
                }
            }
        }
    }
}