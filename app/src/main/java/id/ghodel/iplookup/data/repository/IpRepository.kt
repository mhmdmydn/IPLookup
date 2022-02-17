package id.ghodel.iplookup.data.repository

import id.ghodel.iplookup.data.model.IpResponse
import id.ghodel.iplookup.data.remote.ApiService
import retrofit2.Response
import javax.inject.Inject

class IpRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getLocalIp(): Response<IpResponse> {
        return apiService.getLocalIp()
    }

    suspend fun searchIp(ip: String): Response<IpResponse> {
        return apiService.searchIp(ip)
    }
}