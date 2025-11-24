package com.budoxr.ett.ui

import androidx.compose.runtime.Composable
import com.budoxr.ett.commons.onDismissType
import com.budoxr.ett.commons.onIntType
import com.budoxr.ett.presentation.presenters.ActivityViewModel
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber

@Composable
fun ActivityScreen(
    isDarkTheme: Boolean,
    onBackButtonClick: onDismissType,
    navigateToActivityForm: onIntType,
    viewModel: ActivityViewModel = koinViewModel()
) {
    Timber.tag(TAG).i("compose / recompose")

    //TODO not yet implemented

}

private const val TAG = "che.ActivityScreen"