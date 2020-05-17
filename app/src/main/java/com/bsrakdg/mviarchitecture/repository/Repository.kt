package com.bsrakdg.mviarchitecture.repository

import androidx.lifecycle.LiveData
import com.bsrakdg.mviarchitecture.model.BlogPost
import com.bsrakdg.mviarchitecture.model.User
import com.bsrakdg.mviarchitecture.network.CustomRetrofitBuilder
import com.bsrakdg.mviarchitecture.ui.main.state.MainViewState
import com.bsrakdg.mviarchitecture.util.ApiSuccessResponse
import com.bsrakdg.mviarchitecture.util.DataState
import com.bsrakdg.mviarchitecture.util.GenericApiResponse

object Repository {

    fun getBlogPosts(): LiveData<DataState<MainViewState>> {
        return object : NetworkBoundResource<List<BlogPost>, MainViewState>() {
            override fun createCall(): LiveData<GenericApiResponse<List<BlogPost>>> {
                return CustomRetrofitBuilder.networkService.getBlogPosts()
            }

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<List<BlogPost>>) {
                result.value = DataState.data(
                    message = null,
                    data = MainViewState(
                        blogPosts = response.body
                    )
                )
            }

        }.asLiveData()
    }

    fun getUser(userId: String): LiveData<DataState<MainViewState>> {
        return object : NetworkBoundResource<User, MainViewState>() {
            override fun createCall(): LiveData<GenericApiResponse<User>> {
                return CustomRetrofitBuilder.networkService.getUser(userId)
            }

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<User>) {
                result.value = DataState(
                    message = null,
                    data = MainViewState(
                        user = response.body
                    )
                )
            }
        }.asLiveData()
    }
}