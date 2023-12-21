package yao.ic.appexample.data.state

sealed interface NetworkState {
    data class Success(val showState: ShowState) : NetworkState
    data class Error(val message: String) : NetworkState
    object Loading : NetworkState
}