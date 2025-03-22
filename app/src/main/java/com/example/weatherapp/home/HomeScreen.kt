package com.example.weatherapp.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.weatherapp.R
@Preview(showSystemUi = true)
@Composable
fun HomeScreen(){
    val context= LocalContext.current
    Scaffold (){ innerPadding -> Column (modifier = Modifier.padding(innerPadding).background(Color.Blue).fillMaxSize()){
        Icon(
            painter = painterResource(id = R.drawable.menu_icon),
            contentDescription = "location description",
           // modifier = Modifier.size(AssistChipDefaults.IconSize),
            tint = colorResource(R.color.white) ,
        )
    } }
}