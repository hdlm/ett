/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.budoxr.ett.BuildConfig
import com.budoxr.ett.R
import com.budoxr.ett.ui.components.GlobalTopBar
import com.budoxr.ett.ui.navigation.Screens
import com.budoxr.ett.ui.theme.EasyTimeTrackingTheme
import com.budoxr.ett.ui.theme.blue
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(
    language: String,
    isDarkTheme: Boolean,
    onBackButtonClick : () -> Unit = {}
) {
    Timber.tag(TAG).i("Compose / Recompose, language: $language")




    Scaffold(
        topBar = {
            GlobalTopBar(
                isDarkTheme = isDarkTheme,
                navIcon = Screens.AboutScreen.icon,
                onBackButtonClick = onBackButtonClick,
                titleIcon = null,
                title = stringResource(Screens.AboutScreen.titleResId),
                actionIcon = null,
                onActionButtonClick = {},
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ett_logo),
                contentDescription = stringResource(id = R.string.content_description_ett_logo),
                modifier = Modifier.size(120.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))


            AboutSectionItem(
                label = stringResource(id = R.string.label_version, BuildConfig.VERSION_NAME)
            )

            AboutSectionItem(
                label = stringResource(id = R.string.label_developer, "Henry De la Mano")
            )

            //#region Contact/Support
            val contactEmail = stringResource(id = R.string.url_contact_email)
            val labelContact = stringResource(id = R.string.label_contact, contactEmail)

            // Find the start and end index of the email within the localized string
            val emailStartIndex = labelContact.indexOf(contactEmail)

            val annotatedLabelContact = buildAnnotatedString {
                append(labelContact)
                if (emailStartIndex != -1) {
                    addStyle(
                        style = SpanStyle(
                            color = blue,
                            textDecoration = TextDecoration.Underline
                        ),
                        start = emailStartIndex,
                        end = emailStartIndex + contactEmail.length
                    )
                }
            }

            AboutSectionItem(
                label = annotatedLabelContact,
                value = "mailto:$contactEmail",
                isLinkLabel = true,
            )
            //#endregion

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(id = R.string.label_legal),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold
            )

            AboutSectionItem(
                label = stringResource(id = R.string.label_privacy_policy),
                value = stringResource(id = R.string.url_privacy_policy),
                isLinkValue = true
            )

            AboutSectionItem(
                label = stringResource(id = R.string.label_terms_of_service),
                value = stringResource(id = R.string.url_terms_of_service),
                isLinkValue = true
            )

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(id = R.string.label_software_licenses),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold
            )

            val labelMitLicense = stringResource(id = R.string.label_mit_license)
            val annotatedLabelMitLicense = buildAnnotatedString {
                append(labelMitLicense)
                addStyle(
                    style = SpanStyle(
                        color = blue,
                        textDecoration = TextDecoration.Underline
                    ),
                    start = 0,
                    end = labelMitLicense.length
                )
            }
            AboutSectionItem(
                label = annotatedLabelMitLicense,
                value = stringResource(id = R.string.url_mit_license),
                isLinkLabel = true
            )
        }
    }
}

@Composable
fun AboutSectionItem(
    modifier: Modifier = Modifier,
    label: Any,
    isLinkLabel: Boolean = false,
    value: String? = null,
    isLinkValue: Boolean = false
) {
    val uriHandler = LocalUriHandler.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        // Use a helper to handle either String or AnnotatedString
        val textToDisplay = when (label) {
            is AnnotatedString -> label
            else -> AnnotatedString(label.toString())
        }

        if (isLinkLabel) {
            Text(
                text = textToDisplay,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.clickable {
                    try {
                        uriHandler.openUri(value ?: "")
                    } catch (e: Exception) {
                        Timber.tag(TAG).e("Could not open: $value")
                    }
                }
            )

        } else {
            Text(
                text = textToDisplay,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = if (value != null) FontWeight.SemiBold else FontWeight.Normal
            )

            if (value != null) {
                if (isLinkValue) {
                    Text(
                        text = value,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            textDecoration = TextDecoration.Underline
                        ),
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.clickable { uriHandler.openUri(value) }
                    )

                } else {
                    Text(
                        text = value,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )

                }

            }

        }

    }
}


@Composable
@Preview(showBackground = true)
fun AboutScreenPreview() {
    val isDarkTheme = false

    EasyTimeTrackingTheme(darkTheme = isDarkTheme, dynamicColor = false) {
        Surface(color = MaterialTheme.colorScheme.background) {
            AboutScreen(
                language = "es",
                isDarkTheme = isDarkTheme,
                onBackButtonClick = {}
            )
        }
    }

}

private const val TAG = "che.AboutScreen"