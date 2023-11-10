package yao.ic.appexample.network

import yao.ic.appexample.data.repository.AmphibianState

sealed interface NetworkState {
    data class Success(val amphibianState: AmphibianState) : NetworkState
    data class Error(val message: String) : NetworkState
    object Loading : NetworkState
}