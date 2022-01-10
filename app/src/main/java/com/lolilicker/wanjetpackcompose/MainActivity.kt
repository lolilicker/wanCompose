package com.lolilicker.wanjetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lolilicker.wanjetpackcompose.extensions.colors
import com.lolilicker.wanjetpackcompose.widget.PageTopBar
import com.rengwuxian.wecompose.ui.theme.WeComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeComposeTheme() {
                Surface() {
                    Column(
                        modifier = Modifier.padding(top = 50.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        chooseItem(text = getString(R.string.hacking)) {

                        }
                        chooseItem(text = getString(R.string.doughter_borned)) {

                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun chooseItem(text: String, onClick: () -> Unit) {
        Text(text = text,
            fontSize = 30.sp,
            color = colors.textPrimary,
            modifier = Modifier
                .background(colors.listItem)
                .clickable {
                    onClick()
                }
                .padding(25.dp)
                .fillMaxWidth()
        )
    }


    @Preview("list item")
    @Composable
    fun previewMainPage() {
        chooseItem(text = "test") {

        }
    }
}