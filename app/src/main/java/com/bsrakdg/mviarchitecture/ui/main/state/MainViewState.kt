package com.bsrakdg.mviarchitecture.ui.main.state

import com.bsrakdg.mviarchitecture.model.BlogPost
import com.bsrakdg.mviarchitecture.model.User

data class MainViewState(
    var blogPosts: List<BlogPost>? = null,
    var user: User? = null
)