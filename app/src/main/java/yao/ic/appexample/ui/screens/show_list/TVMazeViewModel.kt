package yao.ic.appexample.ui.screens.show_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import yao.ic.appexample.data.state.ShowState
import yao.ic.appexample.data.repository.TVMazeRepository
import yao.ic.appexample.data.state.NetworkState
import javax.inject.Inject

@HiltViewModel
class TVMazeViewModel @Inject constructor(
    private val repository: TVMazeRepository
) : ViewModel() {

    private val _state = MutableStateFlow<NetworkState>(NetworkState.Loading)
    val state: StateFlow<NetworkState> = _state

    init {
        getShows()
    }

    fun searchShow(id: String) = viewModelScope.launch {
        _state.update {
            try {
                NetworkState.Success(
                    ShowState(
                        searchedShows = repository.searchShow(id)
                    )
                )
            } catch (e: Exception) {
                NetworkState.Error(e.message ?: "Unknown error")
            }
        }

    }

    fun getShowDetail(id: Int) = viewModelScope.launch {
        _state.update {
            try {
                NetworkState.Success(
                    ShowState(
                        showDetail = repository.getShowDetail(id)
                    )
                )
            } catch (e: Exception) {
                NetworkState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun getShows() = viewModelScope.launch {
        _state.update { NetworkState.Loading }
        delay(2500L)

        _state.update {
            try {
                NetworkState.Success(
                    ShowState(
                        showList = repository.getShows().subList(0, 34)
                    )
                )
            } catch (e: Exception) {
                NetworkState.Error(e.message ?: "Unknown error")
            }
        }
    }

}