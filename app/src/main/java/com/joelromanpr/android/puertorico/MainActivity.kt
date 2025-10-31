/*
 * Copyright (C) 2025 joelromanpr (Joel Roman)
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://opensource.org/licenses/MIT
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.joelromanpr.android.puertorico

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.joelromanpr.android.puertorico.navigation.NavRoutes
import com.joelromanpr.android.puertorico.presentation.government.GovernmentScreen
import com.joelromanpr.android.puertorico.presentation.home.HomeScreen
import com.joelromanpr.android.puertorico.presentation.municipalities.MunicipalitiesScreen
import com.joelromanpr.android.puertorico.presentation.schools.SchoolsScreen
import com.joelromanpr.android.puertorico.ui.theme.AndroidPuertoRicoMVVMTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidPuertoRicoMVVMTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = NavRoutes.Home.route) {
                        composable(NavRoutes.Home.route) {
                            HomeScreen(navigate = { navController.navigate(it.route) })
                        }
                        composable(NavRoutes.Government.route) {
                            GovernmentScreen()
                        }
                        composable(NavRoutes.Municipalities.route) {
                            MunicipalitiesScreen()
                        }
                        composable(NavRoutes.Schools.route) {
                            SchoolsScreen()
                        }
                    }
                }
            }
        }
    }
}
