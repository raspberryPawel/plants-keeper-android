package com.example.plantskeeper

import MainViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import coil.compose.AsyncImage
import com.example.plantskeeper.ui.theme.PlantsKeeperTheme

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        viewModel.getData()


        setContent {
            PlantsKeeperTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    Showcase(viewModel)
                }
            }
        }
    }
}


@Composable
fun Showcase(viewModel: MainViewModel, modifier: Modifier = Modifier) {
    val starships by viewModel.immutablePlantsData.observeAsState(emptyList())

    Row(
        Modifier
            .width(500.dp)
            .height(500.dp)
            .padding(top = 100.dp)
            .horizontalScroll(rememberScrollState())
    ) {
        if (starships.isNotEmpty()) {
            starships.forEachIndexed { index, plant ->
                PlantBox(
                    plant.name,
                    "roślina doniczkowa",
                    plant.image.replace("localhost", "192.168.0.199")
                )
            }
        }
    }

}

@Composable
fun PlantBox(plantName: String, plantType: String, imageUrl: String) {
    Box(
        modifier = Modifier
            .width(180.dp)
            .height(280.dp)
            .padding(10.dp)
    ) {
        Box(
            modifier = Modifier.zIndex(10F)
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = plantName,
                modifier = Modifier
                    .width(200.dp)
                    .height(200.dp)
            )
        }
        Box(
            modifier = Modifier
                .width(200.dp)
                .height(200.dp)
                .shadow(10.dp, shape = RoundedCornerShape(15.dp))
                .background(Color(219, 241, 203))
                .align(Alignment.BottomCenter)
                .zIndex(5F)
        ) {


            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(10.dp)
            ) {
                Text(text = plantType, fontSize = 14.sp, color = Color(65, 129, 63))
                Text(text = plantName, fontSize = 20.sp)
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!", modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PlantsKeeperTheme {
        Greeting("Android")
    }
}