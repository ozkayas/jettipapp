package com.ozkayas.jetpactipapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ozkayas.jetpactipapp.components.InputField
import com.ozkayas.jetpactipapp.ui.theme.JetpactipappTheme
import com.ozkayas.jetpactipapp.util.calculateTotalPerPerson
import com.ozkayas.jetpactipapp.util.calculateTotalTip
import com.ozkayas.jetpactipapp.widgets.RoundIconButton

class MainActivity : ComponentActivity() {
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpactipappTheme {

                Column {
                    BillForm()
                }

            }
        }
    }
}


//@Preview
@Composable
fun TopHeader(totalPerPerson: Double = 1.0) {
    Surface(modifier = Modifier
        .padding(all = 20.dp)
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

/*@ExperimentalComposeUiApi
@Preview
@Composable
fun MainContent() {
    BillForm(){
        billAmount ->
        Log.d("TAG", "MainContent: $billAmount")
    }

}*/



@ExperimentalComposeUiApi
@Composable
fun BillForm(modifier: Modifier = Modifier,
            onValChange: (String) -> Unit = {}){

    val totalBillState = remember {
        mutableStateOf("")
    }
    val validState = remember(totalBillState.value) {
        totalBillState.value.trim().isNotEmpty()
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    var personCount by remember { mutableStateOf(2)}
    val personCountRange = IntRange(1,10)
    fun decrement(){if(personCount>personCountRange.first){personCount--}

    }
    fun increment(){
       if (personCount<personCountRange.last) { personCount++ }}
    var sliderPositionState: Float by remember{ mutableStateOf(0f)}
    val tipPercentage = (sliderPositionState*100).toInt()
    var tipAmount: Double by remember { mutableStateOf(0.0)}
    var totalPerPersonState by remember {
        mutableStateOf(0.0)
    }

    TopHeader(totalPerPerson = totalPerPersonState)

    Surface(modifier = Modifier
        .padding(20.dp)
        .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(width = 1.5.dp, color = Color.LightGray)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            InputField(
                valueState = totalBillState,
                labelId ="Enter Bill",
                enabled = true,
                isSingleLine = true,
                onAction = KeyboardActions{
                    if (!validState) return@KeyboardActions
                    onValChange(totalBillState.value.trim())
                    keyboardController?.hide()
                }
            )
            if (validState){
//            if (true){
                Row(modifier = Modifier.padding(all = 12.dp), horizontalArrangement = Arrangement.Start) {
                    Text(
                        text = "Split",
                        modifier = Modifier.align(
                            alignment = Alignment.CenterVertically
                        )
                    )
                    Spacer(modifier = Modifier.width(120.dp))
                    Row(modifier = Modifier.padding(all = 12.dp),
                        horizontalArrangement = Arrangement.End) {
                        RoundIconButton(imageVector = Icons.Default.Remove, onClick = { decrement()
                            totalPerPersonState = calculateTotalPerPerson(totalBill = totalBillState.value.toDouble(), splitBy = personCount,
                                tipPercentage = tipPercentage)
                        })
                        Text(text = "$personCount",
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .padding(start = 9.dp, end = 9.dp)
                        )
                        RoundIconButton(imageVector = Icons.Default.Add, onClick = { increment()
                            totalPerPersonState = calculateTotalPerPerson(totalBill = totalBillState.value.toDouble(), splitBy = personCount,
                                tipPercentage = tipPercentage)
                        })
                    }
                }
                Row(modifier = Modifier.padding(all = 12.dp)) {
                    Text(text = "Tip", modifier = Modifier.align(alignment = Alignment.CenterVertically))
                    Spacer(modifier = Modifier.width(200.dp))
                    Text(text = "$tipAmount",modifier = Modifier.align(alignment = Alignment.CenterVertically))
                }
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "$tipPercentage %")
                    Spacer(modifier = Modifier.height(20.dp))
                    Slider(
                        modifier = Modifier.padding(horizontal = 20.dp),
                        valueRange = 0f..1f,
                        value = sliderPositionState,
                        onValueChange = {  sliderPositionState = it

                            tipAmount = calculateTotalTip(totalBill = totalBillState.value.toDouble(),
                                tipPercentage = tipPercentage)
                            totalPerPersonState = calculateTotalPerPerson(totalBill = totalBillState.value.toDouble(), splitBy = personCount,
                                tipPercentage = tipPercentage)
                            Log.d("TAG", "$sliderPositionState ")
                        })
                }
            }
            else {
                Box {
                    
                }
            }
        }
    }

}