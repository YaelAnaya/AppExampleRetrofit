package yao.ic.appexample.ui.screens.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import yao.ic.appexample.data.repository.TVMazeRepository
import yao.ic.appexample.data.state.ShowState
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: TVMazeRepository
) : ViewModel() {

    private val _state = MutableStateFlow<ShowState>(ShowState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getFavoriteShows()
                .flowOn(Dispatchers.IO)
                .collectLatest {
                    _state.emit(_state.value.copy(favoriteShows = it))
            }
        }
    }

}