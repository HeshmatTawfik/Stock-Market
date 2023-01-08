package com.heshmat.data.di

import com.heshmat.data.csv.CSVParser
import com.heshmat.data.csv.CompanyListingParser
import com.heshmat.data.csv.IntradayInfoParser
import com.heshmat.data.repository.StockRepositoryImpl
import com.heshmat.domain.model.company_info.IntradayInfo
import com.heshmat.domain.model.company_listing.CompanyListing
import com.heshmat.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCompanyListingParser(
        companyListingParser: CompanyListingParser
    ): CSVParser<CompanyListing>

    @Binds
    @Singleton
    abstract fun bindIntradayInfoParser(
        intradayInfoParser: IntradayInfoParser
    ): CSVParser<IntradayInfo>

    @Binds
    @Singleton
    abstract fun bindStockRepository(
        stockRepositoryImpl:StockRepositoryImpl
    ): StockRepository
}
