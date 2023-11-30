package yao.ic.appexample.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddHome
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import yao.ic.appexample.navigation.AppNavigation
import yao.ic.appexample.navigation.BOTTOM_NAV_ITEMS
import yao.ic.appexample.navigation.NavItem
import yao.ic.appexample.ui.screens.amphibians.TVMazeViewModel
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TVMazeApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),

){
    val tvMazeViewModel: TVMazeViewModel = hiltViewModel()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val scrollBehavior = when( currentDestination?.route){
        NavItem.Shows.route -> TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
        else -> TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                navBackStackEntry = navController.currentBackStackEntryAsState().value,
                onBack = { navController.popBackStack(); tvMazeViewModel.getShows() },
                onReload = { tvMazeViewModel.getShows() },
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = { BottomBar(
            onClick = { navController.navigate(it.route) },
            currentDestination = currentDestination,
        ) },
        contentColor = colorScheme.onBackground,
        containerColor = colorScheme.background,
    ) { innerPadding ->

        AppNavigation(
            modifier = modifier.padding(innerPadding),
            navController = navController,
            networkState = tvMazeViewModel.state.collectAsState().value,
            retryAction = { tvMazeViewModel.getShows() },
            searchAction = { tvMazeViewModel.searchShow(it) },
            onDetail = { tvMazeViewModel.getShowDetail(it) },
        )

    }

}

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    errorMessage: String = "Error",
    onRetry: () -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Error",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.SemiBold
            )
        )
        Text(
            text = errorMessage,
            style = MaterialTheme.typography.bodyMedium,
            softWrap = true,
            overflow = TextOverflow.Ellipsis
        )
        IconButton(
            onClick = onRetry,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "Retry"
            )
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment =  Alignment.Center
    ) {
        CircularProgressIndicator(
            color = colorScheme.secondary
        )
    }
}

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    onClick: (NavItem) -> Unit = {},
    currentDestination: NavDestination? = null,
    options: List<NavItem> = BOTTOM_NAV_ITEMS,
){
    NavigationBar(
        contentColor = colorScheme.onBackground,
    ){
        
        options.forEach { item ->
            AddItem(
                item = item,
                selected = item.route == currentDestination?.route,
                onClick = onClick
            )
        }
    }
}
@Composable
private fun RowScope.AddItem(
    item: NavItem,
    selected : Boolean,
    onClick: (NavItem) -> Unit
) {
    NavigationBarItem(
        label = { Text(text = item.title) },
        selected = selected,
        onClick = { onClick(item) },
        icon = {
            Icon(
                imageVector = item.icon ?: Icons.Default.AddHome,
                contentDescription = item.title
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    navBackStackEntry: NavBackStackEntry? = null,
    onBack: () -> Unit = {},
    onReload: () -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior
){

    CenterAlignedTopAppBar(
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = navBackStackEntry?.getTitle() ?: " ",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = colorScheme.background,
            titleContentColor = colorScheme.onBackground,
            navigationIconContentColor = colorScheme.onBackground,
            actionIconContentColor = colorScheme.onBackground,
        ),
        navigationIcon = {
            if (navBackStackEntry?.destination?.route != NavItem.Shows.route) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        },
        actions = {
            if (navBackStackEntry?.destination?.route == NavItem.Shows.route) {
                IconButton(onClick = onReload) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Refresh"
                    )
                }
            }
        },
    )
}

private fun NavBackStackEntry.getTitle() : String? {
    // just show the title of the BOTTOM_NAV_ITEMS list
    return BOTTOM_NAV_ITEMS.find { navItem ->
        destination.hierarchy.any { destination ->
            destination.route == navItem.route
        }
    }?.title?.capitalize(Locale.ENGLISH)
}