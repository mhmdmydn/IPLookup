package id.ghodel.iplookup.data.remote

import id.ghodel.iplookup.data.model.IpResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("/json/")
    suspend fun getLocalIp() : Response<IpResponse>

    @GET("/{ip}/json")
    suspend fun searchIp(
        @Path("ip") ip: String
    ) : Response<IpResponse>
}