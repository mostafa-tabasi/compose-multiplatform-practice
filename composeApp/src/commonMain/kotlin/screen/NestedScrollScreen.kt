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
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import composemultiplatformpractice.composeapp.generated.resources.Res
import composemultiplatformpractice.composeapp.generated.resources.nature_1
import org.jetbrains.compose.resources.painterResource

@Composable
fun NestedScrollScreen(modifier: Modifier = Modifier) {
    val fabSize = remember { 50.dp }
    val minImageSize = remember { 75.dp }
    val maxImageSize = remember { 200.dp }
    val padding = remember { 16.dp }
    var currentImageSize by remember { mutableStateOf(maxImageSize) }
    var boxWidth by remember { mutableStateOf(0) }
    var boxHeight by remember { mutableStateOf(0) }
    var currentImageSizeDelta by remember { mutableStateOf(1f) }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {

                val delta = available.y.toInt()
                val newImageSize = currentImageSize + delta.dp
                val previousImageSize = currentImageSize
                currentImageSize = newImageSize.coerceIn(minImageSize, maxImageSize)

                currentImageSizeDelta =
                    (currentImageSize - minImageSize) / (maxImageSize - minImageSize)

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
            .onSizeChanged {
                boxWidth = it.width
                boxHeight = it.height
            }
            .nestedScroll(nestedScrollConnection),
    ) {
        Image(
            painter = painterResource(Res.drawable.nature_1),
            null,
            modifier = Modifier
                .padding(padding)
                .size(currentImageSize)
                .offset {
                    IntOffset(
                        ((boxWidth / 2) - (currentImageSize / 2).toPx() - padding.toPx()).toInt(),
                        0
                    )
                }
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop,
        )
        LazyColumn(
            modifier = Modifier
                .offset {
                    IntOffset(0, (currentImageSize + padding.times(2)).toPx().toInt())
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
        FloatingActionButton(
            modifier = Modifier
                .padding(padding)
                .offset {
                    IntOffset(
                        // used lerp to interpolate fab offset (x) value,
                        // based on the start and stop value and the current image size delta
                        lerp(
                            start = IntOffset(
                                ((boxWidth) - fabSize.toPx() - padding.times(2).toPx()).toInt(), 0
                            ),
                            stop = IntOffset(boxWidth, 0),
                            fraction = currentImageSizeDelta,
                        ).x,
                        ((boxHeight) - fabSize.toPx() - padding.times(2).toPx()).toInt(),
                    )
                },
            onClick = {},
            backgroundColor = Color.Gray,
        ) {
            Icon(Icons.Default.Add, null)
        }
    }
}