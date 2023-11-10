package yao.ic.appexample.ui.screens.amphibians

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import yao.ic.appexample.data.repository.AmphibianState
import yao.ic.appexample.data.repository.AmphibiansRepository
import yao.ic.appexample.network.NetworkState
import javax.inject.Inject

@HiltViewModel
class AmphibiansViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: AmphibiansRepository
) : ViewModel() {

    private val _state = MutableStateFlow<NetworkState>(NetworkState.Loading)
    val state: StateFlow<NetworkState> = _state

    init {
        getAmphibians()
    }

    fun getAmphibians() = viewModelScope.launch {
        _state.update { NetworkState.Loading }
        delay(2500L)

        _state.update {
            try {
                NetworkState.Success(
                    AmphibianState(repository.getAmphibians())
                )

            } catch (e: Exception) {
                NetworkState.Error(e.message ?: "Unknown error")
            }
        }
    }

}