package com.samiun.businesskitchen.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemCard(name: String, onClick: () -> Unit) {
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
        Column(Modifier.fillMaxWidth()) {

            Text(
                text = name,
                textAlign = TextAlign.Center,
                maxLines = 1,
                fontSize = 24.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )
        }
    }
}