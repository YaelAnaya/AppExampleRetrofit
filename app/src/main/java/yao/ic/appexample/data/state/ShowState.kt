package yao.ic.appexample.data.state

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import okhttp3.internal.immutableListOf
import yao.ic.appexample.data.database.entity.FavoriteShow
import yao.ic.appexample.data.model.Show

data class ShowState(
    val showList: List<Show> = emptyList(),
    val searchedShows: List<Show> = emptyList(),
    val favoriteShows : List<FavoriteShow> = emptyList(),
    val showDetail: Show? = null,
)
