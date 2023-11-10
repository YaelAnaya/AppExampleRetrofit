package yao.ic.appexample.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import yao.ic.appexample.navigation.AppNavigation
import yao.ic.appexample.navigation.NavItem
import yao.ic.appexample.network.NetworkState
import yao.ic.appexample.ui.screens.amphibians.AmphibiansViewModel
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AmphibiansApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),

){
    val amphibiansViewModel: AmphibiansViewModel = hiltViewModel()
    Scaffold(
        topBar = {
            TopAppBar(
                navBackStackEntry = navController.currentBackStackEntryAsState().value,
                onBack = { navController.popBackStack() },
                onReload = { amphibiansViewModel.getAmphibians() }
            )
        }
    ) { innerPadding ->
        AppNavigation(
            modifier = modifier.padding(innerPadding),
            navController = navController,
            networkState = amphibiansViewModel.state.collectAsState().value,
            retryAction = { amphibiansViewModel.getAmphibians() }
        )

    }

}

@Composable
fun ErrorScreen(modifier: Modifier, onRetry: () -> Unit) {
    Box(
        modifier = modifier,
        contentAlignment =  Alignment.Center
    ) {
        Text(
            text = "Error",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.SemiBold
            )
        )
        Button(
            onClick = onRetry,
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Text(text = "Retry")
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier, onRetry: () -> Unit) {
    Box(
        modifier = modifier,
        contentAlignment =  Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    navBackStackEntry: NavBackStackEntry? = null,
    onBack: () -> Unit = {},
    onReload: () -> Unit = {},
){
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = navBackStackEntry?.getTitle() ?: "Not title",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        navigationIcon = {
            if (navBackStackEntry?.destination?.route != NavItem.Amphibians.route) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        },
        actions = {
            IconButton(onClick = onReload) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Reload"
                )
            }
        },
    )
}

private fun NavBackStackEntry.getTitle() : String? {
    return destination.route?.capitalize(locale = Locale.ENGLISH)
}