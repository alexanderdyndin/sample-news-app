package com.example.feature.ui

import androidx.lifecycle.viewModelScope
import com.example.domain.model.News
import com.example.domain.repository.NewsRepository
import com.example.domain.utils.doOnError
import com.example.domain.utils.doOnSuccess
import com.example.feature.base.mvi.BaseViewModel
import com.example.feature.base.utils.isNotNull
import com.example.feature.base.utils.isNull
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NewsViewModel(private val newsRepository: NewsRepository): BaseViewModel<NewsState>(NewsState()) {

    init {
        viewModelScope.launch {
            setLoading(true)
            newsRepository.getLocalNews()?.let {
                delay(800) //чтобы загрузка не шла мгновенно
                setNews(it, true)
                setLoading(false)
            } ?: run {
                refreshData()
            }
        }
    }

    private suspend fun refreshData() {
        setLoading(true)
        setError(null)
        newsRepository.getNews()
            .doOnSuccess {
                setNews(it, false)
            }
            .doOnError {
                setError(it)
            }
        setLoading(false)
        setRefreshing(false)
    }

    private fun setLoading(isLoading: Boolean) {
        mutateState {
            state = state.copy(isLoading = isLoading)
        }
    }

    private fun setRefreshing(isRefreshing: Boolean) {
        mutateState {
            state = state.copy(isRefreshing = isRefreshing)
        }
    }

    private fun setNews(news: List<News>, isLocal: Boolean) {
        mutateState {
            state = state.copy(data = news, isDataLocal = isLocal)
        }
    }

    private fun setError(throwable: Throwable?) {
        mutateState {
            state = state.copy(error = throwable)
        }
    }

    fun refresh(withAnimation: Boolean) {
        viewModelScope.launch {
            if (withAnimation) {
                setRefreshing(true)
            }
            refreshData()
        }
    }

}

data class NewsState(
    val data: List<News>? = null,
    val isDataLocal: Boolean = true,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: Throwable? = null,
) {
    val isLoadingWithoutContent = data.isNull() && isLoading && !isRefreshing
}