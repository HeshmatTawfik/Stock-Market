package com.heshmat.data.csv

import com.heshmat.domain.model.company_listing.CompanyListing
import com.opencsv.CSVReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CompanyListingParser @Inject constructor() : CSVParser<CompanyListing> {
    override suspend fun parse(steam: InputStream): List<CompanyListing> {
        val csvReader = CSVReader(InputStreamReader(steam))
        return withContext(Dispatchers.IO) {
            csvReader.readAll().drop(1).mapNotNull { line ->
                val symbol = line.getOrNull(0)
                val name = line.getOrNull(1)
                val exchange = line.getOrNull(2)
                CompanyListing(
                    name = name ?: return@mapNotNull null,
                    symbol = symbol ?: return@mapNotNull null,
                    exchange = exchange ?: return@mapNotNull null,
                )
            }.also {
                csvReader.close()
            }
        }
    }
}