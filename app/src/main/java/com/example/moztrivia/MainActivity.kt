package com.example.moztrivia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.moztrivia.model.playerModel.PlayerViewModel
import com.example.moztrivia.navigation.AppNavHost
import com.example.moztrivia.ui.theme.MozTriviaTheme
import com.example.moztrivia.util.AppColors
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

   private val playerViewModel: PlayerViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MozTriviaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {

                  AppNavHost(createPlayer = {
                          playerViewModel.createPlayer(it)

                  },
                      onUpdate = {
                              playerViewModel.updateLives(it)
                      }
                      , playerViewModel = playerViewModel)

                }


            }
        }
    }
}




@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MozTriviaTheme {

    }
}