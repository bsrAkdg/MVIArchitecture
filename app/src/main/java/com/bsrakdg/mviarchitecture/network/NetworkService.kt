package com.bsrakdg.mviarchitecture.network

import com.bsrakdg.mviarchitecture.model.BlogPost
import com.bsrakdg.mviarchitecture.model.User
import retrofit2.http.GET
import retrofit2.http.Path

interface NetworkService {

    @GET("placeholder/blogs")
    fun getBlogPosts(): List<BlogPost>

    @GET("placeholder/user/{userId}")
    fun getUser(
        @Path("userId") userId: String
    ): User

}