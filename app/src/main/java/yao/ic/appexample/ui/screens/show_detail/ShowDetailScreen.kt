package yao.ic.appexample.ui.screens.show_detail

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import yao.ic.appexample.data.model.Show
import yao.ic.appexample.ui.screens.show_list.ShowThumbnail

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
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        )
        SummaryContent(show = show)

    }
}

@Composable
fun SummaryContent(show: Show) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Summary",
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(
            modifier = Modifier.padding(5.dp)
        )
        Text(
            text = show.toFormattedDescription(),
            style = MaterialTheme.typography.bodyMedium
        )
    }
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
        ShowThumbnail(
            modifier = Modifier
                .fillMaxWidth(0.42f)
                .clip(RoundedCornerShape(10.dp)),
            show = show
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
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )
            )
            LabeledText(
                title = "Genres",
                value = show.toGenresString(),
            )
            LabeledText(
                title = "Premiered",
                value = show.toPremieredString(),
            )
            LabeledText(
                title = "Country",
                value = show.network?.country?.toString() ?: "No country",
            )
            LabeledText(
                title = "Language",
                value = show.language,
            )
//            Text(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(bottom = 14.dp),
//                text = show.toPremieredString(),
//                style = MaterialTheme.typography.bodySmall,
//                textAlign = TextAlign.Start,
//                maxLines = 1,
//                overflow = TextOverflow.Ellipsis
//            )
//            Text(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(bottom = 14.dp),
//                text = show.language,
//                style = MaterialTheme.typography.bodySmall,
//                textAlign = TextAlign.Start,
//                maxLines = 1,
//                overflow = TextOverflow.Ellipsis
//            )
        }
    }
}


@Composable
fun LabeledText(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    delimiter: String = ": ",
    overflow: TextOverflow = TextOverflow.Ellipsis,
    maxLines: Int = 2,
    fontSize : TextUnit = 13.sp,
) {
    Text(
        buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontSize = fontSize,
                    fontWeight = FontWeight.Bold,
                )
            ) {
                append("$title$delimiter")
            }
            withStyle(
                style = SpanStyle(
                    fontSize = fontSize,
                    fontWeight = FontWeight.Normal,
                )
            ) {
                append(value)
            }
        },
        modifier = modifier.padding(vertical = 6.dp) ,
        maxLines = maxLines,
        overflow = overflow,
        lineHeight = 20.sp,
    )
}
