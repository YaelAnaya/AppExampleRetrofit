package yao.ic.appexample.network

import yao.ic.appexample.data.repository.ShowState

sealed interface NetworkState {
    data class Success(val showState: ShowState) : NetworkState
    data class Error(val message: String) : NetworkState
    object Loading : NetworkState
}