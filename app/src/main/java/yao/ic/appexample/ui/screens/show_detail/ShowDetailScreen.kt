package yao.ic.appexample.ui.screens.show_detail

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import coil.compose.AsyncImage
import coil.request.ImageRequest
import yao.ic.appexample.R
import yao.ic.appexample.data.model.Cast
import yao.ic.appexample.data.model.Show
import yao.ic.appexample.util.getGenres

@Composable
fun ShowDetailScreen(
    modifier: Modifier = Modifier,
    show: Show
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .scrollable(
                state = scrollState,
                orientation = Orientation.Vertical,
            )
    ) {
        ShowDetailHeader(show = show)
        Divider()
        SummaryContent(show = show)
        CastGrid(castList = show.embedded?.cast ?: emptyList())

    }
}

@Composable
fun CastGrid(castList: List<Cast>) {
}

@Composable
fun SummaryContent(show: Show) {
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ShowDetailHeader(
    modifier: Modifier = Modifier,
    show: Show
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ShowDetailHeaderImage(
            modifier = modifier,
            imageUrl = show.image?.medium ?: "",
        )
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = show.name,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            )
            Badge(
                modifier = Modifier
                    .padding(vertical = 5.dp),
                containerColor = MaterialTheme.colorScheme.primaryContainer,
            ) {
                Text(
                    text = show.rating.average.toString(),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 12.sp
                    ),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .padding(horizontal = 5.dp, vertical = 3.dp)
                        .align(Alignment.CenterVertically)
                )
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 14.dp),
                text = show.getGenres(),
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 14.dp),
                text = show.premiered,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Start,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 14.dp),
                text = show.language,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Start,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun ShowDetailHeaderImage(
    modifier: Modifier,
    imageUrl: String
) {
    AsyncImage(
        modifier = modifier
            .fillMaxWidth(0.35f)
            .clip(RoundedCornerShape(10.dp)),
        model = ImageRequest.Builder(context = LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        contentDescription = null,
        contentScale = ContentScale.FillWidth,
        error = painterResource(id = R.drawable.ic_broken_image),
        placeholder = painterResource(id = R.drawable.loading_img)
    )
}

@Composable
private fun CastRow(
    modifier: Modifier = Modifier,
    imageUrl: String,
    name: String,
    character: String
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth(0.35f)
                .clip(RoundedCornerShape(10.dp)),
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            error = painterResource(id = R.drawable.ic_broken_image),
            placeholder = painterResource(id = R.drawable.loading_img)
        )
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 14.dp),
                text = character,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Start,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
