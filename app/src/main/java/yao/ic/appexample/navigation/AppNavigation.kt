package yao.ic.appexample.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import yao.ic.appexample.data.repository.AmphibianState
import yao.ic.appexample.network.NetworkState
import yao.ic.appexample.ui.ErrorScreen
import yao.ic.appexample.ui.LoadingScreen
import yao.ic.appexample.ui.screens.amphibian_detail.AmphibianDetailScreen
import yao.ic.appexample.ui.screens.amphibians.AmphibiansScreen

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavItem.Amphibians.route,
    networkState: NetworkState,
    retryAction: () -> Unit,
){

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ){
        composable(NavItem.Amphibians){
            when (networkState) {
                is NetworkState.Loading -> LoadingScreen(
                    modifier = modifier,
                    onRetry = retryAction
                )
                is NetworkState.Success -> AmphibiansScreen(
                    modifier = modifier,
                    amphibianState = networkState.amphibianState,
                )
                is NetworkState.Error -> ErrorScreen(
                    modifier = modifier,
                    onRetry = retryAction
                )
            }
        }
    }

}

private fun NavGraphBuilder.composable(
    navItem: NavItem,
    content: @Composable (NavBackStackEntry) -> Unit
) {
    composable(navItem.route, navItem.args) {
        content(it)
    }
}

private inline fun <reified T> NavBackStackEntry.findArg(arg: NavArg) : T {
    val value = arguments?.get(arg.key)
    requireNotNull(value) { "Missing argument ${arg.key}" }
    return value as T
}