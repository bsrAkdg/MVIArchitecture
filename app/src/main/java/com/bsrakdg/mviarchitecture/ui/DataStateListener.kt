package com.bsrakdg.mviarchitecture.ui

import com.bsrakdg.mviarchitecture.util.DataState

interface DataStateListener {
    fun onDataStateChange(dataState: DataState<*>?) // dataState maybe null
}