package yao.ic.appexample.ui.screens.show_list

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import yao.ic.appexample.R
import yao.ic.appexample.data.state.ShowState
import yao.ic.appexample.data.model.Show

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShowListScreen(
    modifier: Modifier = Modifier,
    showState: ShowState,
    onCardClick: (Int) -> Unit = {},
) {
    val showList = remember {
        showState.showList
    }

    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize(),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(12.dp)
    ) {

        items(
            items = showList,
            key = { show -> show.id },
        ) { show ->
            ShowCard(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
                    .animateItemPlacement(
                        tween(300),
                    ),
                show = show,
                onClick = { onCardClick(show.id) }
            )
        }
    }

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowCard(
    modifier: Modifier = Modifier,
    show: Show,
    onClick: () -> Unit = {},
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        onClick = onClick,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ShowThumbnail(
                show = show
            )

            Text(
                text = show.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 14.dp, bottom = 4.dp),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 14.sp
                ),
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Start,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 14.dp),
                text = show.genres.ifEmpty { listOf("No genre") }.joinToString(separator = ", "),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 12.sp
                ),
                textAlign = TextAlign.Start,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowThumbnail(
    modifier: Modifier = Modifier,
    show: Show
) {
    Box(
        contentAlignment = Alignment.BottomStart,
    ){
        AsyncImage(
            modifier = modifier
                .fillMaxWidth(),
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(show.image?.medium)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            error = painterResource(id = R.drawable.ic_broken_image),
            placeholder = painterResource(id = R.drawable.loading_img)
        )
        Badge(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 10.dp, start = 10.dp),
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ) {
            Text(
                text = show.rating.average.toString(),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 11.sp
                ),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .padding(horizontal = 5.dp, vertical = 3.dp)
                    .align(Alignment.CenterVertically)
            )
        }
    }
}