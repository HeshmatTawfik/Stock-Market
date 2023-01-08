package com.heshmat.domain.repository

import com.heshmat.domain.model.company_info.CompanyInfo
import com.heshmat.domain.model.company_info.IntradayInfo
import com.heshmat.domain.model.company_listing.CompanyListing
import com.heshmat.util.Resource
import kotlinx.coroutines.flow.Flow


interface StockRepository {

    suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>>

    suspend fun getIntradayInfo(
        symbol: String
    ): Resource<List<IntradayInfo>>

    suspend fun getCompanyInfo(
        symbol: String,
    ): Resource<CompanyInfo>
}
