package io.dynamiteapps.dribbli.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.budoxr.ett.commons.onIntType
import com.budoxr.ett.ui.theme.EasyTimeTrackingTheme

@Composable
fun TimerPickerGroup(
    hours: Int,
    minutes: Int,
    seconds: Int,
    onHoursChange: onIntType,
    onMinutesChange: onIntType,
    onSecondsChange: onIntType,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Hours
        WheelDigitPicker(
            modifier = Modifier.width(60.dp),
            selectedValue = hours,
            maxRange = 23,
            onValueChange = onHoursChange
        )

        Text(
            text = ":",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        // Minutes
        WheelDigitPicker(
            modifier = Modifier.width(60.dp),
            selectedValue = minutes,
            maxRange = 59,
            onValueChange = onMinutesChange
        )

        Text(
            text = ":",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        // Seconds
        WheelDigitPicker(
            modifier = Modifier.width(60.dp),
            selectedValue = seconds,
            maxRange = 59,
            onValueChange = onSecondsChange
        )
    }
}


@Composable
@Preview(showBackground = true)
private fun TimerPickerGroupPreview() {
    val isDarkTheme = false

    EasyTimeTrackingTheme (darkTheme = isDarkTheme, dynamicColor = false) {
        Surface( modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.background
        ) {
            TimerPickerGroup(
                hours = 12,
                minutes = 30,
                seconds = 45,
                onHoursChange = {},
                onMinutesChange = {},
                onSecondsChange = {}
            )
        }
    }

}