package yao.ic.appexample.ui.screens.favorite

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import yao.ic.appexample.data.database.entity.FavoriteShow
import yao.ic.appexample.data.database.entity.toShow
import yao.ic.appexample.data.state.ShowState
import yao.ic.appexample.ui.screens.show_list.ShowCard

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavoriteScreen(
    state: ShowState,
    onCardClick: (Int) -> Unit = {},
) {

    if (state.favoriteShows.isEmpty()){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            Text(
                modifier =  Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                text = "No favorite shows yet",
                style = MaterialTheme.typography.titleMedium
            )
        }
    } else {
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize(),
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(12.dp)
        ) {

            items(
                items = state.favoriteShows,
                key = { show -> show.id },
            ) { favorite ->
                ShowCard(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp)
                        .animateItemPlacement(
                            tween(300),
                        ),
                    show = favorite.toShow(),
                    onClick = { onCardClick(favorite.id) }
                )
            }
        }
    }

}

