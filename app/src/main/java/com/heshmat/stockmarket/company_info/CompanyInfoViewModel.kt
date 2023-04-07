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
import timber.log.Timber
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
            Timber.d("get company info $symbol")
            if (symbol.isNullOrEmpty()) {
                Timber.e("symbol is null or empty")
                state = state.copy(error = "Something went wrong ...")
                return@launch
            }
            Timber.d("company info state now is loading")
            state = state.copy(isLoading = true)
            val companyInfoResult = async { repository.getCompanyInfo(symbol) }
            val intradayInfo = async { repository.getIntradayInfo(symbol) }

            updateState(companyInfoResult.await()) {
                Timber.d("get company info $it")
                state = state.copy(
                    company = it, error = null
                )
            }

            updateState(intradayInfo.await()) {
                Timber.d("get intraday info $it")
                state = state.copy(
                    intradayInfoList = it, error = null
                )
            }
            Timber.d("company info state loading is false")
            state = state.copy(isLoading = false)

        }
    }

    private fun <T> updateState(result: Resource<T>, onSuccess: (T) -> Unit) {
        when (result) {
            is Resource.Success -> {
                result.data?.let { onSuccess(it) }
            }

            is Resource.Error -> {
                Timber.e("update state error $result")
                state = state.copy(
                    error = result.message
                )
            }

            else -> {
                Timber.e("This update state is either success or error, " +
                        "if this log appears it means something is wrong")
            }
        }
    }
}
