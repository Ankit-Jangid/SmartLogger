package com.samples.smartlogger.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.samples.smartlogger.ui.theme.SmartLoggerTheme

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.unit.dp
import com.logger.config.SmartLoggerConfig
import com.logger.core.SmartLogger



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            SmartLoggerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DummyLoggerUI(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun DummyLoggerUI(modifier: Modifier = Modifier) {
    var eventCount by rememberSaveable {  mutableIntStateOf(0) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Dummy Event Logger")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            eventCount++
             SmartLogger.get().logEvent(
                eventName = "button_clicked",
                payload = mapOf(
                    "click_count" to eventCount,
                    "source" to "ComposeUI",
                    "timestamp" to System.currentTimeMillis()
                )
            )
        }) {
            Text("Log Dummy Event #$eventCount")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DummyLoggerPreview() {
    SmartLoggerTheme {
        DummyLoggerUI()
    }
}
