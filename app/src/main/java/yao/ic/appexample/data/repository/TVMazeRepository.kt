package yao.ic.appexample.data.repository

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import yao.ic.appexample.data.database.FavoriteShowDao
import yao.ic.appexample.data.database.entity.FavoriteShow
import yao.ic.appexample.data.model.Show
import yao.ic.appexample.network.TVMazeApiService
import javax.inject.Inject

class TVMazeRepository @Inject constructor(
    private val api: TVMazeApiService,
    private val favoriteShowDao: FavoriteShowDao
) {
    suspend fun searchShow(query: String) = api.searchShow(query).map { it.toShow() }
    suspend fun getShows(): Flow<List<Show>> = flow {
        emit(api.getShows())
    }

    suspend fun addFavoriteShow(show: FavoriteShow) = favoriteShowDao.addFavorite(show)

    suspend fun removeFavoriteShow(show: FavoriteShow) = favoriteShowDao.deleteFavorite(show)

    fun isFavorite(id: Int): Flow<Boolean> = favoriteShowDao.isFavorite(id)

    fun getFavoriteShows(): Flow<List<FavoriteShow>> = favoriteShowDao.getAllFavorites()

    suspend fun getShowDetail(id: Int) = api.getShowDetail(id)
}