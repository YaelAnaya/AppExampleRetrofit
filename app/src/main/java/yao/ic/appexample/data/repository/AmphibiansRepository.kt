package yao.ic.appexample.data.repository

import yao.ic.appexample.network.AmphibiansApiService
import javax.inject.Inject

class AmphibiansRepository @Inject constructor(
    private val api: AmphibiansApiService
) {
    suspend fun getAmphibians() = api.getAmphibians()
}