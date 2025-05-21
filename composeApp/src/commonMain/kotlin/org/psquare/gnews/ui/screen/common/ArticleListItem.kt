package org.psquare.gnews.ui.screen.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import gnews.composeapp.generated.resources.Res
import gnews.composeapp.generated.resources.ic_bookmark_add
import gnews.composeapp.generated.resources.ic_bookmark_remove
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.resources.painterResource
import org.psquare.gnews.domain.entities.ArticleEntity

@Composable
fun ArticleListItem(
    articleEntity: ArticleEntity,
    onClick: (ArticleEntity) -> Unit,
    onBookmarkClick: (ArticleEntity) -> Unit
) {
    Row(modifier = Modifier.clickable { onClick.invoke(articleEntity) }) {
        Box(
            modifier = Modifier.padding(16.dp)
                .size(80.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(color = MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            KamelImage(
                { asyncPainterResource(articleEntity.image) },
                contentDescription = null,
                contentScale = ContentScale.Crop,
                onLoading = { progress ->
                    CircularProgressIndicator(
                        modifier = Modifier.size(32.dp),
                        progress = { progress }
                    )
                }
            )
        }
        Column(modifier = Modifier.weight(1f).padding(top = 16.dp, bottom = 16.dp, end = 16.dp)) {
            Text(
                text = articleEntity.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = "${articleEntity.sourceName} · ${articleEntity.elapsedTime}",
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        IconButton(onClick = { onBookmarkClick.invoke(articleEntity) }) {
            Icon(
                painter = painterResource(
                    if (articleEntity.isBookmarked) {
                        Res.drawable.ic_bookmark_remove
                    } else {
                        Res.drawable.ic_bookmark_add
                    }
                ),
                contentDescription = if (articleEntity.isBookmarked) {
                    "Remove bookmark"
                } else {
                    "Add bookmark"
                }
            )
        }
    }
}