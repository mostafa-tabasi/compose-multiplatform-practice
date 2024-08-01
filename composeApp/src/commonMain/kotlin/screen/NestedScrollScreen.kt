package screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import composemultiplatformpractice.composeapp.generated.resources.Res
import composemultiplatformpractice.composeapp.generated.resources.nature_1
import org.jetbrains.compose.resources.painterResource

@Composable
fun NestedScrollScreen(modifier: Modifier = Modifier) {
    val minImageSize = remember { 75.dp }
    val maxImageSize = remember { 200.dp }
    val imagePadding = remember { 16.dp }
    var currentImageSize by remember { mutableStateOf(maxImageSize) }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {

                val delta = available.y.toInt()
                val newImageSize = currentImageSize + delta.dp
                val previousImageSize = currentImageSize
                currentImageSize = newImageSize.coerceIn(minImageSize, maxImageSize)

                // the amount that we consumed from input scroll
                // so that this amount won't pass to list composable to scroll
                val consumed = currentImageSize - previousImageSize

                return Offset(0f, consumed.value)
            }
        }
    }

    val items = remember {
        (1..100).map { "Item #$it" }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection),
    ) {
        Image(
            painter = painterResource(Res.drawable.nature_1),
            null,
            modifier = Modifier
                .padding(imagePadding)
                .size(currentImageSize)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop,
        )
        LazyColumn(
            modifier = Modifier
                .offset {
                    IntOffset(0, (currentImageSize + imagePadding.times(2)).toPx().toInt())
                }
                .fillMaxWidth()
                .background(Color.LightGray)
        ) {
            items(items) {
                Text(
                    it,
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 12.dp)
                )
            }
        }
    }
}