package com.vhenri.stock_list_app.network

import com.vhenri.stock_list_app.models.CompanyProfile
import com.vhenri.stock_list_app.models.StockApiException
import com.github.michaelbull.result.Result
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url
import javax.inject.Inject

interface FinnhubApi {
    @GET
    suspend fun getCompanyProfileBySymbol(
        @Url url: String,
        @Query("symbol") symbol: String,
        @Query("token") token: String
    ) : Response<CompanyProfile>

}

class FinnhubApiClient @Inject constructor(
    private val api: FinnhubApi
): BaseApiClient() {

    companion object {
        const val BASE_FINNHUB_URL = "https://finnhub.io/api/v1"
        const val FINNHUB_API_KEY = "cde24biad3i4an260tcgcde24biad3i4an260td0"
    }

    suspend fun getCompanyProfileBySymbol(symbol: String): Result<CompanyProfile?, StockApiException> {
        return execute {
            api.getCompanyProfileBySymbol(
                url = "$BASE_FINNHUB_URL/stock/profile2",
                symbol = symbol,
                token = FINNHUB_API_KEY
            )
        }
    }
}