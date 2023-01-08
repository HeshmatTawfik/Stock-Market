package com.heshmat.data.mapper

import com.heshmat.data.local.CompanyListingEntity
import com.heshmat.data.remote.dto.company_info.CompanyInfoDto
import com.heshmat.domain.model.company_info.CompanyInfo
import com.heshmat.domain.model.company_listing.CompanyListing

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

fun List<CompanyListing>.toCompanyListingEntities() = map { it.toCompanyListingEntity() }

fun CompanyInfoDto.toCompanyInfo(): CompanyInfo {
    return CompanyInfo(
        symbol = symbol ?: "",
        description = description ?: "",
        name = name ?: "",
        country = country ?: "",
        industry = industry ?: ""

    )
}