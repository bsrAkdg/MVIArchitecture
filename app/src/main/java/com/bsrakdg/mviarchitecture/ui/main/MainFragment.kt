package com.bsrakdg.mviarchitecture.ui.main

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bsrakdg.mviarchitecture.R
import com.bsrakdg.mviarchitecture.ui.DataStateListener
import com.bsrakdg.mviarchitecture.ui.main.state.MainStateEvent.GetBlogPostsEvent
import com.bsrakdg.mviarchitecture.ui.main.state.MainStateEvent.GetUserEvent
import com.bsrakdg.mviarchitecture.ui.main.viewmodel.MainViewModel

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var dataStateHandler: DataStateListener

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_get_user -> triggerUserEvent()
            R.id.action_get_blogs -> triggerBlogsEvent()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        viewModel = activity?.run {
            ViewModelProvider(this).get(MainViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        subscribeObservers()
    }

    private fun subscribeObservers() {

        // Get data from repository and update viewmodel data
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->

            println("DEBUG : DataState: $dataState")

            // Handle loading and message
            dataStateHandler.onDataStateChange(dataState)

            // Handle Data<T>
            dataState.data?.let { event ->
                event.getContentIfNotHandled()?.let { mainViewState ->
                    mainViewState.blogPosts?.let { blogPosts ->
                        // Set BlogPost data
                        viewModel.setBlogListData(blogPosts)
                    }

                    mainViewState.user?.let { user ->
                        // Set User data
                        viewModel.setUser(user)
                    }
                }
            }

            // Handle Error
            dataState.message?.let {
                println("DEBUG: MainFragment error")
            }

            // Handle Loading
            dataState.loading.let {
                println("DEBUG: MainFragment loading")
            }

        })

        // update UI
        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            viewState.blogPosts?.let {
                // Show blogs data
                println("DEBUG : Setting blog posts to Recyclerview $viewState")
            }

            viewState.user?.let {
                // Show user data
                println("DEBUG : Setting user data $viewState")
            }
        })
    }

    private fun triggerUserEvent() {
        viewModel.setStateEvent(GetUserEvent("1"))
    }

    private fun triggerBlogsEvent() {
        viewModel.setStateEvent(GetBlogPostsEvent())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            dataStateHandler = context as DataStateListener
        } catch (e: ClassCastException) {
            println("DEBUG: $context must implement DataStateListener")
        }
    }
}