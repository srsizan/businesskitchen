package com.samiun.businesskitchen.ui.components


import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.samiun.businesskitchen.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenTopBar(
    onSignOut: () -> Unit = {},
    onControlPanelClick: () -> Unit = {},
    title: String = stringResource(id = R.string.app_name),
) {

    var settingsDialog by rememberSaveable {
        mutableStateOf(false)
    }
    val items = listOf(
        stringResource(R.string.control_pannel),
        stringResource(R.string.sign_out),
    )
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("") }
    val context = LocalContext.current
    TopAppBar(
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
        title = {
            Text(
                title, color = MaterialTheme.colorScheme.onPrimary
            )
        },
        actions = {

            IconButton(onClick = {
                expanded = !expanded
            }) {
                if (!expanded) {
                    Icon(
                        Icons.Default.ArrowDropDown,
                        stringResource(R.string.dropdown_button),
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(50.dp)
                    )
                } else {
                    Icon(
                        Icons.Default.ArrowDropUp,
                        stringResource(R.string.reverse_dropdown_button),
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(50.dp)
                    )
                }
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                items.forEach { item ->
                    DropdownMenuItem(text = { Text(text = item) }, onClick = {
                        when (item) {
                            context.getString((R.string.control_pannel)) -> onControlPanelClick()
                            context.getString((R.string.sign_out)) -> onSignOut()
                        }
                        selectedItem = item
                        expanded = false
                    })
                }
            }
        },
    )
}
