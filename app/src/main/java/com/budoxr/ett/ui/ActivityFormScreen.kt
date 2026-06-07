/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.budoxr.ett.R
import com.budoxr.ett.commons.onDismissType
import com.budoxr.ett.commons.onIntType
import com.budoxr.ett.commons.onStringType
import com.budoxr.ett.presentation.presenters.ActivityFormState
import com.budoxr.ett.presentation.presenters.ActivityFormUiState
import com.budoxr.ett.presentation.presenters.ActivityFormViewModel
import com.budoxr.ett.ui.components.ButtonConfirm
import com.budoxr.ett.ui.components.FieldFormCombo
import com.budoxr.ett.ui.components.FieldFormText
import com.budoxr.ett.ui.components.GlobalTopBar
import com.budoxr.ett.ui.theme.EasyTimeTrackingTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber

@Composable
fun ActivityFormScreen(
    id: Long,
    isDarkTheme: Boolean,
    onBackButtonClick: onDismissType,
    viewModel: ActivityFormViewModel = koinViewModel()
) {
    Timber.tag(TAG).i("Compose / Recompose -> id: $id")

    val marginHorizontal = dimensionResource(R.dimen.margin_horizontal)

    val formState by viewModel.formState.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val resultMessage = stringResource(R.string.msg_new_activity_save)
    val onConfirmClick: onDismissType = {
        viewModel.onSaveClick()
        scope.launch {
            val snackbarResult = snackbarHostState.showSnackbar(
                message = resultMessage,
                actionLabel = "x",
                duration = SnackbarDuration.Short
            )
            when (snackbarResult) {
                SnackbarResult.Dismissed -> {
                    Timber.tag(TAG).i("Snackbar dismissed, clean form")
                    viewModel.onCleanForm()
                }
                SnackbarResult.ActionPerformed -> {
                    Timber.tag(TAG).i("Snackbar action performed, go back")
                    onBackButtonClick.invoke()
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        topBar = {
            GlobalTopBar(
                isDarkTheme = isDarkTheme,
                navIcon = Icons.Filled.Close,
                onBackButtonClick = onBackButtonClick,
                titleIcon = null,
                title = stringResource(id = R.string.title_activity_form_screen),
                actionIcon = null,
                onActionButtonClick = {},
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState,
                modifier = Modifier.imePadding()
            )
        },
        bottomBar = {
            ButtonConfirm(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = marginHorizontal)
                    .navigationBarsPadding()
                    .imePadding(),
                label = stringResource(R.string.label_button_save),
                isEnabled = formState.isValid,
                showTopBorderLine = true,
                buttonIcon = null,
                buttonVector = null,
                buttonImg = null,
                onConfirmClick = onConfirmClick,
            )
        }
    ) { innerPadding ->

        when (uiState) {
            is ActivityFormUiState.Form -> {
                Column( modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
                    .padding(horizontal = marginHorizontal),
                ) {

                    ActivityFormBodyScreen(
                        isDarkTheme = isDarkTheme,
                        formState = formState,
                        onNameChanged = viewModel::onNameChanged,
                        onColorChange = viewModel::onColorChanged,
                    )

                }

            }

            is ActivityFormUiState.Loading -> {
                ActivityFormScreenLoading()

            }
        }

    }

}


@Composable
private fun ActivityFormScreenLoading(modifier: Modifier = Modifier) {
    val iconSize = dimensionResource(id = R.dimen.icon_huge_size)
    val areaSize = 94.dp

    Surface(modifier.fillMaxSize()) {
        Box {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(areaSize)
                    .align(Alignment.Center),
                strokeWidth = 8.dp,
                color = MaterialTheme.colorScheme.primary
            )

            Image( modifier = Modifier
                .align(Alignment.Center)
                .clip(CircleShape)
                .size(iconSize),
                painter = painterResource(id = R.drawable.ett_logo),
                contentDescription = stringResource(id = R.string.content_description_ett_logo),
                contentScale = ContentScale.Fit,
            )
        }
    }
}


@Composable
fun ActivityFormBodyScreen(
    isDarkTheme: Boolean,
    formState: ActivityFormState,
    onNameChanged: onStringType,
    onColorChange: onStringType,
) {
    val lineSpacing2x = dimensionResource(R.dimen.line_spacing_2)

    val colorsArray: Array<String> = stringArrayResource(id = R.array.colors_array)

    val onColorItemSelected: onIntType = { index ->
        Timber.tag(TAG).d("onColorItemSelected() -> invoked, index: $index")
        onColorChange.invoke(colorsArray[index])
    }

    FieldFormText(
        isDarkTheme = isDarkTheme,
        label = stringResource(R.string.label_name),
        hintLabel = stringResource(R.string.label_hint_name),
        field = formState.name,
        onValueChange = onNameChanged,
    )

    Spacer(modifier = Modifier.padding(vertical = lineSpacing2x))

    FieldFormCombo(
        modifier = Modifier,
        isDarkTheme = isDarkTheme,
        items = colorsArray,
        label = stringResource(R.string.label_color),
        field = formState.color,
        onSelectedItem = onColorItemSelected,
        enabled = true
    )

}


@Composable
@Preview(showBackground = true)
fun ActivityFormScreenPreview() {
    val formState = ActivityFormState(
        name = "My actividad",
        color = "blue",
        isValid = true,
    )

    val marginHorizontal = dimensionResource(R.dimen.margin_horizontal)
    val scrollState = rememberScrollState()

    val isDarkTheme = true

    EasyTimeTrackingTheme(darkTheme = isDarkTheme, dynamicColor = false) {
        Column( modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
            .fillMaxSize()
            .padding(marginHorizontal)
            .imePadding(), // Save Bottom always on Top
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(Modifier.fillMaxWidth()) {
                ActivityFormBodyScreen(
                    isDarkTheme = isDarkTheme,
                    formState = formState,
                    onNameChanged = {},
                    onColorChange = {},
                )
            }

            Column {
                ButtonConfirm(
                    modifier = Modifier,
                    label = stringResource(R.string.label_button_save),
                    isEnabled = true,
                    showTopBorderLine = true,
                    buttonIcon = null,
                    buttonVector = null,
                    buttonImg = null,
                    onConfirmClick = {},
                )
            }
        }

    }

}

private const val TAG = "che.ActivityFormScreen"