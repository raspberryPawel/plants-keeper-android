package com.example.plantskeeper

import EnvConfig
import SinglePlantActivityModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.example.plantskeeper.repository.UiState
import com.example.plantskeeper.ui.theme.LoadingScreen
import com.example.plantskeeper.ui.theme.PlantsKeeperTheme


class SinglePlantActivity : ComponentActivity() {
    private val viewModel: SinglePlantActivityModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var intent = intent
        var plantId = "NOT FOUND"

        if (intent.hasExtra("PLANT_ID")) {
            plantId = intent.getStringExtra("PLANT_ID").toString()
            viewModel.getData(plantId)
        }

        setContent {
            PlantsKeeperTheme {
                SinglePlantActivityMainView(viewModel)
            }
        }
    }
}


@Composable
fun SinglePlantActivityMainView(viewModel: SinglePlantActivityModel) {
    val uiState by viewModel.immutablePlantData.observeAsState(UiState())

    when {
        uiState.isLoading -> LoadingScreen()

        uiState.error != null -> {
            Text(
                text = "Something went wrong. Try again.",
                fontSize = 14.sp,
                color = Color(65, 129, 63)
            )
        }

        uiState.data != null -> uiState.data?.let { it ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .zIndex(10F)
                        .fillMaxWidth()
                        .height(200.dp)
                        .shadow(10.dp, shape = RoundedCornerShape(15.dp))
                        .background(Color(219, 241, 203))
                        .zIndex(5F)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                    ) {
                        AsyncImage(
                            model = it.image.replace("http://localhost", EnvConfig.getServerPath()),
                            contentDescription = it.name,
                            modifier = Modifier
                                .width(200.dp)
                                .height(200.dp),
                            placeholder = null
                        )

                        Column(
                            modifier = Modifier
                                .align(Alignment.Bottom)
                                .padding(10.dp)
                        ) {
                            Text(
                                text = it.details.type, fontSize = 14.sp, color = Color(65, 129, 63)
                            )
                            Text(text = it.name, fontSize = 20.sp)
                        }
                    }

                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    Column(
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Text(
                            text = "Opis:",
                            fontSize = 20.sp,
                            modifier = Modifier.padding(bottom = 10.dp)
                        )
                        Text(text = it.description, fontSize = 14.sp)

                        Text(
                            text = "Szczegóły:",
                            fontSize = 20.sp,
                            modifier = Modifier.padding(top = 20.dp, bottom = 10.dp)
                        )

                        DetailInfo("Ułożenie: ", it.details.placement)
                        DetailInfo("Temperatura: ", it.details.temperature)
                        DetailInfo("Podlewanie: ", it.details.watering)


//                        Text(
//                            text = "Ocena: ${it.rate}",
//                            fontSize = 20.sp,
//                            modifier = Modifier.padding(top = 20.dp, bottom = 10.dp)
//                        )
                    }
                }
            }
        }
    }
}


@Composable
fun DetailInfo(infoText: String, value: String) {
    Row {
        Text(
            text = infoText, fontSize = 14.sp, fontWeight = FontWeight.Bold
        )
        Text(text = value, fontSize = 14.sp)

    }
}