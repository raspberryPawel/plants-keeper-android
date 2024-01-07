package com.example.plantskeeper

import EnvConfig
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.example.plantskeeper.repository.Plant

@Composable
fun MyPlantsView(plants: List<Plant>) {
    LazyRow(
        Modifier
            .width(500.dp)
            .height(500.dp)
            .padding(top = 100.dp)
    ) {
        items(plants) { plant ->
            SinglePlantView(
                plant.name,
                plant.details.type,
                //@TODO: ONLY FOR DEVELOPMENT PURPOSES
                plant.image.replace("http://localhost", EnvConfig.getServerPath())
            )
        }
    }
}


@Composable
fun SinglePlantView(plantName: String, plantType: String, imageUrl: String) {
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
                    .height(200.dp),
                // TODO: add placeholder
                placeholder = null
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