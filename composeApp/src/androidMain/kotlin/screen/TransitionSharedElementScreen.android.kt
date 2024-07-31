@file:OptIn(ExperimentalSharedTransitionApi::class)

package screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import composemultiplatformpractice.composeapp.generated.resources.Res
import composemultiplatformpractice.composeapp.generated.resources.nature_1
import composemultiplatformpractice.composeapp.generated.resources.nature_2
import composemultiplatformpractice.composeapp.generated.resources.nature_3
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource


@Composable
actual fun TransitionSharedElementScreen(modifier: Modifier) {
    var showDetails by remember { mutableStateOf(false) }
    var selectedNature by remember { mutableStateOf<Nature?>(null) }

    SharedTransitionLayout {
        AnimatedContent(
            targetState = showDetails,
            label = "basic_transition"
        ) { targetState ->
            if (!targetState) {
                ListContent(
                    onNatureSelect = {
                        showDetails = true
                        selectedNature = it
                    },
                    animatedVisibilityScope = this@AnimatedContent,
                    sharedTransitionScope = this@SharedTransitionLayout,
                )
            } else {
                selectedNature?.let {
                    DetailsContent(
                        nature = it,
                        onBackClicked = { showDetails = false },
                        animatedVisibilityScope = this@AnimatedContent,
                        sharedTransitionScope = this@SharedTransitionLayout,
                    )
                }
            }
        }
    }
}

@Composable
fun ListContent(
    onNatureSelect: (Nature) -> Unit,
    animatedVisibilityScope: AnimatedContentScope,
    sharedTransitionScope: SharedTransitionScope,
) {
    val natures = remember {
        listOf(
            Nature(
                id = 1,
                title = "Nature #1",
                description = "A vibrant canvas painted with lush green forests, golden sunrises, and the tranquil depths of oceans. It's a symphony of life, where creatures great and small find harmony.",
                imageSrc = Res.drawable.nature_1
            ),
            Nature(
                id = 2,
                title = "Nature #2",
                description = "It provides the air we breathe, the water we drink, and the food we eat. A delicate balance of interconnected systems, it sustains all life on Earth.",
                imageSrc = Res.drawable.nature_2
            ),
            Nature(
                id = 3,
                title = "Nature #3",
                description = "From the towering mountains to the smallest insect, it invites us to explore, learn, and appreciate the beauty of our world.",
                imageSrc = Res.drawable.nature_3
            ),
        )
    }
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
    ) {
        items(natures) { nature ->
            with(sharedTransitionScope) {
                Column(
                    modifier = Modifier
                        .sharedBounds(
                            rememberSharedContentState(
                                key = NatureSharedElementKey(
                                    id = nature.id,
                                    type = NatureSharedElementType.Background,
                                )
                            ),
                            animatedVisibilityScope = animatedVisibilityScope,
                        )
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(8.dp)
                        .clip(shape = RoundedCornerShape(12.dp))
                        .border(
                            width = 1.dp,
                            color = Color.Gray.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(12.dp),
                        )
                        .clickable { onNatureSelect(nature) },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    with(sharedTransitionScope) {
                        Image(
                            painter = painterResource(nature.imageSrc), null,
                            modifier = Modifier
                                .sharedBounds(
                                    rememberSharedContentState(
                                        key = NatureSharedElementKey(
                                            id = nature.id,
                                            type = NatureSharedElementType.Image,
                                        )
                                    ),
                                    animatedVisibilityScope = animatedVisibilityScope,
                                    clipInOverlayDuringTransition = OverlayClip(
                                        RoundedCornerShape(
                                            topStart = 12.dp,
                                            topEnd = 12.dp,
                                        )
                                    ),
                                )
                        )
                    }
                    with(sharedTransitionScope) {
                        Text(
                            nature.title,
                            modifier = Modifier
                                .sharedBounds(
                                    rememberSharedContentState(
                                        key = NatureSharedElementKey(
                                            id = nature.id,
                                            type = NatureSharedElementType.Title,
                                        )
                                    ),
                                    animatedVisibilityScope = animatedVisibilityScope,
                                    enter = fadeIn(),
                                    exit = fadeOut(),
                                    resizeMode = SharedTransitionScope.ResizeMode.ScaleToBounds(),
                                )
                                .padding(bottom = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DetailsContent(
    nature: Nature,
    onBackClicked: () -> Unit,
    animatedVisibilityScope: AnimatedContentScope,
    sharedTransitionScope: SharedTransitionScope,
) {
    with(sharedTransitionScope) {
        Column(
            modifier = Modifier
                .sharedBounds(
                    rememberSharedContentState(
                        key = NatureSharedElementKey(
                            id = nature.id,
                            type = NatureSharedElementType.Background,
                        )
                    ),
                    animatedVisibilityScope = animatedVisibilityScope,
                )
                .fillMaxSize()
                .background(Color.LightGray),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            with(sharedTransitionScope) {
                Image(
                    painter = painterResource(nature.imageSrc), null,
                    modifier = Modifier
                        .sharedBounds(
                            rememberSharedContentState(
                                key = NatureSharedElementKey(
                                    id = nature.id,
                                    type = NatureSharedElementType.Image,
                                )
                            ),
                            animatedVisibilityScope = animatedVisibilityScope,
                        )
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop,
                )
            }
            with(sharedTransitionScope) {
                Text(
                    nature.title,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .sharedBounds(
                            rememberSharedContentState(
                                key = NatureSharedElementKey(
                                    id = nature.id,
                                    type = NatureSharedElementType.Title,
                                )
                            ),
                            animatedVisibilityScope = animatedVisibilityScope,
                            enter = fadeIn(),
                            exit = fadeOut(),
                            resizeMode = SharedTransitionScope.ResizeMode.ScaleToBounds(),
                        )
                        .padding(horizontal = 16.dp),
                )
            }
            Text(
                nature.description,
                modifier = Modifier.padding(horizontal = 16.dp),
            )
            Spacer(Modifier.weight(1f))
            IconButton(
                onClick = onBackClicked,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 16.dp, bottom = 16.dp),
            ) {
                Icon(Icons.AutoMirrored.Default.ArrowBack, null)
            }
        }
    }
}

data class Nature(
    val id: Int,
    val title: String,
    val description: String,
    val imageSrc: DrawableResource,
)

data class NatureSharedElementKey(
    val id: Int,
    val type: NatureSharedElementType,
)

enum class NatureSharedElementType {
    Image,
    Title,
    Background,
}