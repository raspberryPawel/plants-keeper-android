@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.plantskeeper

import MainViewModel
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.plantskeeper.repository.UiState
import com.example.plantskeeper.ui.theme.LoadingScreen
import com.example.plantskeeper.ui.theme.PlantsKeeperTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    private fun navigateToDetails(id: String) {
        val intent = Intent(this, SinglePlantActivity::class.java)
        intent.putExtra("PLANT_ID", "$id")
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        viewModel.getData()

        setContent {
            PlantsKeeperTheme {
                MainView(viewModel = viewModel,
                    onClick = { id -> navigateToDetails(id) })
            }
        }
    }
}


@Composable
fun MainView(viewModel: MainViewModel, onClick: (String) -> Unit) {
    val uiState by viewModel.immutablePlantsData.observeAsState(UiState())
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) { contentPadding ->
        when {
            uiState.isLoading -> LoadingScreen()

            uiState.error != null -> {
                scope.launch {
                    val result = snackbarHostState.showSnackbar(
                        message = "Something went wrong",
                        actionLabel = "Try again",
                        duration = SnackbarDuration.Indefinite
                    )
                    when (result) {
                        SnackbarResult.ActionPerformed -> {
                            viewModel.getData()
                        }

                        SnackbarResult.Dismissed -> {
                            // Nothing
                        }
                    }
                }
            }

            uiState.data != null -> uiState.data?.let {
                MyPlantsView(
                    plants = it,
                    onClick = { id -> onClick(id) })
            }
        }
    }
}