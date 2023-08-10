package com.samiun.businesskitchen.ui.components

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.samiun.businesskitchen.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemWithImage(context: Context,name: String, image: String, modifier: Modifier,onClick: () -> Unit) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(
            topStart = 0.dp,
            topEnd = 12.dp,
            bottomStart = 12.dp,
            bottomEnd = 0.dp
        ),
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .padding(bottom = 26.dp)
    ) {
        Column() {
            AsyncImage(
                modifier = modifier.height(150.dp),
                contentScale = ContentScale.Crop,
                model = context.resources.getIdentifier(
                    image,
                    "drawable",
                    "com.samiun.businesskitchen"
                ),
                contentDescription = stringResource(R.string.imagedescription),
            )
            Text(
                text = name,
                textAlign = TextAlign.Center,
                maxLines = 2,
                fontSize = 18.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )
        }
    }
}