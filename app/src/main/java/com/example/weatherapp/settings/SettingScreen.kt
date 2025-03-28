package com.example.weatherapp.settings

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.weatherapp.R
import com.example.weatherapp.data.sharedPreferences.SharedPreferencesDataSource
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import java.util.Locale

data class SettingItem(
    val titleRes: Int,
    val optionResIds: List<Int>,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

data class LanguageOption(
    val key: String,
    val displayResId: Int
)


fun updateLocale(context: Context, locale: Locale) {
    val resources = context.resources
    val configuration = Configuration(resources.configuration)
    configuration.setLocale(locale)
    resources.updateConfiguration(configuration, resources.displayMetrics)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    sharedPreferencesDataSource: SharedPreferencesDataSource,
    onSettingsChanged: () -> Unit
) {
    val context = LocalContext.current

    var selectedLocation by remember { mutableStateOf(sharedPreferencesDataSource.getLocationSetting()) }
    var selectedWindSpeed by remember { mutableStateOf(sharedPreferencesDataSource.getWindSpeedSetting()) }
    var selectedLanguageKey by remember { mutableStateOf(sharedPreferencesDataSource.getLanguageSetting()) }
    var selectedNotification by remember { mutableStateOf(sharedPreferencesDataSource.getNotificationSetting()) }

    val locationTitle = stringResource(id = R.string.location)
    val windSpeedTitle = stringResource(id = R.string.wind_speed)
    val languageTitle = stringResource(id = R.string.language)
    val notificationTitle = stringResource(id = R.string.notification)

    val languageOptions = listOf(
        LanguageOption(key = "en", displayResId = R.string.english),
        LanguageOption(key = "ar", displayResId = R.string.arabic)
    )
    val languageDisplayMap = languageOptions.associate { it.key to stringResource(id = it.displayResId) }

    val settings = listOf(
        SettingItem(
            titleRes = R.string.location,
            optionResIds = listOf(R.string.gps, R.string.map),
            icon = Icons.Default.LocationOn
        ),
        SettingItem(
            titleRes = R.string.wind_speed,
            optionResIds = listOf(R.string.meter_per_second, R.string.mile_per_hour),
            icon = Icons.Default.Build
        ),
        SettingItem(
            titleRes = R.string.language,
            optionResIds = languageOptions.map { it.displayResId },
            icon = Icons.Default.Create
        ),
        SettingItem(
            titleRes = R.string.notification,
            optionResIds = listOf(R.string.enable, R.string.disable),
            icon = Icons.Default.Notifications
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.settings)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.back)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(settings) { setting ->
                val titleText = stringResource(id = setting.titleRes)
                val optionsText = if (titleText == languageTitle) {
                    languageOptions.map { stringResource(id = it.displayResId) }
                } else {
                    setting.optionResIds.map { stringResource(id = it) }
                }
                val selectedOption = when (titleText) {
                    locationTitle -> selectedLocation
                    windSpeedTitle -> selectedWindSpeed
                    languageTitle -> languageDisplayMap[selectedLanguageKey] ?: ""
                    notificationTitle -> selectedNotification
                    else -> ""
                }

                SettingsCard(
                    title = titleText,
                    options = optionsText,
                    selectedOption = selectedOption,
                    icon = setting.icon
                ) { newValue ->
                    when (titleText) {
                        windSpeedTitle -> {
                            selectedWindSpeed = newValue
                            sharedPreferencesDataSource.setWindSpeedSetting(newValue)
                            onSettingsChanged()
                        }
                        languageTitle -> {
                            val selectedKey = languageDisplayMap.entries.find { it.value == newValue }?.key
                            if (selectedKey != null) {
                                selectedLanguageKey = selectedKey
                                sharedPreferencesDataSource.setLanguageSetting(selectedLanguageKey)
                                onSettingsChanged()

                                val newLocale = if (selectedLanguageKey == "ar") Locale("ar") else Locale("en")
                                updateLocale(context, newLocale)
                                (context as? Activity)?.recreate()
                            }
                        }
                        notificationTitle -> {
                            selectedNotification = newValue
                            sharedPreferencesDataSource.setNotificationSetting(newValue)
                        }
                    }
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
                modifier = Modifier.padding(bottom = 8.dp),
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onOptionSelected(option) }
                        .padding(vertical = 8.dp),
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
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
