package com.heshmat.data.repository

import com.heshmat.data.local.StockDatabase
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
    val api: StockApi,
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

            if (shouldJustLoadFromCache){
                emit(Resource.Loading(false))
                return@flow
            }

            val remoteListing = try{
                val response = api.getListings()
                response.byteStream()
            }
            catch (e: IOException){
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))

            }
            catch (e:HttpRetryException){
                emit(Resource.Error("Couldn't load data"))
                e.printStackTrace()

            }
        }
    }
}