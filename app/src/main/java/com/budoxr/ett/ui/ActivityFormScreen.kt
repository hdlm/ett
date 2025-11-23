package com.budoxr.ett.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.budoxr.ett.R
import com.budoxr.ett.commons.onDismissType
import com.budoxr.ett.commons.onIntType
import com.budoxr.ett.commons.onStringType
import com.budoxr.ett.presentation.presenters.ActivityFormState
import com.budoxr.ett.presentation.presenters.ActivityFormViewModel
import com.budoxr.ett.ui.components.ButtonConfirm
import com.budoxr.ett.ui.components.FieldFormCombo
import com.budoxr.ett.ui.components.FieldFormText
import com.budoxr.ett.ui.components.GlobalTopBar
import com.budoxr.ett.ui.theme.EasyTimeTrackingTheme
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber

@Composable
fun ActivityFormScreen(
    id: Int,
    isDarkTheme: Boolean,
    onBackButtonClick: onDismissType,
    viewModel: ActivityFormViewModel = koinViewModel()
) {
    Timber.tag(TAG).i("Compose / Recompose -> id: $id")

    val marginHorizontal = dimensionResource(R.dimen.margin_horizontal)
    val scrollState = rememberScrollState()

    val formState by viewModel.formState.collectAsStateWithLifecycle()

    val onConfirmClick: onDismissType = {
        viewModel.onSaveClick()
    }

    Scaffold(
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
        }
    ) { innerPadding ->
        Column( modifier = Modifier
            .verticalScroll(scrollState)
            .fillMaxSize()
            .padding(innerPadding)
            .padding(horizontal = marginHorizontal)
            .imePadding(), // Save Bottom always on Top
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                ActivityFormBodyScreen(
                    isDarkTheme = isDarkTheme,
                    formState = formState,
                    onNameChanged = viewModel::onNameChanged,
                    onColorChange = viewModel::onColorChanged,
                    onBackButtonClick = onBackButtonClick
                )
            }

            Column {
                ButtonConfirm(
                    modifier = Modifier,
                    label = stringResource(R.string.label_button_save),
                    isEnabled = formState.isValid,
                    isDarkTheme = isDarkTheme,
                    showTopBorderLine = true,
                    buttonIcon = null,
                    buttonVector = null,
                    buttonImg = null,
                    onConfirmClick = onConfirmClick,
                )

            }

        }
    }

}


@Composable
fun ActivityFormBodyScreen(
    isDarkTheme: Boolean,
    formState: ActivityFormState,
    onNameChanged: onStringType,
    onColorChange: onStringType,
    onBackButtonClick: onDismissType,
) {
    val lineSpacing2x = dimensionResource(R.dimen.line_spacing_2)

    val colorsArray: Array<String> = stringArrayResource(id = R.array.colors_array)

    val onColorItemSelected: onIntType = { index ->
        Timber.tag(TAG).d("onColorItemSelected() -> invoked, index: $index")
        onColorChange.invoke(colorsArray[index])
    }

    FieldFormText(
        label = stringResource(R.string.label_name),
        hintLabel = stringResource(R.string.label_hint_name),
        field = formState.name,
        onValueChange = onNameChanged,
    )

    Spacer(modifier = Modifier.padding(vertical = lineSpacing2x))

    FieldFormCombo(
        items = colorsArray,
        label = "",
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

    EasyTimeTrackingTheme(darkTheme = false, dynamicColor = false) {
        Column( modifier = Modifier
            .verticalScroll(scrollState)
            .fillMaxSize()
            .padding(marginHorizontal)
            .imePadding(), // Save Bottom always on Top
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                ActivityFormBodyScreen(
                    isDarkTheme = false,
                    formState = formState,
                    onNameChanged = {},
                    onColorChange = {},
                    onBackButtonClick = {},
                )
            }

            Column {
                ButtonConfirm(
                    modifier = Modifier,
                    label = stringResource(R.string.label_button_save),
                    isEnabled = formState.isValid,
                    isDarkTheme = false,
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