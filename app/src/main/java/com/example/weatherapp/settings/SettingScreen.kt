package com.example.weatherapp.settings

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

@Composable
fun SettingsScreen() {
    var selectedLocation by remember { mutableStateOf("GPS") }
    var selectedWindSpeed by remember { mutableStateOf("Mile/Hour") }
    var selectedLanguage by remember { mutableStateOf("English") }
    var selectedNotification by remember { mutableStateOf("Enable") }

    val settings = listOf(
        Triple("Location", listOf("GPS", "Map"), Icons.Default.LocationOn),
        Triple("Wind Speed", listOf("Meter/Second", "Mile/Hour"), Icons.Default.Build),
        Triple("Language", listOf("English", "Arabic"), Icons.Default.Create),
        Triple("Notification", listOf("Enable", "Disable"), Icons.Default.Notifications)
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Settings",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        items(settings) { setting ->
            val (title, options, icon) = setting
            val selectedOption = when (title) {
                "Location" -> selectedLocation
                "Wind Speed" -> selectedWindSpeed
                "Language" -> selectedLanguage
                "Notification" -> selectedNotification
                else -> ""
            }

            SettingsCard(title, options, selectedOption, icon) { newValue ->
                when (title) {
                    "Location" -> selectedLocation = newValue
                    "Wind Speed" -> selectedWindSpeed = newValue
                    "Language" -> selectedLanguage = newValue
                    "Notification" -> selectedNotification = newValue
                }
            }
        }
    }
}


@Composable
fun SettingsCard(
    title: String,
    options: List<String>,
    selectedOption: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onOptionSelected: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .animateContentSize()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            options.forEach { option ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onOptionSelected(option) }
                        .padding(vertical = 8.dp)
                ) {
                    RadioButton(
                        selected = (option == selectedOption),
                        onClick = { onOptionSelected(option) }
                    )
                    Text(
                        text = option,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(start = 8.dp),
                        color = if (option == selectedOption) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSettingsScreen() {
    SettingsScreen()
}
