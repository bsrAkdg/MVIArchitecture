package com.bsrakdg.mviarchitecture.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.bsrakdg.mviarchitecture.util.*
import com.bsrakdg.mviarchitecture.util.Constants.Companion.TESTING_NETWORK_DELAY
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/** ViewStateType : MainViewState
 *  ResponseObject : GenericApiResponse
 */
abstract class NetworkBoundResource<ResponseObject, ViewStateType> {

    protected val result = MediatorLiveData<DataState<ViewStateType>>()

    init {
        result.value = DataState.loading(true)

        GlobalScope.launch(IO) {
            delay(TESTING_NETWORK_DELAY) // For testing

            withContext(Main) {

                val apiResponse = createCall()

                result.addSource(apiResponse) {
                    result.removeSource(apiResponse)

                    handleNetworkCall(it)
                }
            }
        }
    }

    private fun handleNetworkCall(response: GenericApiResponse<ResponseObject>) {
        when (response) {
            is ApiSuccessResponse -> {
                handleApiSuccessResponse(response)
            }
            is ApiErrorResponse -> {
                println("DEBUG: NetworkBoundResource : {${response.errorMessage}}")
                onReturnError(response.errorMessage)
            }
            is ApiEmptyResponse -> {
                println("DEBUG: NetworkBoundResource : HTTP 204. Returned NOTHING!")
                onReturnError("HTTP 204. Returned NOTHING!")
            }
        }
    }

    fun onReturnError(message: String) {
        result.value = DataState.error(message)
    }

    abstract fun createCall(): LiveData<GenericApiResponse<ResponseObject>>

    abstract fun handleApiSuccessResponse(response: ApiSuccessResponse<ResponseObject>)

    fun asLiveData() = result as LiveData<DataState<ViewStateType>>
}