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
package com.joelromanpr.android.puertorico.presentation.home

import androidx.lifecycle.viewModelScope
import com.joelromanpr.android.essentials.arch.ui.EssentialsViewModel
import com.joelromanpr.android.puertorico.navigation.NavRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() :
    EssentialsViewModel<HomeContract.HomeUiState, HomeContract.HomeUiAction, NavRoutes>() {

    private val _screenState = MutableStateFlow(HomeContract.HomeUiState())
    override val screenState: StateFlow<HomeContract.HomeUiState> = _screenState.asStateFlow()

    init {
        _screenState.update { it.copy(currentDate = getFormattedDate()) }
    }

    override fun onAction(action: HomeContract.HomeUiAction) {
        when (action) {
            is HomeContract.HomeUiAction.NavigateTo -> {
                viewModelScope.launch {
                    navigateTo(mapToAppDestination(action.route))
                }
            }
        }
    }

    private fun getFormattedDate(): String {
        val sdf = SimpleDateFormat("EEEE, dd 'de' MMMM 'de' yyyy", Locale("es", "ES"))
        return sdf.format(Date())
    }

    private fun mapToAppDestination(route: String): NavRoutes {
        return when (route) {
            NavRoutes.Government.route -> NavRoutes.Government
            NavRoutes.Municipalities.route -> NavRoutes.Municipalities
            NavRoutes.Schools.route -> NavRoutes.Schools
            else -> throw IllegalArgumentException("Unknown route: $route")
        }
    }
}
