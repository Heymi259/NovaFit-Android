package com.novafit.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.navigation.compose.rememberNavController
import com.novafit.app.data.local.TokenDataStore
import com.novafit.app.ui.navigation.NovafitNavHost
import com.novafit.app.ui.theme.NovafitTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var tokenDataStore: TokenDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val estaAutenticado = runBlocking {
            tokenDataStore.accessToken.firstOrNull() != null
        }

        setContent {
            NovafitTheme {
                val navController = rememberNavController()
                NovafitNavHost(
                    navController = navController,
                    estaAutenticado = estaAutenticado
                )
            }
        }
    }
}
