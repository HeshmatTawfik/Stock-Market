package com.heshmat.data.repository

import com.heshmat.data.csv.CSVParser
import com.heshmat.data.local.StockDatabase
import com.heshmat.data.mapper.toCompanyListingEntities
import com.heshmat.data.mapper.toCompanyListings
import com.heshmat.data.remote.StockApi
import com.heshmat.domain.model.CompanyListing
import com.heshmat.domain.repository.StockRepository
import com.heshmat.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.net.HttpRetryException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    private val api: StockApi,
    private val companyListingParser: CSVParser<CompanyListing>,
    db: StockDatabase
) : StockRepository {

    private val dao = db.dao

    override suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> {
        return flow {

            emit(Resource.Loading(true))

            val localListing = dao.searchCompanyListing(query)

            emit(
                Resource.Success(
                    data = localListing.toCompanyListings()
                )
            )

            val isDbEmpty = localListing.isEmpty() && query.isBlank()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote

            if (shouldJustLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }

            val remoteListing = try {
                val response = api.getListings()
                companyListingParser.parse(response.byteStream())
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null

            } catch (e: HttpRetryException) {
                emit(Resource.Error("Couldn't load data"))
                e.printStackTrace()
                null
            }

            remoteListing?.let { listings ->
                dao.clearCompanyListings()
                dao.insertCompanyListings(listings.toCompanyListingEntities())
                emit(
                    Resource.Success(
                        data = dao
                            .searchCompanyListing("")
                            .toCompanyListings()
                    )
                )
                emit(Resource.Loading(false))
            }
        }
    }
}