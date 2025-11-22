package com.budoxr.ett.ui.components

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.budoxr.ett.R
import com.budoxr.ett.commons.onDismissType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GlobalTopBar(
    isDarkTheme: Boolean,
    navIcon: ImageVector?,
    onBackButtonClick: onDismissType,
    titleIcon: ImageVector?,
    title: String,
    actionIcon: ImageVector?,
    onActionButtonClick: onDismissType,
) {
    CenterAlignedTopAppBar(
        navigationIcon = {
            if (navIcon != null) {
                IconButton(onClick = onBackButtonClick) {
                    Icon(
                        imageVector = navIcon,
                        contentDescription = stringResource(R.string.content_description_nav_icon),
//                        tint = MaterialTheme.colorScheme.onSurface // Use themed color
                    )
                }
            }
        },
        title = {
            if (titleIcon != null) {
                Icon(
                    imageVector = titleIcon,
                    contentDescription = stringResource(R.string.content_description_icon),
//                        tint = MaterialTheme.colorScheme.onSurface // Use themed color
                )
            }
            Text(
                modifier = Modifier,
                text = title,
                style = MaterialTheme.typography.titleSmall,
            )

            //#region Image
//            Image(
//                painter = if(isSystemInDarkTheme()) painterResource(id = R.drawable.peimi_h_white) else painterResource(id = R.drawable.peimi_h_black),
//                contentDescription = stringResource(id = R.string.content_description_topbar_icon),
//                modifier = Modifier
//                    .fillMaxWidth(0.20f)
//                    .aspectRatio(1f), // Maintain square aspect ratio
//                contentScale = ContentScale.Fit
//            )
            //#endregion
        },
        actions = {
            if (actionIcon != null) {
                IconButton(onClick = onActionButtonClick) {
                    Icon(
                        imageVector = actionIcon,
                        contentDescription = stringResource(id = R.string.content_description_icon)
                    )
                }
            }
        },
//        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
//            containerColor = MaterialTheme.colorScheme.background,
//            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
//        )
    )
}