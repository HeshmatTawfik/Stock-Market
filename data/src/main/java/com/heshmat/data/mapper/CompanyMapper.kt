package com.heshmat.data.mapper

import com.heshmat.data.local.CompanyListingEntity
import com.heshmat.domain.model.CompanyListing

fun CompanyListingEntity.toCompanyListing(): CompanyListing {
    return CompanyListing(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}

fun List<CompanyListingEntity>.toCompanyListings() = map { it.toCompanyListing() }

fun CompanyListing.toCompanyListingEntity(): CompanyListingEntity {
    return CompanyListingEntity(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}

fun List<CompanyListing>.toCompanyListingEntities() = map{it.toCompanyListingEntity()}