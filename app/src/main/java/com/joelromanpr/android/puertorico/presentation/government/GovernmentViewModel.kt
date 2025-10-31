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
package com.joelromanpr.android.puertorico.presentation.government

import androidx.lifecycle.viewModelScope
import com.joelromanpr.android.essentials.arch.ui.EssentialsViewModel
import com.joelromanpr.android.puertorico.domain.usecase.GetGovernmentDataUseCase
import com.joelromanpr.android.puertorico.navigation.NavRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GovernmentViewModel @Inject constructor(
    private val getGovernmentDataUseCase: GetGovernmentDataUseCase
) : EssentialsViewModel<GovernmentContract.GovernmentUiState, GovernmentContract.GovernmentUiAction, NavRoutes>() {

    private val _screenState = MutableStateFlow(GovernmentContract.GovernmentUiState())
    override val screenState: StateFlow<GovernmentContract.GovernmentUiState> = _screenState.asStateFlow()

    init {
        onAction(GovernmentContract.GovernmentUiAction.LoadGovernmentData)
    }

    override fun onAction(action: GovernmentContract.GovernmentUiAction) {
        when (action) {
            GovernmentContract.GovernmentUiAction.LoadGovernmentData -> loadGovernmentData()
        }
    }

    private fun loadGovernmentData() {
        viewModelScope.launch {
            _screenState.update { it.copy(isLoading = true, error = null) }
            try {
                val data = getGovernmentDataUseCase()
                _screenState.update { it.copy(isLoading = false, governmentData = data) }
            } catch (e: Exception) {
                _screenState.update { it.copy(isLoading = false, error = e.localizedMessage ?: "Unknown error") }
            }
        }
    }
}
