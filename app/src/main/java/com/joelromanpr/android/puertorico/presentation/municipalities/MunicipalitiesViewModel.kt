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
package com.joelromanpr.android.puertorico.presentation.municipalities

import androidx.lifecycle.viewModelScope
import com.joelromanpr.android.essentials.arch.ui.EssentialsViewModel
import com.joelromanpr.android.puertorico.domain.usecase.GetMunicipalitiesUseCase
import com.joelromanpr.android.puertorico.navigation.NavRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MunicipalitiesViewModel @Inject constructor(
    private val getMunicipalitiesUseCase: GetMunicipalitiesUseCase
) : EssentialsViewModel<MunicipalitiesContract.MunicipalitiesUiState, MunicipalitiesContract.MunicipalitiesUiAction, NavRoutes>() {

    private val _screenState = MutableStateFlow(MunicipalitiesContract.MunicipalitiesUiState())
    override val screenState: StateFlow<MunicipalitiesContract.MunicipalitiesUiState> = _screenState.asStateFlow()

    init {
        onAction(MunicipalitiesContract.MunicipalitiesUiAction.LoadMunicipalities)
    }

    override fun onAction(action: MunicipalitiesContract.MunicipalitiesUiAction) {
        when (action) {
            MunicipalitiesContract.MunicipalitiesUiAction.LoadMunicipalities -> loadMunicipalities()
        }
    }

    private fun loadMunicipalities() {
        viewModelScope.launch {
            _screenState.update { it.copy(isLoading = true, error = null) }
            try {
                val data = getMunicipalitiesUseCase()
                _screenState.update { it.copy(isLoading = false, municipalities = data) }
            } catch (e: Exception) {
                _screenState.update { it.copy(isLoading = false, error = e.localizedMessage ?: "Unknown error") }
            }
        }
    }
}
