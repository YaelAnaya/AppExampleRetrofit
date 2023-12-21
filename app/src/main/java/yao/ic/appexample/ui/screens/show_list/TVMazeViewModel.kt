package yao.ic.appexample.ui.screens.show_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
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

    fun toggleFavorite(id: Int): Flow<Boolean> = channelFlow {
        isFavorite(id).flowOn(Dispatchers.IO).take(1).collectLatest { isFavorite ->
            if (isFavorite) {
                removeFavoriteShow(id)
                send(false)
            } else {
                addFavoriteShow(id)
                send(true)
            }
        }
    }
    private suspend fun addFavoriteShow(id: Int) {
        val show = repository.getShowDetail(id)
        repository.addFavoriteShow(show.toFavoriteShow())
    }

    private suspend fun removeFavoriteShow(id: Int) {
        val show = repository.getShowDetail(id)
        repository.removeFavoriteShow(show.toFavoriteShow())
    }

    fun isFavorite(id: Int): Flow<Boolean> = channelFlow {
        repository.isFavorite(id).take(1).collectLatest { isFavorite ->
            Log.d("TVMazeViewModel", "isFavorite: $isFavorite")
            send(isFavorite)
        }
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
        _state.update {
            NetworkState.Loading
        }
        delay(400L)
        repository.getShows()
            .flowOn(Dispatchers.IO)
            .catch { e ->
                _state.update {
                    NetworkState.Error(e.message ?: "Unknown error")
                }
            }.collect { shows ->
                _state.update {
                    NetworkState.Success(
                        ShowState(
                            showList = shows
                        )
                    )
                }
            }
    }

}