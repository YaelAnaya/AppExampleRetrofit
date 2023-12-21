package yao.ic.appexample.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import yao.ic.appexample.data.state.ShowState
import yao.ic.appexample.data.model.Show
import yao.ic.appexample.data.state.NetworkState
import yao.ic.appexample.ui.ErrorScreen
import yao.ic.appexample.ui.LoadingScreen
import yao.ic.appexample.ui.screens.show_detail.ShowDetailScreen
import yao.ic.appexample.ui.screens.show_list.ShowListScreen

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavItem.Shows.route,
    networkState: NetworkState,
    retryAction: () -> Unit,
    searchAction: (String) -> Unit,
    onDetail: (Int) -> Unit = {},
){
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ){
        composable(
            navItem = NavItem.Shows,
            networkState = networkState,
            onRetry = retryAction
        ){ _, uiState ->
            ShowListScreen(
                modifier = modifier,
                showState = uiState,
                onCardClick = {
                    navController.navigate(NavItem.ShowDetail.createRoute(it.toString()))
                }
            )
        }

        composable(
            navItem = NavItem.Favorites,
            networkState = networkState,
            onRetry = retryAction
        ){ _, uiState ->
            Text(text = "Favorites")
        }

        composable(
            navItem = NavItem.ShowDetail,
            networkState = networkState,
            onRetry = retryAction
        ){ navEntry, uiState ->
            val id = navEntry.findArg<Int>(NavArg.ShowID)

            LaunchedEffect(key1 = Unit) {
                onDetail(id)
            }

            ShowDetailScreen(show = uiState.showDetail ?: Show())
        }
    }
}

private inline fun NavGraphBuilder.composable(
    navItem: NavItem,
    networkState: NetworkState,
    noinline onRetry: () -> Unit,
    crossinline content: @Composable (NavBackStackEntry, ShowState) -> Unit
) {
    when (networkState) {
        is NetworkState.Loading -> composable(navItem.route, navItem.args){
            LoadingScreen()
        }
        is NetworkState.Success -> composable(navItem.route, navItem.args){
            content(it, networkState.showState)
        }
        is NetworkState.Error -> composable(navItem.route, navItem.args){
            ErrorScreen(
                errorMessage = networkState.message,
                onRetry = onRetry
            )
        }
    }
}

private inline fun <reified T> NavBackStackEntry.findArg(arg: NavArg) : T {
    val value = arguments?.get(arg.key)
    requireNotNull(value) { "Missing argument ${arg.key}" }
    return value as T
}