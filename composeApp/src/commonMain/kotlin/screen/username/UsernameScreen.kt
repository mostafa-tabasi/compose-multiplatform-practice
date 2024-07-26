package screen.username

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun UsernameScreen(modifier: Modifier = Modifier) {
    val viewModel = koinViewModel<UsernameViewModel>()
    val username by viewModel.username.collectAsState()
    var typingUsername by remember { mutableStateOf("") }

    DisposableEffect(LocalLifecycleOwner.current) {
        onDispose {
            viewModel.onDispose()
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.CenterVertically),
    ) {
        Text("Username: $username", fontSize = 24.sp)
        TextField(
            value = typingUsername,
            onValueChange = { typingUsername = it },
            placeholder = { Text("Enter Username") },
        )
        Button(
            onClick = { viewModel.saveUsername(typingUsername) },
            content = { Text("Save") },
        )
    }
}
