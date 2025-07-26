package screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ClipAndMaskScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Avatars()
        ItemsList()
    }
}

@Composable
private fun Avatars(modifier: Modifier = Modifier) {
    val avatarSize = remember { 50.dp }
    val avatars = remember {
        mutableListOf(
            Avatar("A"),
            Avatar("B"),
            Avatar("C"),
            Avatar("D"),
        )
    }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                Brush.horizontalGradient(
                    listOf(
                        parseColor("#FB8C00"),
                        parseColor("#558B2F"),
                        parseColor("#558B2F"),
                        parseColor("#455A64"),
                    )
                )
            )
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen),
        ) {
            var offset = 0.dp
            for (avatar in avatars) {
                Avatar(
                    avatar, 10f,
                    Modifier
                        .size(avatarSize)
                        .offset(offset)
                )
                offset += avatarSize * 2 / 3
            }
        }
    }
}

@Composable
private fun Avatar(
    data: Avatar,
    strokeWidth: Float,
    modifier: Modifier = Modifier,
) {
    val stroke = remember(strokeWidth) {
        Stroke(strokeWidth)
    }
    Box(
        modifier = modifier
            .drawWithContent {
                drawContent()
                drawCircle(
                    color = Color.Black,
                    style = stroke,
                    blendMode = BlendMode.Clear,
                )
            }
            .clip(CircleShape)
            .clickable { }
            .background(parseColor("#455A64")),
        contentAlignment = Alignment.Center,
    ) {
        Icon(Icons.Default.Person, null, tint = Color.White)
    }
}

@Composable
private fun ItemsList(modifier: Modifier = Modifier) {
    val items = remember {
        (1..30).map { Item("item number $it") }
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        parseColor("#795548"),
                        parseColor("#455A64"),
                    )
                )
            )
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
                .drawWithContent {
                    drawContent()
                    drawRect(
                        brush = Brush.verticalGradient(
                            listOf(Color.Black, Color.Black, Color.Transparent)
                        ),
                        blendMode = BlendMode.DstIn,
                    )
                }
        ) {
            items(items) {
                Text(
                    it.title,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    ),
                    modifier = Modifier.padding(8.dp),
                )
            }
        }
    }
}

private fun parseColor(hex: String): Color {
    // Ensure the hex string starts with '#'
    val hexColor = if (hex.startsWith("#")) hex else "#$hex"

    // Parse the hex string to an integer
    val colorInt = hexColor.toInt(16)

    return when (hex.length) {
        6 -> Color(colorInt or 0xFF000000.toInt()) // Add alpha channel if absent
        8 -> Color(colorInt) // Full ARGB color
        else -> throw IllegalArgumentException("Invalid hex color string: $hex")
    }
}

data class Avatar(
    val name: String,
)

data class Item(
    val title: String,
)