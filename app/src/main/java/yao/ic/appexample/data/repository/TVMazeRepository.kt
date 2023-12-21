package yao.ic.appexample.data.repository

import yao.ic.appexample.data.database.FavoriteShowDao
import yao.ic.appexample.network.TVMazeApiService
import javax.inject.Inject

class TVMazeRepository @Inject constructor(
    private val api: TVMazeApiService,
    private val favoriteShowDao: FavoriteShowDao
) {
    suspend fun searchShow(query: String) = api.searchShow(query).map { it.toShow() }
    suspend fun getShows() = api.getShows()

    suspend fun getShowDetail(id: Int) = api.getShowDetail(id)
}