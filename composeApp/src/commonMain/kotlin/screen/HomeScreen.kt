package screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import composemultiplatformpractice.composeapp.generated.resources.Res
import composemultiplatformpractice.composeapp.generated.resources.compose_multiplatform
import composemultiplatformpractice.composeapp.generated.resources.platform
import getPlatform
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun HomeScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(painterResource(Res.drawable.compose_multiplatform), null)
        Text(stringResource(Res.string.platform, getPlatform().name))

        Button(
            onClick = { navController.navigate("battery") },
        ) { Text("Battery Level") }

        Button(
            onClick = { navController.navigate("network_call") },
        ) { Text("Network Call") }

        Button(
            onClick = { navController.navigate("counter") },
        ) { Text("Counter") }

        Button(
            onClick = { navController.navigate("username") },
        ) { Text("Username") }
    }
}