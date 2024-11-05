package de.cketti.reproduce.navigationcrash

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.fragment.compose.AndroidFragment
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import de.cketti.reproduce.navigationcrash.ui.theme.NavigationCrashTheme

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavigationCrashTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                    ) {
                        MainNavigation()
                    }
                }
            }
        }
    }
}

@Composable
private fun MainNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "start",
    ) {
        composable(route = "start") {
            StartScreen(
                onButtonClicked = {
                    navController.navigate("fragment")
                }
            )
        }
        composable(route = "fragment") {
            FragmentScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}

@Composable
fun StartScreen(onButtonClicked: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Text("Start")

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onButtonClicked) {
            Text("Next")
        }
    }
}

@Composable
fun FragmentScreen(onBack: (Bundle) -> Unit) {
    val activity = LocalContext.current as FragmentActivity
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(key1 = null) {
        activity.supportFragmentManager.setFragmentResultListener(
            "fragmentResult",
            lifecycleOwner,
        ) { _, result ->
            println("Fragment result")
            onBack(result)
        }

        onDispose {
            activity.supportFragmentManager.clearFragmentResultListener("fragmentResult")
        }
    }

    AndroidFragment<MyFragment>(modifier = Modifier.fillMaxSize())
}
