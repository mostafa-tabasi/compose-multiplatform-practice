package screen

import Platform
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerScreen(platform: Platform, modifier: Modifier = Modifier) {
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = {
        10
    })

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 75.dp),
            modifier = Modifier.weight(1f),
        ) { page ->
            println(
                "pagerState.currentPage: ${pagerState.currentPage} / page: $page / currentPageOffsetFraction: ${pagerState.currentPageOffsetFraction}"
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .graphicsLayer {
                        val pageOffset = (
                                (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
                                ).absoluteValue
                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                        scaleX = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 0.25f)
                        )
                        scaleY = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 0.25f)
                        )
                    }
                    .background(if (page % 2 == 0) Color.Blue else Color.Red),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "Page: $page",
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        if (platform.isDesktop) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(
                    32.dp,
                    alignment = Alignment.CenterHorizontally,
                ),
            ) {
                IconButton(onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(
                            page = (pagerState.currentPage - 1).coerceIn(0, 10)
                        )
                    }
                }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                }
                IconButton(onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(
                            page = (pagerState.currentPage + 1).coerceIn(0, 10)
                        )
                    }
                }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, null)
                }
            }
        }
    }
}