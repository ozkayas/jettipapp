package com.ozkayas.jetpactipapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ozkayas.jetpactipapp.ui.theme.JetpactipappTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpactipappTheme {
                MyApp()
            }
        }
    }
}
@Composable
fun MyApp(){
    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Text("MyApp")
    }
}

//@Preview
@Composable
fun TopHeader(totalPerPerson: Double = 1.0) {
    Surface(modifier = Modifier
        .fillMaxWidth()
        .height(150.dp)
        .clip(shape = RoundedCornerShape(15.dp)),
    color = Color(0xFF58BEEC)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val amount = "%.2f".format(totalPerPerson)
            Text(text = "Total per person:", style = MaterialTheme.typography.h4)
            Text(text = "$ $amount", style = MaterialTheme.typography.h3, fontWeight = FontWeight.Bold)

        }
    }
}

@Preview
@Composable
fun MainContent() {
    Surface(modifier = Modifier
        .padding(2.dp)
        .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(width = 1.5.dp, color = Color.LightGray)
    ) {
        Column() {
            Text(text = "Hello")
            Text(text = "Hello")
            Text(text = "Hello")
            Text(text = "Hello")
        }
    }
}