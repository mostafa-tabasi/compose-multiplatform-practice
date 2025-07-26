package screen.pagnation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.remote.ProductsApiService
import data.remote.dto.ProductDto
import data.remote.dto.ProductResponseDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import util.Paginator

data class ProductsState(
    val products: List<ProductDto> = emptyList(),
    val isLoadingMore: Boolean = false,
    val error: String? = null
)

class ProductsViewModel(
    private val apiService: ProductsApiService
): ViewModel() {

    private val _state = MutableStateFlow(ProductsState())
    val state = _state.asStateFlow()

    private val pageSize = 10
    private val paginator = Paginator<Int, ProductResponseDto>(
        initialKey = 0,
        onLoadUpdated = { isLoading ->
            _state.update { it.copy(
                isLoadingMore = isLoading
            ) }
        },
        onRequest = { currentPage ->
            apiService.getProducts(
                page = currentPage,
                pageSize = pageSize
            )
        },
        getNextKey = { currentPage, _ ->
            currentPage + 1
        },
        onError = { throwable ->
            _state.update { it.copy(
                error = throwable?.message
            ) }
        },
        onSuccess = { productsResponse, nextPage ->
            _state.update { it.copy(
                products = it.products + productsResponse.products,
                error = null
            ) }
        },
        endReached = { currentPage, response ->
            (currentPage * pageSize) >= response.total
        }
    )

    init {
        loadNextItems()
    }

    fun loadNextItems() {
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }
}