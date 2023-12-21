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
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.take
import yao.ic.appexample.navigation.AppNavigation
import yao.ic.appexample.navigation.BOTTOM_NAV_ITEMS
import yao.ic.appexample.navigation.NavArg
import yao.ic.appexample.navigation.NavItem
import yao.ic.appexample.navigation.findArg
import yao.ic.appexample.ui.screens.show_list.TVMazeViewModel
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TVMazeApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    tvMazeViewModel: TVMazeViewModel = hiltViewModel()
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val scrollBehavior = when (currentDestination?.route) {
        NavItem.Shows.route -> TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
        else -> TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    }

    val id = remember { mutableStateOf(0) }


    LaunchedEffect(key1 = navBackStackEntry, key2 = id.value) {
        if (currentDestination?.route == NavItem.Shows.route)
            tvMazeViewModel.getShows()

        navBackStackEntry?.let {
            id.value = it.findArg(NavArg.ShowID) ?: 0
        }
    }

    val isFavorite = remember { mutableStateOf<Flow<Boolean>>(MutableStateFlow(false)) }


    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                navBackStackEntry = navController.currentBackStackEntryAsState().value,
                onBack = {
                    navController.popBackStack();
                },
                onReload = { tvMazeViewModel.getShows() },
                onSearch = { },
                onFavorite = {
                    isFavorite.value = tvMazeViewModel.toggleFavorite(it)
                },
                isFavorite = isFavorite.value.collectAsState(initial = false).value,
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            BottomBar(
                onClick = { navController.navigate(it.route) },
                currentDestination = currentDestination,
            )
        },
        contentColor = colorScheme.onBackground,
        containerColor = colorScheme.background,
    ) { innerPadding ->

        AppNavigation(
            modifier = modifier.padding(innerPadding),
            navController = navController,
            networkState = tvMazeViewModel.state.collectAsState().value,
            searchAction = { tvMazeViewModel.searchShow(it) },
            onDetail = { tvMazeViewModel.getShowDetail(it) },
            loadShows = { tvMazeViewModel.getShows() }
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
        contentAlignment = Alignment.Center
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
) {
    NavigationBar(
        contentColor = colorScheme.onBackground,
    ) {

        repeat(options.size) { index ->
            val item = options[index]
            val selected = currentDestination?.hierarchy?.any { it.route == item.route } == true
            AddItem(
                item = item,
                selected = selected,
                onClick = onClick
            )
        }
    }
}

@Composable
private fun RowScope.AddItem(
    item: NavItem,
    selected: Boolean,
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
    onSearch: () -> Unit = {},
    onFavorite: (Int) -> Unit = {},
    isFavorite: Boolean,
    scrollBehavior: TopAppBarScrollBehavior
) {

    LaunchedEffect(key1 = isFavorite) {

    }

    CenterAlignedTopAppBar(
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = navBackStackEntry?.getTitle() ?: " ",
                style = MaterialTheme.typography.titleLarge.copy(
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
            when (navBackStackEntry?.destination?.route) {
                NavItem.Shows.route -> {
                    IconButton(onClick = onSearch) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search"
                        )
                    }

                }

                else -> {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }

                }
            }
        },
        actions = {
            when (navBackStackEntry?.destination?.route) {
                NavItem.Shows.route -> {
                    IconButton(onClick = onReload) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh"
                        )
                    }
                }

                NavItem.ShowDetail.route -> {
                    val id = navBackStackEntry.findArg<Int>(NavArg.ShowID)
                    IconButton(onClick = { onFavorite(id) }) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite"
                        )
                    }
                }

                else -> {}
            }
        },
    )
}

private fun NavBackStackEntry.getTitle(): String? {
    return BOTTOM_NAV_ITEMS.find { navItem ->
        destination.hierarchy.any { destination ->
            destination.route == navItem.route
        }
    }?.title?.capitalize(Locale.ENGLISH)
}