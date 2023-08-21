package com.samiun.businesskitchen.ui.components


import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.samiun.businesskitchen.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemScreenTopBar(
    onPrint: () -> Unit = {},
    title: String = stringResource(id = R.string.app_name),
) {
    LocalContext.current
    TopAppBar(
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
        title = {
            Text(
                title, color = MaterialTheme.colorScheme.onPrimary
            )
        },
        actions = {
                  Button(onClick = { onPrint() }) {
                      Text(text = "Print")
                  }
        },
    )
}
