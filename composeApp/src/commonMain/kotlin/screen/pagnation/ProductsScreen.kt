package screen.pagnation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import data.remote.ProductsApiService
import data.remote.util.HttpClientEngineFactory
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.serialization.json.Json

@Composable
fun ProductsScreen(modifier: Modifier = Modifier) {
    val viewModel = viewModel(
        initializer = {
            ProductsViewModel(
                apiService = ProductsApiService(
                    httpClient = HttpClient(
                        engine = HttpClientEngineFactory().create()
                    ) {
                        install(Logging) {
                            logger = Logger.SIMPLE
                            level = LogLevel.ALL
                        }
                        install(ContentNegotiation) {
                            json(
                                json = Json { ignoreUnknownKeys = true }
                            )
                        }
                    }
                )
            )
        }
    )
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) { contentPadding ->
        val lazyListState = rememberLazyListState()

        LaunchedEffect(state.products) {
            // Listen to the changes of list scrolling
            snapshotFlow {
                lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
            }
                .distinctUntilChanged()
                .collect { lastVisibleIndex ->
                    if(lastVisibleIndex == state.products.lastIndex) {
                        viewModel.loadNextItems()
                    }
                }
        }

        LazyColumn(
            state = lazyListState,
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = contentPadding
        ) {
            items(state.products) { product ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = product.title,
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "$ ${product.price}"
                    )
                }
            }
            if(state.isLoadingMore) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}