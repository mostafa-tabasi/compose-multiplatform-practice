package screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import data.remote.InsultCensorService
import data.remote.util.NetworkError
import data.remote.util.onError
import data.remote.util.onSuccess
import kotlinx.coroutines.launch


@Composable
fun NetworkCallSampleScreen(censorService: InsultCensorService) {
    var censoredWord by remember { mutableStateOf<String?>(null) }
    var uncensoredWord by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<NetworkError?>(null) }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            value = uncensoredWord,
            onValueChange = { uncensoredWord = it }
        )
        Button(onClick = {
            if (!loading) {
                // not a good practice, just for exercising purposes
                scope.launch {
                    loading = true
                    error = null

                    censorService.censorWord(uncensoredWord)
                        .onSuccess { censoredWord = it }
                        .onError { error = it }
                    loading = false
                }
            }
        }) {
            if (loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(12.dp),
                    strokeWidth = 1.dp,
                    color = Color.White,
                )
            } else {
                Text("Censor!")
            }
        }
        censoredWord?.let { Text(it) }
        error?.let { Text(it.name, color = Color.Red) }
    }
}