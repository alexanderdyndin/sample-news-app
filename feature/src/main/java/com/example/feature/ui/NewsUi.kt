package com.example.feature.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.domain.model.News
import com.example.feature.base.utils.ClickListener
import com.example.feature.base.utils.Typed1Listener
import com.example.feature.base.utils.isNotNull
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

@Composable
fun NewsUI(
    state: NewsState,
    refreshListener: Typed1Listener<Boolean>
) {
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = state.isRefreshing),
        onRefresh = { refreshListener.invoke(true) },
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.background)
    ) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            item {
                AppBar()
            }
            when {
                state.isLoadingWithoutContent -> {
                    item {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                    }
                }
                state.error.isNotNull() -> {
                    item {
                        ShowError(state.error!!) { refreshListener.invoke(false) }
                    }
                }
                state.data.isNotNull() -> {
                    items(
                        count = state.data!!.size,
                        key = { state.data[it].id }
                    ) {
                        NewsItem(state.data[it])
                    }
                }
            }
        }
    }
}

@Composable
fun AppBar() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(bottom = 15.dp, top = 5.dp).padding(horizontal = 10.dp)
    ) {
        Text(
            text = "Новости",
            style = MaterialTheme.typography.h4,
        )
    }
}

@Composable
fun ShowError(error: Throwable, refreshListener: ClickListener) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        val text = when (error) {
            is SocketTimeoutException -> {
                "Превышено время ожидания"
            }
            is UnknownHostException -> {
                "Хост не найден"
            }
            is IOException -> {
                "Ошибка сети"
            }
            else -> "Неизвестная ошибка: ${error.localizedMessage}"
        }
        Text(text = text, style = MaterialTheme.typography.body1)
        Button(onClick = refreshListener) {
            Text(text = "Обновить")
        }
    }
}

@Composable
fun NewsItem(item: News) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 5.dp)) {
        Column(modifier = Modifier.padding(horizontal = 10.dp)) {
            Text(text = item.name, style = MaterialTheme.typography.h5)
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = item.description, style = MaterialTheme.typography.body1)
        }
    }

}