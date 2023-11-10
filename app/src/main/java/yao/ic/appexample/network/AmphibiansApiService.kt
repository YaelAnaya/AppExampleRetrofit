package yao.ic.appexample.network

import retrofit2.http.GET
import yao.ic.appexample.model.Amphibian

interface AmphibiansApiService {
    @GET(AMPHIBIANS_ENDPOINT)
    suspend fun getAmphibians(): List<Amphibian>
}