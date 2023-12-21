package yao.ic.appexample.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import yao.ic.appexample.data.model.Show
import yao.ic.appexample.data.model.ShowSearch

interface TVMazeApiService {
    @GET(SEARCH_SHOW_ENDPOINT)
    suspend fun searchShow(
        @Query("q") query: String
    ): List<ShowSearch>

    @GET(SHOW_DETAIL_ENDPOINT)
    suspend fun getShowDetail(
        @Path("id") id: Int
    ): Show

    @GET(SHOWS_ENDPOINT)
    suspend fun getShows(): List<Show>
}