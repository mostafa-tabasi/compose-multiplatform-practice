package data.remote

import data.remote.dto.ProductResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope.coroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive

class ProductsApiService(
    private val httpClient: HttpClient
) {

    @OptIn(DelicateCoroutinesApi::class)
    suspend fun getProducts(
        page: Int = 0,
        pageSize: Int = 10
    ): Result<ProductResponseDto> {
        delay(2000L)
        val body = try {
            val response = httpClient.get(
                urlString = "https://dummyjson.com/products?select=title,price"
            ) {
                contentType(ContentType.Application.Json)
                parameter("limit", pageSize)
                parameter("skip", page * pageSize)
            }

            response.body<ProductResponseDto>()
        } catch (e: Exception) {
            coroutineContext.ensureActive()
            return Result.failure(e)
        }

        return Result.success(body)
    }
}