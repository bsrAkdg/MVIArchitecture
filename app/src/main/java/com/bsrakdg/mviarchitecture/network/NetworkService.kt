package com.bsrakdg.mviarchitecture.network

import androidx.lifecycle.LiveData
import com.bsrakdg.mviarchitecture.model.BlogPost
import com.bsrakdg.mviarchitecture.model.User
import com.bsrakdg.mviarchitecture.util.GenericApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface NetworkService {

    @GET("placeholder/blogs")
    fun getBlogPosts(): LiveData<GenericApiResponse<List<BlogPost>>>

    @GET("placeholder/user/{userId}")
    fun getUser(
        @Path("userId") userId: String
    ): LiveData<GenericApiResponse<User>>

}