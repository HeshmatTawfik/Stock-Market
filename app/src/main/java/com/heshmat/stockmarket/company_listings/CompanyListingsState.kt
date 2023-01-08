package com.heshmat.stockmarket.company_listings

import com.heshmat.domain.model.company_listing.CompanyListing

data class CompanyListingsState(
    val companies: List<CompanyListing> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val searchQuery: String = ""
)
