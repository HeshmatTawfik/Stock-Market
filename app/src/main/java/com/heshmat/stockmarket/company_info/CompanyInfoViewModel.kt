package com.heshmat.stockmarket.company_info

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heshmat.domain.repository.StockRepository
import com.heshmat.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyInfoViewModel @Inject constructor(
    private val savedStatedHandle: SavedStateHandle, private val repository: StockRepository
) : ViewModel() {
    var state by mutableStateOf(CompanyInfoState())

    init {
        getCompanyInfoState()
    }

    fun getCompanyInfoState() {
        viewModelScope.launch {
            val symbol = savedStatedHandle.get<String>("symbol")
            if (symbol.isNullOrEmpty()) {
                state = state.copy(error = "Something went wrong ...")
                return@launch
            }
            state = state.copy(isLoading = true)
            val companyInfoResult = async { repository.getCompanyInfo(symbol) }
            val intradayInfo = async { repository.getIntradayInfo(symbol) }

            updateState(companyInfoResult.await()) {
                state = state.copy(
                    company = it, error = null
                )
            }

            updateState(intradayInfo.await()) {
                state = state.copy(
                    intradayInfoList = it, error = null
                )
            }
            state = state.copy(isLoading = false)

        }
    }

    private fun <T> updateState(result: Resource<T>, onSuccess: (T) -> Unit) {
        when (result) {
            is Resource.Success -> {
                result.data?.let { onSuccess(it) }
            }

            is Resource.Error -> {
                state = state.copy(
                    error = result.message
                )
            }

            else -> Unit
        }
    }
}
